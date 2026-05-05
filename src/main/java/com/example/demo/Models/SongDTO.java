package com.example.demo.Models;

import com.example.demo.entities.Genre;
import jakarta.persistence.*;

import java.util.List;

public class SongDTO {
    private String name;

    private String image;

    private String artist;

    private String album;

    private String audio;

    private List<Genre> genres;
}
