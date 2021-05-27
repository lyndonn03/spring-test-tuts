package com.lpamintuan.backend.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.models.Library;
import com.lpamintuan.backend.models.LibraryContent;
import com.lpamintuan.backend.services.LibraryService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
import static org.mockito.Mockito.times;

@WebMvcTest(controllers = {LibraryController.class})
public class LibraryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void createLibraryTest() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();
        Library expectedResult = new Library(id, "TEST LIBRARY #1");

        Mockito.when(libraryService.createLibrary(any(Library.class)))
            .thenReturn(expectedResult);
        
        mockMvc.perform(post("/libraries").contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(expectedResult)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value("TEST LIBRARY #1"));
                    
    }

    @Test
    public void getAllLibrariesTest() throws Exception {
        List<Library> libraries = Arrays.asList(
            new Library(null, "TEST LIBRARY #1"),
            new Library(null, "TEST LIBRARY #2")
        );

        Mockito.when(libraryService.getAllLibraries()).thenReturn(libraries);

        mockMvc.perform(get("/libraries"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    public void getLibraryTest() throws Exception {
        Library expectedResult = new Library(null, "TEST LIBRARY");

        Mockito.when(libraryService.getLibraryById(any(UUID.class))).thenReturn(expectedResult);

        mockMvc.perform(get("/libraries/{id}", UUID.randomUUID()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("TEST LIBRARY"));

    }

    @Test
    public void getLibrary_shouldPass_ifItWillNotIncludeLibraryContents() throws Exception {

        Library expectedResult = new Library(null, "TEST LIBRARY",
            Arrays.asList(
                new LibraryContent()
            )
        );

        Mockito.when(libraryService.getLibraryById(any(UUID.class))).thenReturn(expectedResult);

        mockMvc.perform(get("/libraries/{id}", UUID.randomUUID()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("TEST LIBRARY"))
            .andExpect(jsonPath("$.contents").doesNotExist());

    }

    @Test
    public void getLibraryExceptionTest() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(libraryService.getLibraryById(id)).thenThrow(LibraryNotFoundException.getInstance(id.toString()));

        mockMvc.perform(get("/libraries/{id}", id))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("No library with id: " + id + " found."));

    }

    @Test
    public void updateLibraryTest() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();
        Library library = new Library(id, "TEST LIBRARY");

        Mockito.when(libraryService.updateLibrary(any(UUID.class), any(Library.class))).thenReturn(library);
        
        mockMvc.perform(put("/libraries/{id}", id).contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(library)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("TEST LIBRARY"));

    }

    @Test
    public void updateLibraryExceptionTest() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();
        Library library = new Library(id, "TEST LIBRARY");

        Mockito.when(libraryService.updateLibrary(any(UUID.class), any(Library.class))).thenThrow(
            LibraryNotFoundException.getInstance(id.toString())
        );
        
        mockMvc.perform(put("/libraries/{id}", id).contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(library)))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("No library with id: " + id + " found."));

    }


    @Test
    public void deleteLibraryTest() throws Exception {
        UUID id = UUID.randomUUID();
        
        mockMvc.perform(delete("/libraries/{id}", id))
            .andExpect(status().isOk());

        Mockito.verify(libraryService, times(1)).deleteLibrary(id);

    }

    @Test
    public void deleteLibraryExceptionTest() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(LibraryNotFoundException.getInstance(id.toString()))
            .when(libraryService).deleteLibrary(id);

        mockMvc.perform(delete("/libraries/{id}", id))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("No library with id: " + id + " found."))
            .andExpect(jsonPath("$.status").value(404));
    }
    
}
