package com.lpamintuan.backend.exceptions;

public class LibraryNotFoundException extends Exception {
    
    public LibraryNotFoundException(String msg) {
        super(msg);
    }

    public static LibraryNotFoundException getInstance(String id) {
        String msg = "No library with id: " + id + " found.";
        return new LibraryNotFoundException(msg);
    } 
}
