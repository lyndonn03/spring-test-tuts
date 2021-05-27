package com.lpamintuan.backend.services;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.LibraryContent;
import com.lpamintuan.backend.models.Song;

public interface LibraryContentService {

    public List<LibraryContent> getAllLibraryContentsById(UUID libraryId) throws LibraryNotFoundException;

    public LibraryContent createContentInLibrary(UUID libraryId, Song song) throws LibraryNotFoundException, SongNotFoundException;
 
}
