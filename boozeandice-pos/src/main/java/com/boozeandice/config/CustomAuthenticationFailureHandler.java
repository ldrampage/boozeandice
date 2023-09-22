package com.boozeandice.config;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private static final Logger logger = LogManager.getLogger(CustomAuthenticationFailureHandler.class);
	
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		logger.debug("Authentication failed: " + exception.getMessage());
        // Forward to the login page with the error message
        super.setDefaultFailureUrl("/login?error=true&message="+exception.getMessage());
        super.onAuthenticationFailure(request, response, exception);
    }

}
