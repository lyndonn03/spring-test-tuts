package com.lpamintuan.backend.controllers;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.models.Library;
import com.lpamintuan.backend.services.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Library createLibrary(@RequestBody Library library) {
        return libraryService.createLibrary(library);
    }

    @GetMapping
    public List<Library> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    @GetMapping("/{id}")
    public Library getLibrary(@PathVariable UUID id) throws LibraryNotFoundException {
        return libraryService.getLibraryById(id);
    }

    @PutMapping("/{id}")
    public Library updateLibrary(@PathVariable UUID id, @RequestBody Library library) throws LibraryNotFoundException {
        return libraryService.updateLibrary(id, library);
    }

    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable UUID id) throws LibraryNotFoundException {
        libraryService.deleteLibrary(id);
    }
    
}
