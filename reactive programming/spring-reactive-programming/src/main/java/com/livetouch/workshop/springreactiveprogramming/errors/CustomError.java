package com.livetouch.workshop.springreactiveprogramming.errors;

public class CustomError extends RuntimeException {
    public CustomError(String message) {
        super(message);
    }

}
