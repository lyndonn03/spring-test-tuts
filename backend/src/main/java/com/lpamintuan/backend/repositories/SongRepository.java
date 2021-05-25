package com.lpamintuan.backend.repositories;

import java.util.UUID;

import com.lpamintuan.backend.models.Song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {
    
}
