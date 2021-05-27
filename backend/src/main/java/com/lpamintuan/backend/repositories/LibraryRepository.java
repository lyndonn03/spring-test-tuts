package com.lpamintuan.backend.repositories;

import java.util.UUID;

import com.lpamintuan.backend.models.Library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LibraryRepository extends JpaRepository<Library, UUID> {

    @Modifying
    @Query(value = "UPDATE library SET name = :name WHERE id = :id", nativeQuery=true)
    int updateLibraryById(UUID id, String name);
    
}
