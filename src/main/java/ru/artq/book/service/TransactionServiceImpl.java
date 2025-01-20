package ru.artq.book.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artq.book.entity.*;
import ru.artq.book.repository.AuthorRepository;
import ru.artq.book.repository.BookRepository;
import ru.artq.book.repository.ReaderRepository;
import ru.artq.book.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public void executeTransaction(String readerId, Integer bookId, TransactionType type) {
        log.info("Starting transaction: readerId={}, bookId={}, type={}", readerId, bookId, type);

        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> {
                    log.error("Reader not found: readerId={}", readerId);
                    return new EntityNotFoundException("Reader not found");
                });

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found: bookId={}", bookId);
                    return new EntityNotFoundException("Book not found");
                });

        Transaction transaction = new Transaction();
        transaction.setReader(reader);
        transaction.setBook(book);
        transaction.setType(type);
        transaction.setDateTime(LocalDateTime.now());

        transactionRepository.save(transaction);
        log.info("Transaction saved successfully: {}", transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Author findMostPopularAuthor(LocalDate start, LocalDate end) {
        log.info("Finding most popular author between {} and {}", start, end);

        return transactionRepository.findMostPopularAuthor(start, end)
                .orElseThrow(() -> {
                    log.warn("No popular author found between {} and {}", start, end);
                    return new EntityNotFoundException("Author not found");
                });
    }

    @Transactional(readOnly = true)
    @Override
    public Reader findMostActiveReader() {
        log.info("Finding most active reader");

        return transactionRepository.findMostActiveReader()
                .orElseThrow(() -> {
                    log.warn("No active readers found");
                    return new EntityNotFoundException("Readers not found");
                });
    }

    @Transactional(readOnly = true)
    @Override
    public List<Reader> getReadersWithUnreturnedBooks() {
        log.info("Fetching readers with unreturned books");

        Map<Reader, Integer> unreturnedBooks = transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getReader, Collectors.summingInt(tx ->
                        tx.getType() == TransactionType.TAKE ? 1 : -1)));

        List<Reader> readers = unreturnedBooks.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();

        log.info("Found {} readers with unreturned books", readers.size());
        return readers;
    }
}