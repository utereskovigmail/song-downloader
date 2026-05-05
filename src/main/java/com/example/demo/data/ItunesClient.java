package com.example.demo.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import java.util.regex.Matcher;

import java.util.regex.Pattern;
@Service
public class ItunesClient {
    private static final String API_KEY = "AIzaSyBu24FkfGdUVgEIEI7npuT_dOiVkxhCYsI";
    public String searchSong(String song) {
        try{
            String encodedSong = URLEncoder.encode(song, StandardCharsets.UTF_8);

            String url = "https://itunes.apple.com/search?term="

                    + encodedSong

                    + "&entity=song&limit=1";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()

                    .uri(URI.create(url))

                    .GET()

                    .build();

            HttpResponse<String> response =

                    client.send(request, HttpResponse.BodyHandlers.ofString());

//            System.out.println(response.body());

            return response.body();

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public Pair<String, String> getLink(String song) {

        try {

            String url = "https://www.googleapis.com/youtube/v3/search"

                    + "?part=snippet"

                    + "&q=" + java.net.URLEncoder.encode(song, java.nio.charset.StandardCharsets.UTF_8)

                    + "&type=video"

                    + "&maxResults=1"

                    + "&key=" + API_KEY;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()

                    .uri(URI.create(url))

                    .GET()

                    .build();

            HttpResponse<String> response =

                    client.send(request, HttpResponse.BodyHandlers.ofString());


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.body());

            String url111 = new ObjectMapper()

                    .readTree(response.body())

                    .at("/items/0/snippet/thumbnails/high/url")

                    .asText();

//            System.out.println(response.body());
            if(response.statusCode() == 200) {
                String videoId = root.path("items").get(0)

                        .path("id")

                        .path("videoId")

                        .asText();

                String link = "https://youtube.com/watch?v=" + videoId;
                return Pair.of(link, url111);
            }


            return null;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    public String downloadSong(String link) {

        try {

            new File("music").mkdirs();

            ProcessBuilder pb = new ProcessBuilder(

                    "yt-dlp",

                    "-x",

                    "--audio-format", "mp3",

                    "-o", "music/%(title)s.%(ext)s",

                    link

            );

            pb.redirectErrorStream(true);

            Process process = pb.start();

            // read output (important for debugging)
            String downloadedFile = "";

            try (BufferedReader reader = new BufferedReader(

                    new InputStreamReader(process.getInputStream()))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    System.out.println(line);

                    if (line.contains("Destination:")) {

                        String path = line.substring(

                                line.indexOf("Destination:") + "Destination:".length()

                        ).trim();

                        String fileName = new File(path).getName();

                        System.out.println(fileName);

                        downloadedFile = fileName;
                        System.out.println(downloadedFile);

                    }

                }

            }

            int exitCode = process.waitFor();

            System.out.println("Finished with code: " + exitCode);
            return downloadedFile;

        } catch (Exception e) {

            e.printStackTrace();
            return "error";

        }

    }
}
