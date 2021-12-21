package com.github.yaroslavskybadev.repository;

import com.github.yaroslavskybadev.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}