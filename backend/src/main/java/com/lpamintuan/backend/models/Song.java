package com.lpamintuan.backend.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "song")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    @Id
    private UUID id;

    private String title;

    private String artist;

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID();
    }
    
}
