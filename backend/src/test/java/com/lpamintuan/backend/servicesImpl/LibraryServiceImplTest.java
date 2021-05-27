package com.lpamintuan.backend.servicesImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.exceptions.UndesiredSQLManipulationException;
import com.lpamintuan.backend.models.Library;
import com.lpamintuan.backend.models.LibraryContent;
import com.lpamintuan.backend.repositories.LibraryRepository;
import com.lpamintuan.backend.services.LibraryService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceImplTest {

    LibraryService libraryService;

    @Mock
    private LibraryRepository libraryRepository;


    @BeforeEach
    void initSongService() {
        this.libraryService = new LibraryServiceImpl(libraryRepository);
    }

    @Test
    @DisplayName("Test Should Pass If It Returns The Newly Created Library Object")
    public void createLibrary() {
        Library expectedResult = new Library(UUID.randomUUID(), "TEST LIBRARY #1");
        Mockito.when(libraryRepository.save(any(Library.class))).thenReturn(expectedResult);

        Library actualResult = libraryService.createLibrary(expectedResult);

        Assertions.assertThat(actualResult).isEqualToComparingFieldByFieldRecursively(expectedResult);
    }

    @Test
    public void createLibrary_shouldPass_ifItReturnsNewLyCreatedObject_withLibraryContents() {
        Library expectedResult = new Library(UUID.randomUUID(), "TEST LIBRARY #1", 
            Arrays.asList(new LibraryContent())
        );
        Mockito.when(libraryRepository.save(any(Library.class))).thenReturn(expectedResult);

        Library actualResult = libraryService.createLibrary(expectedResult);

        Assertions.assertThat(actualResult.getContents().size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Test Should Pass If It Returns List of Library When findAll() Is Called")
    public void getLibrariesTest() {
        List<Library> expectedLibraries = Arrays.asList(
            new Library(null, "TEST LIBRARY #1"),
            new Library(null, "TEST LIBRARY #2")
        );
        Mockito.when(libraryRepository.findAll()).thenReturn(expectedLibraries);

        List<Library> actualLibraries = libraryService.getAllLibraries();

        Assertions.assertThat(actualLibraries.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test Should Pass If It Returns The Library When findById() Is Called")
    public void getLibraryTest() throws LibraryNotFoundException {
        Library expectedResult = new Library(null, "TEST NAME");
        Mockito.when(libraryRepository.findById(any(UUID.class))).thenReturn(Optional.of(expectedResult));

        Library actualResult = libraryService.getLibraryById(UUID.randomUUID());

        Assertions.assertThat(actualResult).isEqualToIgnoringGivenFields(expectedResult, "id");

    }

    @Test
    @DisplayName("Test Should Pass If It Throws LibraryNotFoundException If findById() Returns Null")
    public void getLibraryTestException() {
        UUID id = UUID.randomUUID();
        Mockito.when(libraryRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            libraryService.getLibraryById(id);
        }).isInstanceOf(LibraryNotFoundException.class)
        .hasMessage("No library with id: " + id + " found.");

    }

    @Test
    @DisplayName("Test Should Pass If It Returns The Updated Library Object")
    public void updateLibraryTest() throws LibraryNotFoundException {
        UUID id = UUID.randomUUID();
        Library expectedResult = new Library(null, "TEST NAME");
        Mockito.when(libraryRepository.existsById(id)).thenReturn(true);
        Mockito.when(libraryRepository.updateLibraryById(any(UUID.class), anyString())).thenReturn(1);

        Library actualResult = libraryService.updateLibrary(id, expectedResult);

        Assertions.assertThat(actualResult).isEqualToIgnoringGivenFields(expectedResult, "id");
        Assertions.assertThat(actualResult.getId()).isEqualTo(id);

    }

    @Test
    @DisplayName("Test Should Pass If It Throws LibraryNotFoundException When No Library Found To Update")
    public void updateLibraryExceptionTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(libraryRepository.existsById(any(UUID.class))).thenReturn(false);

        Assertions.assertThatThrownBy(() -> {
            libraryService.updateLibrary(id, new Library());
        }).isInstanceOf(LibraryNotFoundException.class)
        .hasMessage("No library with id: " + id + " found.");

    }

    @Test
    @DisplayName("Test Should Pass If It Throws UndesiredNotFoundException When There Are Multiple Rows Modified")
    public void updateLibrarySQLExceptionTest() {
        UUID id = UUID.randomUUID();
        Library expectedResult = new Library(null, "TEST NAME");
        Mockito.when(libraryRepository.existsById(id)).thenReturn(true);
        Mockito.when(libraryRepository.updateLibraryById(any(UUID.class), anyString())).thenReturn(3);

        Assertions.assertThatThrownBy(() -> {
            libraryService.updateLibrary(id, expectedResult);
        }).isInstanceOf(UndesiredSQLManipulationException.class)
        .hasMessage("Error updating library with id: " + id + ". Multiple rows modified. Transaction is invalid and rollbacked.");
    }

    @Test
    @DisplayName("Test Should Pass If It Calls deleteById() Method Successfully")
    public void deleteLibraryTest() throws LibraryNotFoundException {
        UUID id = UUID.randomUUID();
        Mockito.when(libraryRepository.existsById(any(UUID.class))).thenReturn(true);

        libraryService.deleteLibrary(id);

        Mockito.verify(libraryRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Test Should Pass If It Throws LibraryNotFoundException When No Library Found To Delete")
    public void deleteLibraryExceptionTest() {
        UUID id = UUID.randomUUID();
        Mockito.when(libraryRepository.existsById(any(UUID.class))).thenReturn(false);

        Assertions.assertThatThrownBy(() -> {
            libraryService.deleteLibrary(id);
        }).isInstanceOf(LibraryNotFoundException.class)
        .hasMessage("No library with id: " + id + " found.");
    }
    
}
