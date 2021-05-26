package com.lpamintuan.backend.controllers;

import java.util.List;
import java.util.UUID;

import com.lpamintuan.backend.exceptions.SongNotFoundException;
import com.lpamintuan.backend.models.Song;
import com.lpamintuan.backend.services.SongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Song createSong(@RequestBody Song song) {
        return songService.saveSong(song);
    }
    
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public Song getSong(@PathVariable(name = "id") UUID songId) throws SongNotFoundException {
        return songService.getSongById(songId);
    }

    @PutMapping("/{id}")
    public Song updateSong(@PathVariable(name = "id") UUID songId, @RequestBody Song song) throws SongNotFoundException {
        return songService.updateSong(songId, song);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable(name = "id") UUID songId) throws SongNotFoundException {
        songService.deleteSong(songId);
    }

}
