package dev.otthon.parkingapi.exception;

public class UsernameViolationException extends RuntimeException {

    public UsernameViolationException(String message) {
        super(message);
    }

}
