package com.clovischakrian.mediator.exceptions;

public class FailedToHandleRequestException extends RuntimeException {
    public FailedToHandleRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
