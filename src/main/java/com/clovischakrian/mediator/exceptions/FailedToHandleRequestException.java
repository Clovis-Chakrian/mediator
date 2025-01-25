package com.clovischakrian.mediator.exceptions;

public class FailedToHandleRequestException extends RuntimeException {
    private Throwable throwable;

    public FailedToHandleRequestException(String message, Throwable throwable) {
        super(message, throwable);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
