package com.project.MyProject.config;

import com.project.MyProject.config.jwt.JWTAuthenticationFilter;
import com.project.MyProject.config.jwt.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.headers().cacheControl();
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/user/register").permitAll()
                    .antMatchers("/tabs/all").permitAll()
                    .antMatchers("/tabs/user/**").permitAll()
                    .antMatchers("/tabs/search").permitAll()
                    .antMatchers("/tabs/{id}").permitAll()
                    .antMatchers(HttpMethod.POST,"/login").permitAll()
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Create a default account
        /*auth.inMemoryAuthentication()
                .withUser("root").password(encoder().encode("root")).roles("ADMIN");*/
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

}