package com.lpamintuan.backend.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LibraryContentkey implements Serializable {

    @Column(name = "song_id")
    private UUID songId;

    @Column(name = "library_id")
    private UUID libraryId;


    
}
