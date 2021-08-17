package com.cliente.escola.gradecurricular.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configuracaoGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication().withUser("root").password(password.encode("root")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] allowed = new String[]{
                "/webjars", "/static/**", "/swagger*/**"
        };
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(allowed).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
