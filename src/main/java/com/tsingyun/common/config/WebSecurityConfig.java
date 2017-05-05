package com.tsingyun.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/login").permitAll()//ignore
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login") //login page
            .defaultSuccessUrl("/chat") // success page
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add two users in memory
        auth
            .inMemoryAuthentication()
            .withUser("wyf").password("wyf").roles("USER")
            .and()
            .withUser("wisely").password("wisely").roles("USER");
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // ignore the static resources
        web.ignoring().antMatchers("/resources/static/**");
        web.ignoring().antMatchers("/resources/templates/**");
    }

}
