package com.example.demo.controllers;

import com.example.demo.repositories.ISongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.beans.ConstructorProperties;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ISongRepository songRepository;

    @GetMapping("/")
    public String Index(Model model) {

        var songs = songRepository.findAll();

        model.addAttribute("songs", songs);

        return "index";

    }
}