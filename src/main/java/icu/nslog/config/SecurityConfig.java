package icu.nslog.config;

import icu.nslog.NslogStatus;
import icu.nslog.api.bean.NslogResultBean;
import icu.nslog.api.bean.UserAuthorizationDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: MultiHttpSecurityConfig
 * @description: TODO
 * @author: cookun
 * @date: 1/5/22
 **/
@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Configuration
    @Order(1)
    public static class BasicWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();
            http.antMatcher("/openapi/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasAnyRole("API")
                )
            .httpBasic()
                    .authenticationEntryPoint(new OpenApiAuthenticationEntryPoint());

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            OpenApiAuthenticationProvider provider = new OpenApiAuthenticationProvider();
            provider.setHideUserNotFoundExceptions(true);
            provider.setUserDetailsService(openApiDetailsService());
            auth.authenticationProvider(provider);
        }

        @Bean
        public UserDetailsService openApiDetailsService() {
            return new OpenApiAuthenticationDetailsServiceImpl();
        }

        @Bean
        public BCryptPasswordEncoder openApiPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }


    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.addFilterBefore(new FormLoginAuthenticationFilter(this.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
            http.authorizeRequests().antMatchers("/**").hasAnyRole("ADMIN", "USER").antMatchers("/login**").permitAll();
            http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).permitAll();
            http.formLogin();
            http.exceptionHandling().accessDeniedHandler(new FormLoginAccessDeniedHandler()).authenticationEntryPoint(new FormLoginAuthenticationEntryPoint());

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setHideUserNotFoundExceptions(true);
            provider.setUserDetailsService(apiDetailsService());
            provider.setPasswordEncoder(passwordEncoder());
            auth.authenticationProvider(provider);
        }

        @Bean
        public UserDetailsService apiDetailsService() {
            return new FormLoginAuthenticationDetailsServiceImpl();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }


}
