package com.cdnapi.config;

import com.cdnapi.user.dto.CreateUserRequest;
import com.cdnapi.user.entity.Role;
import com.cdnapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserInitializer implements CommandLineRunner {

    private final UserService userService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String adminEmail = "admin@cdnapi.com";

        try {
            if (userService.existsByEmail(adminEmail)) {
                return;
            }
            userService.createUser(new CreateUserRequest(
                    adminEmail,
                    "admin",
                    "password",
                    "admin description",
                    Set.of(Role.ADMIN)
            ));
        } catch (Exception e) {
            log.info("Admin user could not be created: " + e.getMessage());
        }
    }
}
