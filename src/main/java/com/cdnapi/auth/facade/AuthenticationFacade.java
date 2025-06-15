package com.cdnapi.auth.facade;

import com.cdnapi.auth.dto.AuthenticationRequest;
import com.cdnapi.auth.dto.AuthenticationResponse;
import com.cdnapi.auth.service.AuthenticationService;
import com.cdnapi.auth.service.JwtService;
import com.cdnapi.user.entity.User;
import com.cdnapi.user.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userService.findUserByEmailOrThrow(request.getEmail());
        authenticationService.authenticate(request.getEmail(), request.getPassword());

        String token = jwtService.generateToken(user);
        long expireTime = jwtService.extractClaim(token, Claims::getExpiration).getTime();
        long remainingSeconds = (expireTime - System.currentTimeMillis()) / 1000;

        return new AuthenticationResponse(
                token,
                expireTime,
                remainingSeconds
        );
    }
}
