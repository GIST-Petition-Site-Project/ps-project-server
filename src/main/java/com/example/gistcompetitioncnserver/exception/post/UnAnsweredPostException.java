package com.example.gistcompetitioncnserver.exception.post;

import org.springframework.http.HttpStatus;

public class UnAnsweredPostException extends PostException {
    private static final String MESSAGE = "답변이 존재하지 않습니다.";
    private static final HttpStatus HTTP_STATUS = null;

    public UnAnsweredPostException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
