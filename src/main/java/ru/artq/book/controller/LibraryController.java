package ru.artq.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "Perform a transaction", description = "Register a book transaction for a reader")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successful"),
            @ApiResponse(responseCode = "404", description = "Reader or book not found", content = @Content)
    })
    @PostMapping("transaction")
    public ResponseEntity<String> transaction(
            @RequestParam String readerId,
            @RequestParam Integer bookId,
            @RequestParam TransactionType type) {
        transactionService.executeTransaction(readerId, bookId, type);
        return ResponseEntity.ok("Transaction successful");
    }

    @Operation(summary = "Find the most popular author", description = "Get the author whose books were most often borrowed within the specified period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found most popular author", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))
            }),
            @ApiResponse(responseCode = "404", description = "No popular author found", content = @Content)
    })
    @GetMapping("most-popular-author")
    public ResponseEntity<Author> findMostPopularAuthor(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        Author author = transactionService.findMostPopularAuthor(start, end);
        return ResponseEntity.ok(author);
    }

    @Operation(summary = "Find the most active reader", description = "Get the reader who performed the most transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found most active reader", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Reader.class))
            }),
            @ApiResponse(responseCode = "404", description = "No active reader found", content = @Content)
    })
    @GetMapping("most-active-reader")
    public ResponseEntity<Reader> findMostActiveReader() {
        Reader reader = transactionService.findMostActiveReader();
        return ResponseEntity.ok(reader);
    }

    @Operation(summary = "Get readers with unreturned books", description = "List all readers who have unreturned books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found readers with unreturned books", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Reader.class))
            })
    })
    @GetMapping("readers-with-unreturned-books")
    public ResponseEntity<List<Reader>> getReadersWithUnreturnedBooks() {
        List<Reader> readers = transactionService.getReadersWithUnreturnedBooks();
        return ResponseEntity.ok(readers);
    }
}
