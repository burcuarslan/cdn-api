package com.cdnapi.user.controller;

import com.cdnapi.user.dto.CreateUserRequest;
import com.cdnapi.user.dto.RoleRequest;
import com.cdnapi.user.dto.UpdateUserRequest;
import com.cdnapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{email}")
    public ResponseEntity<Void> updateUser(@PathVariable String email, @RequestBody UpdateUserRequest request) {
        userService.updateUser(email, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/roles/{email}")
    public ResponseEntity<Void> addRolesToUser(@PathVariable String email, @RequestBody RoleRequest request) {
        userService.addRolesToUser(email, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/roles/{email}")
    public ResponseEntity<Void> removeRolesFromUser(@PathVariable String email, @RequestBody RoleRequest request) {
        userService.removeRolesFromUser(email, request);
        return ResponseEntity.ok().build();
    }
}
