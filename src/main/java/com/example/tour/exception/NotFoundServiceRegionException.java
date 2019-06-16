package com.example.tour.exception;

public class NotFoundServiceRegionException extends RuntimeException {
    private static final long serialVersionUID = -7763497242866602403L;

    public NotFoundServiceRegionException(String name) {
        super("Not found service region: " + name);
    }
}
