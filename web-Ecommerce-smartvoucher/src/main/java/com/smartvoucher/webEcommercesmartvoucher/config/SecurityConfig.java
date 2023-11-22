
package com.smartvoucher.webEcommercesmartvoucher.config;

import com.smartvoucher.webEcommercesmartvoucher.filter.JWTFilter;
import com.smartvoucher.webEcommercesmartvoucher.provider.CustomAuthenticationProvider;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.OAuth2UserDetailCustomService;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.handler.OAuth2FailureHandlerCustom;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.handler.OAuth2SuccessHandlerCustom;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;
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
    private final OAuth2SuccessHandlerCustom oAuth2SuccessHandlerCustom;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    public SecurityConfig(final CustomAuthenticationProvider customAuthenticationProvider,
                          final JWTFilter jwtFilter,
                          final LogoutHandler logoutHandler,
                          final UserDetailsService userDetailsService,
                          final OAuth2UserDetailCustomService oAuth2UserDetailCustomService,
                          final OAuth2FailureHandlerCustom oAuth2FailureHandlerCustom,
                          final OAuth2SuccessHandlerCustom oAuth2SuccessHandlerCustom,
                          final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository
    ) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtFilter = jwtFilter;
        this.logoutHandler = logoutHandler;
        this.userDetailsService = userDetailsService;
        this.oAuth2UserDetailCustomService = oAuth2UserDetailCustomService;
        this.oAuth2FailureHandlerCustom = oAuth2FailureHandlerCustom;
        this.oAuth2SuccessHandlerCustom = oAuth2SuccessHandlerCustom;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
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
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository(){
        return new HttpCookieOAuth2AuthorizationRequestRepository();
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
                    .antMatchers("/auth/**", "/oauth2/**").permitAll()
                    .antMatchers("/merchant/**").hasRole("ADMIN")
                    .antMatchers("/chain/**").hasRole("ADMIN")
                    .antMatchers("/label/api/all").permitAll()
                    //user
                    .antMatchers(HttpMethod.GET,"/user/api/infor").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/user/api/auth2/infor").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/user/api/edit").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/user/api/upload").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/user/api/change_pwd").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/user/api/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/user/api/all").hasRole("ADMIN")
                    //user
                    //category
                    .antMatchers(HttpMethod.GET, "/category/api/all").permitAll()
                    .antMatchers("/category/**").hasRole("ADMIN")
                    //category
                    .antMatchers("/discount/**").hasRole("ADMIN")
                    .antMatchers("/store/**").hasRole("ADMIN")
                    //warehouse
                    .antMatchers(HttpMethod.GET, "/warehouse/CategoryCode/{categoryCode}").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/api/all").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/api/{id}").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/by-label-id/{id}").permitAll()
                    .antMatchers(HttpMethod.POST,"/warehouse/api/upload").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/warehouse/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/warehouse/api/{id}").hasRole("ADMIN")
                    //warehouse
                    .antMatchers("/ticket_history/**").hasRole("ADMIN")
                    .antMatchers("/role_user/**").hasRole("ADMIN")
                    .anyRequest().authenticated()// tất cả những cái còn lại đều cần phải chứng thực
                    .and().oauth2Login()
                    .authorizationEndpoint()
                    //.baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                    .and()
                    .redirectionEndpoint()
                    //.baseUri("/oauth2/callback/*")
                    .and()
                    .userInfoEndpoint().userService(oAuth2UserDetailCustomService)
                    .and()
                    .successHandler(oAuth2SuccessHandlerCustom)
                    .failureHandler(oAuth2FailureHandlerCustom)
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
