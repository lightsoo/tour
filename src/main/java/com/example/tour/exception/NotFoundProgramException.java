package com.example.tour.exception;

public class NotFoundProgramException extends RuntimeException {
    private static final long serialVersionUID = 7274318503028241642L;

    public NotFoundProgramException(Integer programId) {
        super("Not found program id: " + programId);
    }
}
