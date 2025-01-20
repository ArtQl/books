package ru.artq.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artq.book.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
