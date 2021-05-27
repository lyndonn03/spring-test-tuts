package com.lpamintuan.backend.servicesImpl;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.Library;
import com.lpamintuan.backend.models.LibraryContent;
import com.lpamintuan.backend.models.LibraryContentkey;
import com.lpamintuan.backend.models.Song;
import com.lpamintuan.backend.repositories.LibraryContentRepository;
import com.lpamintuan.backend.repositories.LibraryRepository;
import com.lpamintuan.backend.repositories.SongRepository;
import com.lpamintuan.backend.services.LibraryContentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryContentServiceImpl implements LibraryContentService {

    private LibraryContentRepository libraryContentRepository;
    private LibraryRepository libraryRepository;
    private SongRepository songRepository;

    @Autowired
    public LibraryContentServiceImpl(LibraryContentRepository libraryContentRepository, LibraryRepository libraryRepository, SongRepository songRepository) {
        this.libraryContentRepository = libraryContentRepository;
        this.libraryRepository = libraryRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<LibraryContent> getAllLibraryContentsById(UUID libraryId) throws LibraryNotFoundException {
        if(!libraryRepository.existsById(libraryId))
            throw LibraryNotFoundException.getInstance(libraryId.toString());
        return libraryContentRepository.findAllByLibraryId(libraryId);
    }

    @Override
    public LibraryContent createContentInLibrary(UUID libraryId, Song song) throws LibraryNotFoundException, SongNotFoundException {

        Library library = libraryRepository.findById(libraryId).orElseThrow(
            () -> LibraryNotFoundException.getInstance(libraryId.toString())
        );

        Song songToInsert = songRepository.findById(song.getId()).orElseThrow(
            () -> SongNotFoundException.getInstance(song.getId().toString())
        );

        LibraryContentkey key = new LibraryContentkey(library.getId(), songToInsert.getId());
        LibraryContent newContent = new LibraryContent(key, songToInsert, library);
        
        return libraryContentRepository.save(newContent);
    }

    
}
