package com.github.yaroslavskybadev.repository;

import com.github.yaroslavskybadev.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}