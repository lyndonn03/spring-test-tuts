package com.lpamintuan.backend.servicesImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LibraryContentServiceImplTest {

    @Mock
    private LibraryContentRepository libraryContentRepository;

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private SongRepository songRepository;

    private LibraryContentService libraryContentService;


    @BeforeEach
    public void autowire() {
        this.libraryContentService = new LibraryContentServiceImpl(libraryContentRepository, libraryRepository, songRepository);
    }

    @Test
    public void getAllLibraryContentsById_shouldPass_ifItReturnsAllContentsWithTheLibraryId() throws LibraryNotFoundException {
        List<LibraryContent> expectedResults = Arrays.asList(
            new LibraryContent(new Song(), new Library()),
            new LibraryContent(new Song(), new Library()),
            new LibraryContent(new Song(), new Library())
        );

        Mockito.when(libraryRepository.existsById(any(UUID.class))).thenReturn(true);

        Mockito.when(libraryContentRepository.findAllByLibraryId(any(UUID.class)))
            .thenReturn(expectedResults);

        List<LibraryContent> actualResults = libraryContentService.getAllLibraryContentsById(UUID.randomUUID());        

        Assertions.assertThat(actualResults.size()).isEqualTo(3);

    }

    @Test
    public void getAllLibraryContentsById_shouldPass_ifItThrowsLibraryNotFoundException() {

        UUID id = UUID.randomUUID();
        Mockito.when(libraryRepository.existsById(any(UUID.class))).
            thenReturn(false);
        
        Assertions.assertThatThrownBy(() -> {
            libraryContentService.getAllLibraryContentsById(id);
        }).isInstanceOf(LibraryNotFoundException.class)
        .hasMessage("No library with id: " + id + " found.");

    }

    @Test
    public void createContentInLibrary_shouldPass_ifItReturnsTheNewlyCreatedContent() throws LibraryNotFoundException, SongNotFoundException {
        UUID id = UUID.randomUUID();
        Song song = new Song(id, "TEST SONG", "TEST ARTIST");
        Library library = new Library(id, "TEST LIBRARY");
        LibraryContentkey key = new LibraryContentkey(library.getId() ,song.getId());
        LibraryContent expectedResult = new LibraryContent(key, song, library);

        Mockito.when(libraryContentRepository.save(any(LibraryContent.class)))
            .thenReturn(expectedResult);
        Mockito.when(libraryRepository.findById(any(UUID.class))).thenReturn(Optional.of(library));
        Mockito.when(songRepository.findById(any(UUID.class))).thenReturn(Optional.of(song));

        LibraryContent actualResult = libraryContentService.createContentInLibrary(id, song);

        Mockito.verify(libraryRepository, times(1)).findById(id);
        Mockito.verify(songRepository, times(1)).findById(song.getId());
        
        Assertions.assertThat(actualResult.getSong()).isEqualToComparingFieldByFieldRecursively(expectedResult.getSong());
        Assertions.assertThat(actualResult.getLibrary()).isEqualToComparingFieldByFieldRecursively(library);
        Assertions.assertThat(actualResult.getKey().getSongId()).isEqualTo(song.getId());
        Assertions.assertThat(actualResult.getKey().getLibraryId()).isEqualTo(library.getId());

    }

    @Test
    public void createContentInLibrary_shouldPass_ifItReturnsLibraryNotFoundException() {
        UUID id = UUID.randomUUID();

        Mockito.when(libraryRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            libraryContentService.createContentInLibrary(id, new Song());
        }).isInstanceOf(LibraryNotFoundException.class)
        .hasMessage("No library with id: " + id + " found.");
       
    }

    @Test
    public void createContentInLibrary_shouldPass_ifItReturnsSongNotFoundException() throws LibraryNotFoundException {
        UUID id = UUID.randomUUID();

        Mockito.when(libraryRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Library()));
        Mockito.when(songRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            libraryContentService.createContentInLibrary(id, new Song(id, null, null));
        }).isInstanceOf(SongNotFoundException.class)
        .hasMessage("No song with id: " + id + " found.");
    }

    
}
