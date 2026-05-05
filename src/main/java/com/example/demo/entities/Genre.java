package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200, nullable = false, unique = true)
    private String name;
    @Column(length = 1000)
    private String description;

    @ManyToMany(mappedBy = "genres")
    private List<Song> songs;

    public Genre(String l, String shortDesc) {
        this.name = l;
        this.description = shortDesc;
    }

    public Genre() {

    }
}
