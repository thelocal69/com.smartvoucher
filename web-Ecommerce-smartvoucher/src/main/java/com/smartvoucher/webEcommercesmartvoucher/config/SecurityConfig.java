
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
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

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
            return http.cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.addAllowedOrigin("*");
                corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                corsConfig.addAllowedHeader("*");
                corsConfig.setAllowCredentials(false);
                corsConfig.setMaxAge(3600L);
                return corsConfig;
            })).csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeHttpRequests()
                    .antMatchers("/account/**").permitAll()
                    .antMatchers("**/upload").permitAll()
                    .antMatchers("/merchant/**").hasRole("ADMIN")
                    .antMatchers("/chain/**").hasRole("ADMIN")
                    .antMatchers("/category/**").hasRole("ADMIN")
                    .antMatchers("/discount/**").hasRole("ADMIN")
                    .antMatchers("/store/**").hasRole("ADMIN")
                    .antMatchers("/warehouse/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/user/api/upload").hasRole("USER")
                    .antMatchers("/serial").hasRole("ADMIN")
                    .antMatchers("/role").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/order").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/order").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/order").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/order").hasRole("USER")
                 /*   .antMatchers(HttpMethod.GET,"/ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/ticket").hasRole("ADMIN")*/
                    .antMatchers("/ticket/**").permitAll()
                    .antMatchers("/ticket/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/ticket_history").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/ticket_history").hasRole("USER")
                    .antMatchers("/role_user").hasRole("ADMIN")
                    .anyRequest().authenticated()// tất cả những cái còn lại đều cần phải chứng thực
                    .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/account/api/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(((request, response, authentication) ->
                            SecurityContextHolder.clearContext()))
                    .and().build();
    }

}
