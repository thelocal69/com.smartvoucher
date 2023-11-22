package com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.handler;

import com.google.gson.Gson;
import com.smartvoucher.webEcommercesmartvoucher.config.ApplicationConfig;
import com.smartvoucher.webEcommercesmartvoucher.exception.BadRequestException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseToken;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.OAuth2UserDetailCustom;
import com.smartvoucher.webEcommercesmartvoucher.service.oauth2.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.smartvoucher.webEcommercesmartvoucher.util.CookieUtils;
import com.smartvoucher.webEcommercesmartvoucher.util.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OAuth2SuccessHandlerCustom extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTHelper jwtHelper;
    private final CookieUtils cookieUtils;
    private final ApplicationConfig applicationConfig;
    private final Gson gson;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    public OAuth2SuccessHandlerCustom(JWTHelper jwtHelper,
                                      CookieUtils cookieUtils,
                                      ApplicationConfig applicationConfig,
                                      HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
                                      Gson gson) {
        this.jwtHelper = jwtHelper;
        this.cookieUtils = cookieUtils;
        this.applicationConfig = applicationConfig;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.gson = gson;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.info("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = cookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            log.info("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
            throw new BadRequestException(400 ,"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        OAuth2UserDetailCustom oAuth2UserDetailCustom = (OAuth2UserDetailCustom) authentication.getPrincipal();
        ResponseToken data = new ResponseToken();
        data.setUsername(oAuth2UserDetailCustom.getEmail());
        data.setRoles(oAuth2UserDetailCustom.getAuthorities().stream()
                .map(r -> new SimpleGrantedAuthority(r.getAuthority())).collect(Collectors.toList())
        );
        String token = jwtHelper.createdGoogleToken(gson.toJson(data));

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return applicationConfig.getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }
    }
