
package com.smartvoucher.webEcommercesmartvoucher.config;

import com.smartvoucher.webEcommercesmartvoucher.filter.JWTFilter;
import com.smartvoucher.webEcommercesmartvoucher.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JWTFilter jwtFilter;
    private final LogoutHandler logoutHandler;
    private final UserDetailsService userDetailsService;
    @Value("${frontend_utl}")
    private String frontEndURL;
    @Value("${ngrokURL}")
    private String ngrokURL;
    @Autowired
    public SecurityConfig(final CustomAuthenticationProvider customAuthenticationProvider,
                          final JWTFilter jwtFilter,
                          final LogoutHandler logoutHandler,
                          final UserDetailsService userDetailsService
    ) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtFilter = jwtFilter;
        this.logoutHandler = logoutHandler;
        this.userDetailsService = userDetailsService;
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
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of(frontEndURL, ngrokURL));
                        configuration.setAllowedHeaders(Arrays.asList(
                                "X-CSRF-Token",
                                "X-Requested-With",
                                "client-security-token",
                                "user-agent",
                                "Content-Type",
                                "Accept",
                                "Authorization",
                                "ngrok-skip-browser-warning"));
                        configuration.setAllowedMethods(Arrays.asList(
                                "GET",
                                "POST",
                                "OPTIONS",
                                "PUT",
                                "DELETE"));
                        configuration.setAllowCredentials(true);
                        return  configuration;
            })).csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeHttpRequests()
                    .antMatchers("/account/**").permitAll()
                    .antMatchers("**/upload").permitAll()
                    .antMatchers("/comment/**").permitAll()
                    .antMatchers("/reply/**").permitAll()
                    //merchant
                    .antMatchers(HttpMethod.GET, "/merchant").permitAll()
                    .antMatchers(HttpMethod.GET, "/merchant/{fileName}").permitAll()
                    .antMatchers(HttpMethod.GET, "/merchant/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/merchant/api/getName").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/merchant/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/merchant/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/merchant/api/upload").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/merchant/api/local_upload").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/merchant/api/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/merchant/api/{id}").hasRole("ADMIN")
                    //merchant
                    //chain
                    .antMatchers("/chain/**").hasRole("ADMIN")
                    //chain
                    .antMatchers("/label/api/all").permitAll()
                    .antMatchers("/label/api/getName").permitAll()
                    .antMatchers("/label/api/getAll").hasRole("ADMIN")
                    //user
                    .antMatchers(HttpMethod.GET,"/user/api/infor").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/user/api/edit").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/user/api/upload").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/user/api/change_pwd").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/user/api/buy_voucher").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/user/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/user/api/search").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/user/api/block/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/user/api/profile").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/user/api/uploadAdmin").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/user/api/editAdmin").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/user/api/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/user/api/all").hasRole("ADMIN")
                    //user
                    //category
                    .antMatchers(HttpMethod.GET, "/category/api/all").permitAll()
                    .antMatchers(HttpMethod.GET, "/category/{fileName}").permitAll()
                    .antMatchers(HttpMethod.GET, "/category/api/getName").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/category/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/category/api/search").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/category/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/category/api/upload").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/category/api/local_upload").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/category/api/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/category/api/{id}").hasRole("ADMIN")
                    //category
                    //discount
                    .antMatchers(HttpMethod.GET,"/discount").permitAll()
                    .antMatchers(HttpMethod.GET,"/discount/api/getName").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/discount/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/discount/api/search").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/discount/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/discount/api/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/discount/api/{id}").hasRole("ADMIN")
                    //discount
                    //store
                    .antMatchers(HttpMethod.GET, "/store").permitAll()
                    .antMatchers(HttpMethod.GET, "/store/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/store/api/search").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/store/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/store/api/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/store/api/{id}").hasRole("ADMIN")
                    //store
                    //order
                    .antMatchers(HttpMethod.GET, "/order/api/list-order").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/order/api/get/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/order/api/get/all").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/order/api/add-order").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE, "/order/api/delete-order").hasRole("USER")
                    //order
                    //warehouse
                    .antMatchers(HttpMethod.GET, "/warehouse/api/category_name").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/api/all").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/api/get/label").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/api/{id}").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/{fileName}").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse/by-label-id/{id}").permitAll()
                    .antMatchers(HttpMethod.GET,"/warehouse/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/warehouse/api/search").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/warehouse/api/upload/banner").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/warehouse/api/upload/thumbnail").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/warehouse/api/local_banner").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/warehouse/api/local_thumbnail").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/warehouse/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT,"/warehouse/api/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/warehouse/api/{id}").hasRole("ADMIN")
                    //warehouse
                    //warehouse_store
                    .antMatchers(HttpMethod.GET, "/warehouse_merchant").permitAll()
                    .antMatchers(HttpMethod.POST,"/warehouse_merchant/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/warehouse_merchant/api/delete").hasRole("ADMIN")
                    //warehouse_store
                    //warehouse_merchant
                    .antMatchers(HttpMethod.GET, "/warehouse_store").permitAll()
                    .antMatchers(HttpMethod.GET, "/warehouse_store/api/getId_Store").permitAll()
                    .antMatchers(HttpMethod.POST,"/warehouse_store/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/warehouse_store/api/delete").hasRole("ADMIN")
                    //warehouse_merchant
                    //warehouse_store
                    .antMatchers(HttpMethod.GET, "/warehouse_serial/api/all").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/warehouse_serial/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/warehouse_serial/api/delete").hasRole("ADMIN")
                    //warehouse_store
                    .antMatchers(HttpMethod.GET,"/ticket_history").hasRole("ADMIN")
                    //ticket
                    .antMatchers(HttpMethod.GET, "/ticket/api/list-ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/ticket/api/update-ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/ticket/api/delete-ticket").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/ticket/api/get/all").hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/ticket/api/buy-ticket").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/ticket/api/use_ticket").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/ticket/api/ticket_detail").hasRole("USER")
                    //ticket
                    //role_user
                    .antMatchers(HttpMethod.GET,"/role_user").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/role_user/api/insert").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/role_user/api/delete").hasRole("ADMIN")
                    //role_user
                    //serial
                    .antMatchers(HttpMethod.GET,"/serial/api/list-serial").permitAll()
                    .antMatchers(HttpMethod.GET,"/serial/api/getAll").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/serial/api/add-serial").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT,"/serial/api/update-serial").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/serial/api/delete-serial").hasRole("ADMIN")
                    //serial
                    .anyRequest().authenticated()
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
