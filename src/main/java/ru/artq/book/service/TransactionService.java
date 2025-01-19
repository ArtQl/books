package ru.artq.book.service;

import ru.artq.book.entity.Author;
import ru.artq.book.entity.Reader;
import ru.artq.book.entity.TransactionType;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    void executeTransaction(String readerId, Integer bookId, TransactionType type);

    Author findMostPopularAuthor(LocalDate start, LocalDate end);

    Reader findMostActiveReader();

    List<Reader> getReadersWithUnreturnedBooks();
}
