package robson.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    public LocalDateTime timestamp;
    public int status;
    public String message;
    public String path;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }
}