package com.cdnapi.auth.service;

public interface AuthenticationService {
    void authenticate(String email, String password);
}
