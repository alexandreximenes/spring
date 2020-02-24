package com.course.springbootreactiveprogramming;

public class CustomError extends Throwable {

    public CustomError(String message) {
        super(message);
    }
}
