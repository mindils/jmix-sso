package com.company.sso.app;

import com.vaadin.flow.spring.security.RequestUtil;
import io.jmix.core.JmixSecurityFilterChainOrder;
import io.jmix.oidc.userinfo.JmixOidcUserService;
import io.jmix.security.SecurityConfigurers;
import io.jmix.security.configurer.SessionManagementConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class AppOAuthConfiguration {
    public static final String SECURITY_CONFIGURER_QUALIFIER = "oidc-login";

    @Bean("oidc_OAuthLoginSecurityFilterChain")
    @Order(JmixSecurityFilterChainOrder.OIDC_LOGIN)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JmixOidcUserService jmixOidcUserService,
            ClientRegistrationRepository clientRegistrationRepository,
            RequestUtil requestUtil)
            throws Exception {
        http.authorizeHttpRequests(authorize -> {
                    authorize
                            // if we don't allow /vaadinServlet/PUSH URL the Session Expired
                            // toolbox won't
                            // be shown in the web browser
                            .requestMatchers(
                                    new AntPathRequestMatcher("/vaadinServlet/PUSH/**"),
                                    requestUtil::isFrameworkInternalRequest)
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .oauth2Login(oauth2Login -> {
                    oauth2Login.userInfoEndpoint(userInfoEndpoint -> {
                        userInfoEndpoint.oidcUserService(jmixOidcUserService);
                    });
                })
                .logout(logout -> {
                    logout.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository));
                })
                .csrf(csrf -> {
                    csrf.disable();
                })
                .headers(headers -> {
                    headers.frameOptions(frameOptions -> {
                        frameOptions.sameOrigin();
                    });
                });
        http.apply(new SessionManagementConfigurer());
        SecurityConfigurers.applySecurityConfigurersWithQualifier(http, SECURITY_CONFIGURER_QUALIFIER);
        return http.build();
    }

    protected OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler(
            ClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedLogoutSuccessHandler successHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("{baseUrl}");
        return successHandler;
    }
}
