package za.co.bonga.jwt_practice.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String errorMessage, String responseCode, LocalDateTime responseDateTime) {
}
