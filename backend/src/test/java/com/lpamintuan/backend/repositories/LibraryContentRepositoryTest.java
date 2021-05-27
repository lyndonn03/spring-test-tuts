package com.lpamintuan.backend.repositories;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.ContainersForTest;
import com.lpamintuan.backend.models.LibraryContent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LibraryContentRepositoryTest extends ContainersForTest {

    @Autowired
    private LibraryContentRepository libraryContentRepository;


    @Test
    public void setUp_test() {

    }

    @Test
    @Sql("classpath:db/librarycontent.test.sql")
    public void findAllByLibraryId_shouldPass_ifItReturnsLibraryContentWithLibraryId() {
        
        UUID id = UUID.fromString("36ab2b24-a27d-4d58-a92f-bc4cdd96853b");
        List<LibraryContent> contents = libraryContentRepository.findAllByLibraryId(id);

        Assertions.assertThat(contents.size()).isEqualTo(2);
        
    }
    
}
