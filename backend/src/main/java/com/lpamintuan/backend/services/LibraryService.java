package com.lpamintuan.backend.services;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.models.Library;

public interface LibraryService {

    public List<Library> getAllLibraries();

    public Library getLibraryById(UUID id) throws LibraryNotFoundException;

    public Library updateLibrary(UUID id, Library library) throws LibraryNotFoundException;

    public void deleteLibrary(UUID id) throws LibraryNotFoundException;

    public Library createLibrary(Library library);
    
}
