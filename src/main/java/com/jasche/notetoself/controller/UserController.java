package com.jasche.notetoself.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Rest controller for HTTP requests related to Users.
 */
@RestController
public class UserController {
    private final ClientRegistration registration;

    /**
     * Constructor for UserController, initializing ClientRegistration to the okta profile
     * found in resources/application.yml.
     * @param registrations repository for ClientRegistration
     */
    public UserController(ClientRegistrationRepository registrations) {
        this.registration = registrations.findByRegistrationId("okta");
    }

    /**
     * This mapping to /api/user for GET requests returns a user's details.
     * @param user  user whose details are to be returned
     * @return  HTTP response whose body contains user details or empty if null user
     */
    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            return ResponseEntity.ok().body(user.getAttributes());
        }
    }

    /**
     * This mapping to /api/post for POST requests logs out a user by invalidating their
     * authentication and OIDC id token.
     * @param request   HttpServletRequest whose current session is to be invalidated
     * @param idToken   OidcIdToken to be invalidated
     * @return  HTTP response with details for managing logout
     */
    @PostMapping("/api/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request,
                                    @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {
        // send logout URL to client so they can initiate logout
        String logoutUrl = this.registration.getProviderDetails()
                .getConfigurationMetadata().get("end_session_endpoint").toString();

        Map<String, String> logoutDetails = new HashMap<>();
        logoutDetails.put("logoutUrl", logoutUrl);
        logoutDetails.put("idToken", idToken.getTokenValue());
        request.getSession(false).invalidate();
        return ResponseEntity.ok().body(logoutDetails);
    }
}