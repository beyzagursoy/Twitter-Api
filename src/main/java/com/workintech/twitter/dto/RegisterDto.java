package com.workintech.twitter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank(message = "Kullanıcı adı boş olamaz.")
        @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır.")
        String username,

        @NotBlank(message = "E-posta boş olamaz.")
        @Email(message = "Geçerli bir e-posta adresi giriniz.")
        String email,

        @NotBlank(message = "Şifre boş olamaz.")
        @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır.")
        String password
) {}