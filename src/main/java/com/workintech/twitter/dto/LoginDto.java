package com.workintech.twitter.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Kullanıcı adı boş olamaz.")
        String username,

        @NotBlank(message = "Şifre boş olamaz.")
        String password
) {}