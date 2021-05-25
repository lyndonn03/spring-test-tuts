package com.lpamintuan.backend.repositories;

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
    }

}
