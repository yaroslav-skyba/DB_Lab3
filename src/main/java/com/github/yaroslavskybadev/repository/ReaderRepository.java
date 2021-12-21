package com.github.yaroslavskybadev.repository;

import com.github.yaroslavskybadev.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}