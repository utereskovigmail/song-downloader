package com.example.demo.controllers;



import com.example.demo.repositories.ISongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;


@RequiredArgsConstructor
@Controller
public class MusicController {
    private final ISongRepository songRepository;

//    @GetMapping("/music/{filename:.+}")
//    public Resource getMusic(@PathVariable String filename) {
//        File file = new File("music/" + filename);
//        return new FileSystemResource(file);
//    }

}
