package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SongDtoApi {
    public String artistName;

    public String trackName;

    public String collectionName;

    public String previewUrl;

    public String artworkUrl100;

    public String primaryGenreName;
}
