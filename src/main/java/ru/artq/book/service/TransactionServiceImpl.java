package ru.artq.book.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public void executeTransaction(String readerId, Integer bookId, TransactionType type) {
        Reader reader = readerRepository.findById(readerId).orElseThrow(() -> new EntityNotFoundException("Reader not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Transaction transaction = new Transaction();
        transaction.setReader(reader);
        transaction.setBook(book);
        transaction.setType(type);
        transaction.setDateTime(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Author findMostPopularAuthor(LocalDate start, LocalDate end) {
        return transactionRepository.findMostPopularAuthor(start, end)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Reader findMostActiveReader() {
        return transactionRepository.findMostActiveReader()
                .orElseThrow(() -> new EntityNotFoundException("Readers not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Reader> getReadersWithUnreturnedBooks() {
        //todo: можно лучше
        Map<Reader, Integer> unreturnedBooks = transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getReader, Collectors.summingInt(tx ->
                        tx.getType() == TransactionType.TAKE ? 1 : -1)));
        return unreturnedBooks.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();
    }
}
