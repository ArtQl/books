package ru.artq.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.artq.book.entity.Author;
import ru.artq.book.entity.Reader;
import ru.artq.book.entity.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Transaction> findByReaderOrderByDateTimeAsc(Reader reader);

    @Query("select t.book.authors from Transaction t " +
            "where t.dateTime between :start and :end " +
            "group by t.book.authors " +
            "order by count(t.id) desc limit 1")
    Optional<Author> findMostPopularAuthor(LocalDate start, LocalDate end);

    @Query("select t.reader from Transaction t group by t.reader order by count (t.id) desc limit 1")
    Optional<Reader> findMostActiveReader();
}