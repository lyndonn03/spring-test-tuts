package com.lpamintuan.backend.globals;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApiErrorResponse {

    private Timestamp timestamp;
    private String message;
    private int status;


    public ApiErrorResponse(String message, int status) {
        this.timestamp = new Timestamp(new Date().getTime());
        this.message = message;
        this.status = status;
    }
    
}
