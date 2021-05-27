package com.lpamintuan.backend.servicesImpl;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.exceptions.UndesiredSQLManipulationException;
import com.lpamintuan.backend.models.Library;
import com.lpamintuan.backend.repositories.LibraryRepository;
import com.lpamintuan.backend.services.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private LibraryRepository libraryRepository;

    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Override
    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    @Override
    public Library getLibraryById(UUID id) throws LibraryNotFoundException {
        Library library = libraryRepository.findById(id).orElseThrow(
            () -> LibraryNotFoundException.getInstance(id.toString())
        );
        return library;
    }

    @Override
    @Transactional(rollbackFor = UndesiredSQLManipulationException.class)
    public Library updateLibrary(UUID id, Library library) throws LibraryNotFoundException {
        if(libraryRepository.existsById(id)) {
            int numberOfUpdated = libraryRepository.updateLibraryById(id, library.getName());
            if(numberOfUpdated == 1)
             {
                 library.setId(id);
                 return library;
             }
            else throw UndesiredSQLManipulationException.getInstance(id.toString());
        }
        throw LibraryNotFoundException.getInstance(id.toString());
    }

    @Override
    public void deleteLibrary(UUID id) throws LibraryNotFoundException {
        if(!libraryRepository.existsById(id)) 
            throw LibraryNotFoundException.getInstance(id.toString());
        libraryRepository.deleteById(id);
    }

    @Override
    public Library createLibrary(Library library) {
        return libraryRepository.save(library);
    }
    
}
