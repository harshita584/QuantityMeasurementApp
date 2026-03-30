package com.app.quantitymeasurement.OAuthHandler;

import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepo;
import com.app.quantitymeasurement.service.JWTService;
import com.app.quantitymeasurement.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JWTService jwtService;
    private final UserRepo userRepo;
    private final UserDetailsService userDetailsService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken)  authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        log.info(email + " " + name);

        if(!userRepo.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            userRepo.save(user);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtService.generateToken(email);

        String jsonResponse = String.format(
                "{\"token\": \"%s\", \"email\": \"%s\"}", token, email
        );
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
