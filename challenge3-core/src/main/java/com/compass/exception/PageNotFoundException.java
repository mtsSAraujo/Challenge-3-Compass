package com.compass.exception;

public class PageNotFoundException extends RuntimeException{

    public PageNotFoundException(String message) {
        super(message);
    }
}
