package ru.artq.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artq.book.entity.Author;
import ru.artq.book.entity.Reader;
import ru.artq.book.entity.TransactionType;
import ru.artq.book.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api-library")
@RequiredArgsConstructor
public class LibraryController {
    private final TransactionService transactionService;

    @PostMapping("transaction")
    public ResponseEntity<String> transaction(
            @RequestParam String readerId,
            @RequestParam Integer bookId,
            @RequestParam TransactionType type) {
        transactionService.executeTransaction(readerId, bookId, type);
        return ResponseEntity.ok("Transaction successful");
    }

    @GetMapping("most-popular-author")
    public ResponseEntity<Author> findMostPopularAuthor(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        Author author = transactionService.findMostPopularAuthor(start, end);
        return ResponseEntity.ok(author);
    }

    @GetMapping("most-active-reader")
    public ResponseEntity<Reader> findMostActiveReader() {
        Reader reader = transactionService.findMostActiveReader();
        return ResponseEntity.ok(reader);
    }

    @GetMapping("readers-with-unreturned-books")
    public ResponseEntity<List<Reader>> getReadersWithUnreturnedBooks() {
        List<Reader> readers = transactionService.getReadersWithUnreturnedBooks();
        return ResponseEntity.ok(readers);
    }
}
