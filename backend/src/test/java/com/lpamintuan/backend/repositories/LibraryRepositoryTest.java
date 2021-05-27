package com.lpamintuan.backend.repositories;

import java.util.UUID;

import com.lpamintuan.backend.ContainersForTest;
import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.models.Library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LibraryRepositoryTest extends ContainersForTest {

    @Autowired
    private LibraryRepository libraryRepository;
    

    @Test
    @Sql("classpath:db/library.test.sql")
    public void updateLibraryByIdTest() throws LibraryNotFoundException {
        UUID id = UUID.fromString("36ab2b24-a27d-4d58-a92f-bc4cdd96853b");

        int actualResult = libraryRepository.updateLibraryById(id, "TEST LIBRARY UPDATE");
        Library actualResultObject = libraryRepository.findById(id)
            .orElseThrow(
                () -> LibraryNotFoundException.getInstance(id.toString())
            );

        Assertions.assertThat(actualResult).isEqualTo(1);
        Assertions.assertThat(actualResultObject.getName()).isEqualTo("TEST LIBRARY UPDATE");
    }
}
