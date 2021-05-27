package com.lpamintuan.backend.controllers;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.LibraryContent;
import com.lpamintuan.backend.models.Song;
import com.lpamintuan.backend.services.LibraryContentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libraries")
public class LibraryContentController {

    @Autowired
    LibraryContentService libraryContentService;

    @GetMapping("/{id}/contents")
    public List<LibraryContent> getAllLibraryContentsByLibraryId(@PathVariable UUID id) throws LibraryNotFoundException {
        return libraryContentService.getAllLibraryContentsById(id);
    }

    @PostMapping("/{id}/contents")
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryContent createLibraryContent(@PathVariable UUID id, @RequestBody Song song) throws LibraryNotFoundException, SongNotFoundException {
        return libraryContentService.createContentInLibrary(id, song);
    }
    
}
