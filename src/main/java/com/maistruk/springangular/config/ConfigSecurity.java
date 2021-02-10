package com.maistruk.springangular.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigSecurity extends WebSecurityConfigurerAdapter{
    
    private UserDetailsService userDetailsService;
    
    
    @Autowired
    public ConfigSecurity(@Qualifier("userDetailsServiceImpl")UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
          .authorizeRequests()
          .antMatchers("/").permitAll()
          .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//        .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
//         .antMatchers(HttpMethod.GET, "/api/**").hasRole(Role.ADMIN.name())
//          .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
//          .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic();
    }
    

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
////                .authorities(Role.ADMIN.getAuthorities(Role.ADMIN.name()))
//                .roles(Role.ADMIN.name())
//                .build(),
//                User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("user"))
//                .roles(Role.USER.name())
//                .build()
//                );        
//    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    
    
//    CREATE TABLE users(
//            id bigint PRIMARY KEY,
//            email VARCHAR(255) NOT NULL,
//            first_name VARCHAR(50) NOT NULL,
//            last_name VARCHAR(50) NOT NULL,
//            password VARCHAR(255) NOT NULL,
//            role VARCHAR(50) NOT NULL DEFAULT ('USER'),
//            status VARCHAR(50) NOT NULL DEFAULT ('ACTIVE')
//            );
    
//    INSERT INTO users (id, email, first_name, last_name, password, role, status) VALUES (1, 'admin@gmail.com', 'admin', 'adminov', '$2y$12$0ae5AOJ0owvGKKc7SiRfV.HM1e3saGpF53w.3Cig0q6ZZYFzR74Ca', 'ADMIN', DEFAULT);
//    INSERT INTO users (id, email, first_name, last_name, password, role, status) VALUES (2, 'user@gmail.com', 'user', 'Userov', '$2y$12$0tZB.0IdmwKUUvx3oL2rIeM4jJSCivdQfQILH5EWp6DX14HeYgaby', 'USER', 'BANNED');

    
}
