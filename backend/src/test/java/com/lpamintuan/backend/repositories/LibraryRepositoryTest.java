package com.lpamintuan.backend.repositories;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.ContainersForTest;
import com.lpamintuan.backend.exceptions.LibraryNotFoundException;
import com.lpamintuan.backend.models.Library;
import com.lpamintuan.backend.models.LibraryContent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LibraryRepositoryTest extends ContainersForTest {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private  LibraryContentRepository libraryContentRepository;

    @Autowired
    TestEntityManager testEntityManager;
    

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

    @Test
    @Sql("classpath:db/librarycontent.test.sql")
    public void deleteById_shouldPass_ifItAlsoDeletesLibraryContent() {
        UUID id = UUID.fromString("36ab2b24-a27d-4d58-a92f-bc4cdd96853b");

        libraryRepository.deleteById(id);
        testEntityManager.flush();
        Library library = testEntityManager.find(Library.class, id);

        List<LibraryContent> contents = libraryContentRepository.findAllByLibraryId(id);

        Assertions.assertThat(library).isNull();
        Assertions.assertThat(contents.size()).isEqualTo(0);

    }
}
