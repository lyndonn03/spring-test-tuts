package com.lpamintuan.backend.repositories;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.ContainersForTest;
import com.lpamintuan.backend.models.Song;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SongRepositoryTest extends ContainersForTest {

    @Autowired
    SongRepository songRepository;
   
    @Test
    @DisplayName("Test Should Pass If It Saves The Song In The Database")
    public void saveSongTest() {
        Song expectedResult = new Song(UUID.randomUUID(), "TEST TITLE", "TEST ARTIST");
        Song actualResult = songRepository.save(expectedResult);
        Assertions.assertThat(actualResult).isEqualToIgnoringGivenFields(expectedResult, "id");
        Assertions.assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Should Pass If It Returns All The Songs Inserted By song.test.sql")
    @Sql("classpath:db/song.test.sql")
    public void findAllSongTest() {
        List<Song> songs = songRepository.findAll();
        Assertions.assertThat(songs.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test Should Pass If It Deletes A Song In The Database Inserted Through ssong.test.sql")
    @Sql("classpath:db/song.test.sql")
    public void deleteSongTest() {
        songRepository.deleteById(UUID.fromString("8bc5c0bc-ce44-4596-895c-a33001819175"));
        List<Song> songs = songRepository.findAll();
        Assertions.assertThat(songs.size()).isEqualTo(2);
    }

}
