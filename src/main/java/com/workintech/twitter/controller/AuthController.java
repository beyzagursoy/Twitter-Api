package com.workintech.twitter.controller;

import com.workintech.twitter.dto.LoginDto;
import com.workintech.twitter.dto.RegisterDto;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDto registerDto) {
        User registeredUser = authenticationService.register(
                registerDto.username(),
                registerDto.email(),
                registerDto.password()
        );
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok("Giriş başarılı!" + loginDto.username());
    }
}
