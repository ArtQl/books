CREATE TABLE authors
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    birth_date DATE         NOT NULL
);

CREATE TABLE books
(
    id           SERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    release_date DATE         NOT NULL
);

CREATE TABLE book_author
(
    book_id   INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);

CREATE TABLE employees
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE readers
(
    phone_number VARCHAR(15) PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    gender       VARCHAR(1),
    -- "M" или "F"
    birth_date   DATE         NOT NULL
);

CREATE TABLE transactions
(
    id                  SERIAL PRIMARY KEY,
    type                VARCHAR(10) NOT NULL,
    -- "TAKE" или "RETURN"
    date_time           TIMESTAMP   NOT NULL,
    reader_phone_number VARCHAR(15) NOT NULL,
    book_id             INT         NOT NULL,
    FOREIGN KEY (reader_phone_number) REFERENCES readers (phone_number) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);
