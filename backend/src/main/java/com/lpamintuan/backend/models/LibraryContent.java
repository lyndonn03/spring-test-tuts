package com.lpamintuan.backend.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryContent {

    @EmbeddedId
    public LibraryContentkey key;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;
    
    @ManyToOne
    @MapsId("libraryId")
    @JoinColumn(name = "library_id")
    @JsonIgnore
    private Library library;

    public LibraryContent(Song song, Library library) {
        this.song = song;
        this.library = library;
    }

  
    
}
