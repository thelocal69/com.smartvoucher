
package com.smartvoucher.webEcommercesmartvoucher.config;

import com.smartvoucher.webEcommercesmartvoucher.filter.JWTFilter;
import com.smartvoucher.webEcommercesmartvoucher.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JWTFilter jwtFilter;
    private final LogoutHandler logoutHandler;

    @Autowired
    public SecurityConfig(final CustomAuthenticationProvider customAuthenticationProvider,
                          final JWTFilter jwtFilter,
                          final LogoutHandler logoutHandler
    ) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtFilter = jwtFilter;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
            return http.csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeHttpRequests()
                    .antMatchers("/account/**").permitAll()
                    .antMatchers("**/upload").permitAll()
                    .antMatchers(HttpMethod.GET, "/merchant").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/merchant").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/merchant").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/merchant").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/chain").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/chain").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/chain").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/chain").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/category").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/category").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/category").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/category").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/discount").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/discount").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/discount").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/discount").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/warehouse").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/warehouse").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/warehouse").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/warehouse").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/store").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/store").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/store").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/store").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/label").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/user/api/upload").hasRole("USER")
                    .antMatchers("/serial").hasRole("ADMIN")
                    .antMatchers("/role").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/order").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/order").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/order").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/order").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/ticket_history").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/ticket_history").hasRole("USER")
                    .anyRequest().authenticated()// tất cả những cái còn lại đều cần phải chứng thực
                    .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout()
                    .logoutUrl("/account/api/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(((request, response, authentication) ->
                            SecurityContextHolder.clearContext()))
                    .and()
                    .build();
    }

}
