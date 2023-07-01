package rest.cinema.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String error;

    private String message;

    public ErrorMessage(int statusCode, LocalDateTime timestamp, String error, String message) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
