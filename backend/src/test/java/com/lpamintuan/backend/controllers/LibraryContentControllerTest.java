package com.lpamintuan.backend.controllers;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.Library;
import com.lpamintuan.backend.models.LibraryContent;
import com.lpamintuan.backend.models.LibraryContentkey;
import com.lpamintuan.backend.models.Song;
import com.lpamintuan.backend.services.LibraryContentService;

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
// import static org.mockito.Mockito.times;

@WebMvcTest(controllers = {LibraryContentController.class})
public class LibraryContentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryContentService libraryContentService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getAllLibraryContents_shouldPassIfStatus200() throws Exception {

        UUID id = UUID.randomUUID();
        List<LibraryContent> expectedResults = Arrays.asList(
            new LibraryContent(new Song(), new Library()),
            new LibraryContent(new Song(), new Library())
        );

        Mockito.when(libraryContentService.getAllLibraryContentsById(any(UUID.class))).thenReturn(expectedResults);

        mockMvc.perform(get("/libraries/{id}/contents", id))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllLibraryContents_shouldPass_ifStatus404() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(libraryContentService.getAllLibraryContentsById(any(UUID.class)))
            .thenThrow(LibraryNotFoundException.getInstance(id.toString()));

        mockMvc.perform(get("/libraries/{id}/contents", id))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404));
            
    }

    @Test
    public void addContentToLibrary_shouldPass_ifStatus201() throws JsonProcessingException, Exception {
        LibraryContent expectedResult = new LibraryContent(
            new Song(),
            new Library()
        );
        Mockito.when(libraryContentService.createContentInLibrary(any(UUID.class), any(Song.class)))
            .thenReturn(expectedResult);

        mockMvc.perform(post("/libraries/{id}/contents", UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(expectedResult)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void addContentTolLibrary_shouldPass_ifStatus201_and_responseHasSongDetail() throws JsonProcessingException, Exception {
        LibraryContent expectedResult = new LibraryContent(
            new LibraryContentkey(),
            new Song(),
            new Library()
        );
        Mockito.when(libraryContentService.createContentInLibrary(any(UUID.class), any(Song.class)))
            .thenReturn(expectedResult);

        mockMvc.perform(post("/libraries/{id}/contents", UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(expectedResult)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.key").exists())
            .andExpect(jsonPath("$.song").exists())
            .andExpect(jsonPath("$.library").doesNotExist());
    }

    @Test
    public void addContentToLibrary_shouldPass_ifStatus404_LibraryNotFoundException() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(libraryContentService.createContentInLibrary(any(UUID.class), any(Song.class)))
            .thenThrow(LibraryNotFoundException.getInstance(id.toString()));

        mockMvc.perform(post("/libraries/{id}/contents", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(new Song())))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("No library with id: " + id + " found."));
    }

    @Test
    public void addContentToLibrary_shouldPass_ifStatus404_SongNotFoundException() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(libraryContentService.createContentInLibrary(any(UUID.class), any(Song.class)))
            .thenThrow(SongNotFoundException.getInstance(id.toString()));

        mockMvc.perform(post("/libraries/{id}/contents", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(new Song())))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("No song with id: " + id + " found."));
    }

    
}
