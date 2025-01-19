package ru.artq.book.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "type")
    private TransactionType type;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @ManyToOne
    private Reader reader;
    @ManyToOne
    private Book book;
}
