package com.example.demo.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String image;

    @Column(nullable = false, length = 200)
    private String artist;

    @Column(nullable = false, length = 200)
    private String album;

    @Column(nullable = false, length = 150)
    private String audio;



    @ManyToMany
    @JoinTable(
            name = "song_genres",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
}

//  "Numb",
//  "Blinding Lights",
//  "Shape of You",
//  "Bohemian Rhapsody",
//  "Billie Jean",
//  "Rolling in the Deep",
//  "Smells Like Teen Spirit",
//  "Someone Like You",
//  "In the End"
