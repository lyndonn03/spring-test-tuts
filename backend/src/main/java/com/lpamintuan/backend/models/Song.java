package com.lpamintuan.backend.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    @Id
    private UUID id;

    private String name;

    private String artist;
    
}
