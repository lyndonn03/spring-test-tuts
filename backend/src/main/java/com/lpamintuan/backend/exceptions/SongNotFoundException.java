package com.lpamintuan.backend.exceptions;

public class SongNotFoundException extends Exception {
    
    public SongNotFoundException(String msg) {
        super(msg);
    }

    public static SongNotFoundException getInstance(String id) {
        String msg = "No song with id: " + id + " found.";
        return new SongNotFoundException(msg);
    }
    
}
