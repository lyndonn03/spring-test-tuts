package com.lpamintuan.backend.globals;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.exceptions.SongNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {SongNotFoundException.class, LibraryNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ApiErrorResponse handleNotFoundExceptions(Exception e) {
        return new ApiErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
    
}
