package com.lpamintuan.backend.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.Song;
import com.lpamintuan.backend.services.SongService;

import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.times;

@WebMvcTest(controllers = SongController.class)
public class SongControllerTest {
    
    @MockBean
    private SongService songService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Test Should Pass If It Returns Status 201 And The Song Object")
    public void createSongTest() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();
        Song song = new Song(id, "TEST SONG #1", "TEST ARTIST #1");

        Mockito.when(songService.saveSong(any(Song.class))).thenReturn(song);

        mockMvc.perform(post("/songs").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(song)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("TEST SONG #1"))
                .andExpect(jsonPath("$.artist").value("TEST ARTIST #1"));
    }

    @Test
    @DisplayName("Test Should Pass If It Has Status 200 And All Songs Included")
    public void getAlLSongsTest() throws Exception {

        List<Song> expectedResult = Arrays.asList(
            new Song(null, "TEST SONG #1", "TEST ARTIST #1"),
            new Song(null, "TEST SONG #2", "TEST ARTIST #2")
        );
        Mockito.when(songService.getAllSongs()).thenReturn(expectedResult);

        mockMvc.perform(get("/songs/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Test Should Pass If It Has Status 200 And The Song Object Included")
    public void getSongTest() throws Exception {
        Song expectedResult = new Song(UUID.randomUUID(), "TEST SONG #1", "TEST ARTIST #1");
        Mockito.when(songService.getSongById(expectedResult.getId())).thenReturn(expectedResult);

        mockMvc.perform(get("/songs/{id}", expectedResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("TEST SONG #1"));

    }

    @Test
    @DisplayName("Test Should Pass If It Has Status 404 Not Found")
    public void getSongTestException() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(songService.getSongById(any(UUID.class)))
                .thenThrow(SongNotFoundException.getInstance(id.toString()));

        mockMvc.perform(get("/songs/{id}", id))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("No song with id: " + id + " found."));
    }

    @Test
    @DisplayName("Test Should Pass If It Has Status 200 With The Updated Song Object")
    public void updateSongTest() throws JsonProcessingException, Exception {

        UUID id = UUID.randomUUID();
        Song songToBeUpdated = new Song(id, "TEST SONG #1", "TEST ARTIST #1");
        Mockito.when(songService.updateSong(any(UUID.class), any(Song.class))).thenReturn(songToBeUpdated);

        mockMvc.perform(put("/songs/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(songToBeUpdated)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()));
        
    }

    @Test
    @DisplayName("Test Should Pass If It Has Status 404 Not Found")
    public void updateSongTestException() throws JsonProcessingException, Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(songService.updateSong(any(UUID.class), any(Song.class)))
            .thenThrow(SongNotFoundException.getInstance(id.toString()));

        mockMvc.perform(put("/songs/{id}", id).contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(new Song())))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("No song with id: " + id + " found."))
                    .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("Test Should Pass If It Has Status 200 And Called DeleteSong Successfully")
    public void deleteSongTest() throws Exception {
        UUID id = UUID.randomUUID();
        
        mockMvc.perform(delete("/songs/{id}", id))
            .andExpect(status().isOk());

        Mockito.verify(songService, times(1)).deleteSong(id);
    }

    @Test
    @DisplayName("Test Should Pass If It Has Status 404 Not Found While DeleteSong Is Called")
    public void deleteSongExceptionTest() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(SongNotFoundException.getInstance(id.toString()))
            .when(songService).deleteSong(id);

        mockMvc.perform(delete("/songs/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("No song with id: " + id + " found."))
                .andExpect(jsonPath("$.status").value(404));
    }



}
