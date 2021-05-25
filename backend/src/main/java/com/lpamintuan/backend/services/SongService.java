package com.lpamintuan.backend.services;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.Song;

public interface SongService {

    public List<Song> getAllSongs();

    public Song getSongById(UUID randomUUID) throws SongNotFoundException;

    public Song saveSong(Song song);

    public Song updateSong(UUID id, Song song) throws SongNotFoundException;

    public void deleteSong(UUID id) throws SongNotFoundException;
    
}
