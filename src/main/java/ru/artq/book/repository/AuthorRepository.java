package ru.artq.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artq.book.entity.Author;
import ru.artq.book.entity.Book;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
