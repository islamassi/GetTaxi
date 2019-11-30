package com.assi.islam.mytaxi.model;

/**
 * Created by islam assi
 */
public class ApiError {

    private String message;

    private Throwable throwable;

    public ApiError() {
    }

    public ApiError(Throwable throwable) {
        this.throwable = throwable;
    }

    public ApiError(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
