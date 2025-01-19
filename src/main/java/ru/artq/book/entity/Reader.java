package ru.artq.book.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "readers")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Reader {
    @Id
    private String phoneNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "birhday")
    private LocalDate birthDate;
    @OneToMany(mappedBy = "reader")
    private Set<Transaction> transactions = new HashSet<>();
}
