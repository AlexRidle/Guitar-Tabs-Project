package com.project.MyProject.config;

import com.project.MyProject.enumeration.UserRole;
import com.project.MyProject.security.JWTAuthenticationFilter;
import com.project.MyProject.security.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.vaadin.spring.security.annotation.EnableVaadinManagedSecurity;
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer;

@Configuration
//@EnableWebSecurity
@EnableVaadinManagedSecurity
public class WebSecurityConfig implements AuthenticationManagerConfigurer {

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//
//        http.headers().cacheControl();
//        http.csrf().disable()
//                .authorizeRequests()
//                    .antMatchers("/").permitAll()
//                    .antMatchers("/user/register").permitAll()
//                    .antMatchers("/comment/tab").permitAll()
//                    .antMatchers("/tabs/all").permitAll()
//                    .antMatchers("/tabs/user").permitAll()
//                    .antMatchers("/tabs/search").permitAll()
//                    .antMatchers("/tabs/tab").permitAll()
//                    .antMatchers(HttpMethod.POST,"/login").permitAll()
//                    .antMatchers(HttpMethod.POST,"/user/all").hasAnyRole(UserRole.ADMIN.toString())
//                .anyRequest().authenticated()
//                .and()
//                // We filter the api/login requests
////                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//                // And filter other requests to check the presence of JWT in header
//                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .logout();
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/resources/**", "/VAADIN/**", "/vaadinServlet/**");
//    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

}