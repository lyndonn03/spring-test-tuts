package com.lpamintuan.backend.servicesImpl;


import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.Song;
import com.lpamintuan.backend.repositories.SongRepository;
import com.lpamintuan.backend.services.SongService;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SongServiceImplTest {

    private SongService songService;
    
    @Mock
    private SongRepository songRepository;

    @BeforeEach
    void initSongService() {
        this.songService = new SongServiceImpl(songRepository);
    }

    @Test
    @DisplayName("Test Should Pass When It Gets All The Songs In The SongRepository")
    void getAllSongsTest() {
        Song song1 = new Song(null, "TITLE #1", "ARTIST #1");
        Song song2 = new Song(null, "TITLE #2", "ARTIST #2");
        Mockito.when(songRepository.findAll()).thenReturn(Arrays.asList(song1, song2));

        List<Song> songs = songService.getAllSongs();

        Assertions.assertThat(songs.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test Should Pass When It Gets The Specific Song Using Id")
    void getSongTest() throws SongNotFoundException {
        Song expectedSong = new Song(UUID.randomUUID(), "TEST SONG", "TEST ARTIST");
        Mockito.when(songRepository.findById(any(UUID.class))).thenReturn(Optional.of(expectedSong));

        Song actualSong = songService.getSongById(UUID.randomUUID());

        Assertions.assertThat(actualSong).isEqualToComparingFieldByFieldRecursively(expectedSong);
    }

    @Test
    @DisplayName("Test Should Pass When It Throws SongNotFoundException If No Song Found")
    void getSongTestException() {
        UUID id = UUID.randomUUID();

        Mockito.when(songRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> {
            songService.getSongById(id);
        }).isInstanceOf(SongNotFoundException.class)
        .hasMessageContaining("No song with id: " + id + " found.");
    }

    @Test
    @DisplayName("Test Should Pass When It Returns A Song Object When save() Is Called")
    void saveSongTest() {
        Song expectedResult = new Song(null, "TEST SONG", "TEST ARTIST");
        Mockito.when(songRepository.save(any(Song.class))).thenReturn(expectedResult);

        Song actualResult = songService.saveSong(expectedResult);

        Assertions.assertThat(actualResult)
            .isEqualToIgnoringGivenFields(expectedResult, "id");
        
    }

    @Test
    @DisplayName("Test Should Pass When It Returns The Updated Song Object When save() Is Called")
    void updateSongTest() throws SongNotFoundException {
        UUID id = UUID.randomUUID();
        Song expectedResult = new Song(null, "TEST SONG", "TEST ARTIST");
        Mockito.when(songRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(songRepository.save(any(Song.class))).thenReturn(expectedResult);

        Song actualResult = songService.updateSong(id, expectedResult);

        Assertions.assertThat(actualResult)
            .isEqualToIgnoringGivenFields(expectedResult, "id");
        Assertions.assertThat(actualResult.getId()).isEqualTo(id);

    }

    @Test
    @DisplayName("Test Should Pass When It Throws SongNotFoundException While Updating If No Song With Id Found")
    void updateSongTestException() {
        UUID id = UUID.randomUUID();
        Mockito.when(songRepository.existsById(any(UUID.class))).thenReturn(false);
        
        Assertions.assertThatThrownBy(() -> {
            songService.updateSong(id, new Song());
        }).isInstanceOf(SongNotFoundException.class)
        .hasMessageContaining("No song with id: " + id + " found.");
    }

    @Test
    @DisplayName("Test Should Pass When It Calls The delete() Function 1 Time")
    void deleteSongTest() throws SongNotFoundException {
        UUID id = UUID.randomUUID();
        Mockito.when(songRepository.existsById(any(UUID.class))).thenReturn(true);

        songService.deleteSong(id);
        Mockito.verify(songRepository, Mockito.times(1)).deleteById(id);
    }


    @Test
    @DisplayName("Test Should Pass When It Returns SongNotFoundException While Deleting If No Song With Id Found")
    void deleteSongTestException() {
        UUID id = UUID.randomUUID();
        Mockito.when(songRepository.existsById(any(UUID.class))).thenReturn(false);

        Assertions.assertThatThrownBy(() -> {
            songService.deleteSong(id);
        }).isInstanceOf(SongNotFoundException.class)
        .hasMessageContaining("No song with id: " + id + " found.");
    }
    
}
