package com.example.demo.exceptions;

public class RegionNotFoundException extends RuntimeException {
    private long id;

    public RegionNotFoundException(String message, long id) {
        super(message + id);
        this.id = id;
    }

    public RegionNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "RegionNotFoundException{" +
                "id=" + id +
                '}';
    }
}