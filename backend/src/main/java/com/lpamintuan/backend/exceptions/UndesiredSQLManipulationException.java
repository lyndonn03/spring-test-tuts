package com.lpamintuan.backend.exceptions;

public class UndesiredSQLManipulationException extends IndexOutOfBoundsException {
    
    public UndesiredSQLManipulationException(String msg) {
        super(msg);
    }

    public static UndesiredSQLManipulationException getInstance(String id) {
        return new UndesiredSQLManipulationException("Error updating library with id: " + id + ". Multiple rows modified. Transaction is invalid and rollbacked.");
    }

}
