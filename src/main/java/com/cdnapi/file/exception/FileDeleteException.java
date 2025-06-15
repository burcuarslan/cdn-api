package com.cdnapi.file.exception;

import org.springframework.http.HttpStatus;

public class FileDeleteException extends RuntimeException {
    private final HttpStatus httpStatus;

    public FileDeleteException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public FileDeleteException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
