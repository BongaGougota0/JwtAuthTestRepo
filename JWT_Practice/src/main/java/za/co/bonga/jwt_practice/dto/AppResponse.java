package za.co.bonga.jwt_practice.dto;

import java.time.LocalDateTime;

public record AppResponse(String responseMessage, String responseCode, LocalDateTime responseDateTime) {
}
