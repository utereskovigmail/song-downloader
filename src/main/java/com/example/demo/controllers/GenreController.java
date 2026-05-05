package com.example.demo.controllers;


import com.example.demo.entities.Genre;
import com.example.demo.repositories.IGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class GenreController {
    private final IGenreRepository genreRepository;


    @GetMapping("/genres/add")
    public String showAddForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "add-genre";
    }

    @PostMapping("/genres")
    public String saveGenre(@ModelAttribute Genre genre) {
        genreRepository.save(genre);
        return "redirect:/";
    }
}
