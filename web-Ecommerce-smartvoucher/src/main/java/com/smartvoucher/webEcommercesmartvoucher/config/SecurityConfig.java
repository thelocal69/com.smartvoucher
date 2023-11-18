
package com.smartvoucher.webEcommercesmartvoucher.config;

import com.smartvoucher.webEcommercesmartvoucher.filter.JWTFilter;
import com.smartvoucher.webEcommercesmartvoucher.provider.CustomAuthenticationProvider;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.OAuth2UserDetailCustomService;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.handler.OAuth2FailureHandlerCustom;
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
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final OAuth2UserDetailCustomService oAuth2UserDetailCustomService;
    private final OAuth2FailureHandlerCustom oAuth2FailureHandlerCustom;

    @Autowired
    public SecurityConfig(final CustomAuthenticationProvider customAuthenticationProvider,
                          final JWTFilter jwtFilter,
                          final LogoutHandler logoutHandler,
                          final UserDetailsService userDetailsService,
                          final OAuth2UserDetailCustomService oAuth2UserDetailCustomService,
                          final OAuth2FailureHandlerCustom oAuth2FailureHandlerCustom
    ) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtFilter = jwtFilter;
        this.logoutHandler = logoutHandler;
        this.userDetailsService = userDetailsService;
        this.oAuth2UserDetailCustomService = oAuth2UserDetailCustomService;
        this.oAuth2FailureHandlerCustom = oAuth2FailureHandlerCustom;
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
                return corsConfig;
            })).csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeHttpRequests()
                    .antMatchers("/account/**").permitAll()
                    .antMatchers("**/upload").permitAll()
                    .antMatchers("/oauth2/**").permitAll()
                    .antMatchers("/merchant/**").hasRole("ADMIN")
                    .antMatchers("/chain/**").hasRole("ADMIN")
                    .antMatchers("/label/api/all").permitAll()
                    //category
                    .antMatchers(HttpMethod.GET, "/category/api/all").permitAll()
                    .antMatchers("/category/**").hasRole("ADMIN")
                    //category
                    .antMatchers("/discount/**").hasRole("ADMIN")
                    .antMatchers("/store/**").hasRole("ADMIN")
                    //warehouse
                    .antMatchers(HttpMethod.GET, "/warehouse/api/all").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/api/{id}").permitAll()
                    .antMatchers(HttpMethod.GET, "/by-label-id/{id}").permitAll()
                    .antMatchers("/warehouse/**").hasRole("ADMIN")
                    .antMatchers("/ticket_history/**").hasRole("ADMIN")
                    .antMatchers("/role_user/**").hasRole("ADMIN")
                    .anyRequest().authenticated()// tất cả những cái còn lại đều cần phải chứng thực
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and().oauth2Login()
                    .defaultSuccessUrl("/user/api/auth2/infor", true)
                    .userInfoEndpoint().userService(oAuth2UserDetailCustomService)
                    .and().failureHandler(oAuth2FailureHandlerCustom)
                    .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/account/api/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(((request, response, authentication) ->
                            SecurityContextHolder.clearContext()))
                    .and().rememberMe().key("24215da6-3401-45bd-bbe9-f5236d4d1fbd")
                    .userDetailsService(userDetailsService)
                    .and().build();
    }

}
