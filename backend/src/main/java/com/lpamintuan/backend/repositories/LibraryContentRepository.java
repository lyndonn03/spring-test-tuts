package com.lpamintuan.backend.repositories;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.models.LibraryContent;
import com.lpamintuan.backend.models.LibraryContentkey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryContentRepository extends JpaRepository<LibraryContent, LibraryContentkey> {

    public List<LibraryContent> findAllByLibraryId(UUID libraryId);

}
