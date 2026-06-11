package com.workintech.twitter.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TwitterErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
