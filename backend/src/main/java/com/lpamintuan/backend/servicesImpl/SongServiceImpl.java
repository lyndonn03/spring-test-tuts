package com.lpamintuan.backend.servicesImpl;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.Song;
import com.lpamintuan.backend.repositories.SongRepository;
import com.lpamintuan.backend.services.SongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

    private SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song getSongById(UUID songId) throws SongNotFoundException {
        Song song = songRepository.findById(songId).orElseThrow(
            () ->  SongNotFoundException.getInstance(songId.toString())
        );
        return song;
    }

    @Override
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song updateSong(UUID songId, Song song) throws SongNotFoundException {
        if(!songRepository.existsById(songId))
            throw SongNotFoundException.getInstance(songId.toString());
        song.setId(songId);
        return songRepository.save(song);
    }

    @Override
    public void deleteSong(UUID songId) throws SongNotFoundException {
        if(!songRepository.existsById(songId)) 
            throw SongNotFoundException.getInstance(songId.toString());
        songRepository.deleteById(songId);
    }
    
}
