package ru.artq.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artq.book.entity.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {
}
