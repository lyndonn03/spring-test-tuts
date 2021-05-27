package com.lpamintuan.backend.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    
    @Id
    private UUID id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library", orphanRemoval = true)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<LibraryContent> contents;

    public Library(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Library(String name) {
        this.name = name;
    }


    @PrePersist
    private void setId() {
        this.id = UUID.randomUUID();
    }

}
