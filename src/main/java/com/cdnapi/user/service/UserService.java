package com.cdnapi.user.service;

import com.cdnapi.user.repository.UserRepository;
import com.cdnapi.user.dto.CreateUserRequest;
import com.cdnapi.user.dto.RoleRequest;
import com.cdnapi.user.dto.UpdateUserRequest;
import com.cdnapi.user.entity.User;
import com.cdnapi.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUserByEmailOrThrow(String email) {
        return userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByEmailAndEnabledTrueOrThrow(String email) {
        if (userRepository.existsByEmailAndEnabledTrue(email)) {
            return true;
        }
        throw new UserNotFoundException("User Not Found");
    }

    public void createUser(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setDescription(request.getDescription());
        user.setEnabled(true);
        user.addRoles(request.getRoles());
        userRepository.save(user);
    }

    public User updateUser(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null && !userRepository.existsByEmail(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }
        return userRepository.save(user);
    }

    public void addRolesToUser(String email, RoleRequest request) {
        User user = findUserByEmailOrThrow(email);
        user.addRoles(request.getRoles());
        userRepository.save(user);
    }

    public void removeRolesFromUser(String email, RoleRequest request) {
        User user = findUserByEmailOrThrow(email);
        user.removeRoles(request.getRoles());
        userRepository.save(user);
    }
}
