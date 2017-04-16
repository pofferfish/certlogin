package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class Security extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requiresChannel()
                .anyRequest()
                .requiresSecure()
                .and()
                .authorizeRequests()
                .antMatchers("/protected").fullyAuthenticated()
                .antMatchers("/public").permitAll()
                .and()
                .x509()
                .subjectPrincipalRegex("serialNumber=(.*?)(?:,|$)")
                .userDetailsService(userDetailsServiceSiths());
    }

    @Bean
    public UserDetailsService userDetailsServiceSiths() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String hsaid) {
                if (hsaid != null && !hsaid.isEmpty()) {
                        return new User(hsaid, "",
                                AuthorityUtils
                                        .commaSeparatedStringToAuthorityList("ROLE_USER"));
                }
                throw new UsernameNotFoundException("Invalid login");
            }
        };
    }

}
