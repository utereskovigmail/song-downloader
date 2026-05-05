package com.example.demo.data;

import com.example.demo.Models.ItunesResponse;
import com.example.demo.Models.SongDtoApi;
import com.example.demo.entities.Genre;
import com.example.demo.entities.Song;
import com.example.demo.repositories.IGenreRepository;
import com.example.demo.repositories.ISongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;


import javax.sound.midi.Soundbank;
import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;



import java.net.URI;

import java.net.URLEncoder;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor
public class AppSeedData {
    private final IGenreRepository genreRepository;
    private final ItunesClient itunesClient;
    private final ISongRepository songRepository;

    //Цей метод буде Seed даних у БД
    //Цей метод в java Spring буде зпускати автоматично
    @PostConstruct
    public void seed(){
        System.out.println("---------Run seed data-----------");
        List<String> list = List.of("acoustic",
                "afrobeat",
                "alt-rock",
                "alternative",
                "ambient",
                "anime",
                "black-metal",
                "bluegrass",
                "blues",
                "bossanova",
                "brazil",
                "breakbeat",
                "british",
                "cantopop",
                "chicago-house",
                "children",
                "chill",
                "classical",
                "club",
                "comedy",
                "country",
                "dance",
                "dancehall",
                "death-metal",
                "deep-house",
                "detroit-techno",
                "disco",
                "disney",
                "drum-and-bass",
                "dub",
                "dubstep",
                "edm",
                "electro",
                "electronic",
                "emo",
                "folk",
                "forro",
                "french",
                "funk",
                "garage",
                "german",
                "gospel",
                "goth",
                "grindcore",
                "groove",
                "grunge",
                "guitar",
                "happy",
                "hard-rock",
                "hardcore",
                "hardstyle",
                "heavy-metal",
                "hip-hop",
                "holidays",
                "honky-tonk",
                "house",
                "idm",
                "indian",
                "indie",
                "indie-pop",
                "industrial",
                "iranian",
                "j-dance",
                "j-idol",
                "j-pop",
                "j-rock",
                "jazz",
                "k-pop",
                "kids",
                "latin",
                "latino",
                "malay",
                "mandopop",
                "metal",
                "metal-misc",
                "metalcore",
                "minimal-techno",
                "movies",
                "mpb",
                "new-age",
                "new-release",
                "opera",
                "pagode",
                "party",
                "philippines-opm",
                "piano",
                "pop",
                "pop-film",
                "post-dubstep",
                "power-pop",
                "progressive-house",
                "psych-rock",
                "punk",
                "punk-rock",
                "r-n-b",
                "rainy-day",
                "reggae",
                "reggaeton",
                "road-trip",
                "rock",
                "rock-n-roll",
                "rockabilly",
                "romance",
                "sad",
                "salsa",
                "samba",
                "sertanejo",
                "show-tunes",
                "singer-songwriter",
                "ska",
                "sleep",
                "songwriter",
                "soul",
                "soundtracks",
                "spanish",
                "study",
                "summer",
                "swedish",
                "synth-pop",
                "tango",
                "techno",
                "trance",
                "trip-hop",
                "turkish",
                "work-out",
                "world-music");

        if(genreRepository.count() == 0){
            Faker faker = new Faker();


            List<Genre> genres = new ArrayList<>();
            for(String l: list){
                String shortDesc = faker.lorem().sentence();
                Genre genre = new Genre(l, shortDesc);
                genres.add(genre);
            }

            genreRepository.saveAll(genres);
        }





        ObjectMapper mapper = new ObjectMapper();
        if(songRepository.findAll().isEmpty()){
            try{
                List<String> songs = mapper.readValue(

                        new File("songs.json"),

                        List.class

                );

                for(String s: songs) {
                    String json = itunesClient.searchSong(s);

                    System.out.println(json);


                    ItunesResponse response =

                            mapper.readValue(json, ItunesResponse.class);

                    SongDtoApi song = response.results.get(0);

                    System.out.println(song.artistName);

                    System.out.println(song.trackName);

                    System.out.println("collection - - - - - " + song.collectionName);

                    System.out.println(song.previewUrl);

                    System.out.println("\n\n\n\n\n");





                    Pair<String, String> pair= itunesClient.getLink(s);

                    String link = pair.getFirst();
                    String imageHighQuality = pair.getSecond();

                    System.out.println("link ------ " + link);

                    String fileName = itunesClient.downloadSong(link);

                    Song song1 = new Song();
                    song1.setName(song.trackName);
                    song1.setAlbum(song.collectionName);
                    song1.setImage(imageHighQuality);
                    song1.setArtist(song.artistName);
                    song1.setAudio(fileName);

                    List<Genre> allGenres = song.primaryGenreName == null
                            ? List.of()
                            : Arrays.stream(song.primaryGenreName.split("/"))
                              .map(String::trim)
                              .map(name -> genreRepository.findByName(name)
                                           .orElseGet(() -> genreRepository.save(new Genre(name, ""))))
                              .toList();

                    song1.setGenres(allGenres);

                    songRepository.save(song1);


                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }






    }
}