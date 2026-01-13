package com.romario.usuario.infrastructure.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UnaltorizedException extends AuthenticationException {

    public UnaltorizedException(String message) {
        super(message);
    }

    public UnaltorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
