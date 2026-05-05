package com.example.demo.repositories;


import com.example.demo.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}