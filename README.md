# Book Manager

A Java console application for managing a personal book catalogue. Developed as a university programming project, it supports searching, sorting and editing book records, with CSV files used for storage.

Originally developed as a university programming assignment. This English version preserves the original project structure and application logic.

## Features

- Load a catalogue from a user-specified CSV file.
- Add books with a title, author and publication year.
- Display records in a formatted table.
- Search by title or author using case-insensitive partial matching.
- Sort alphabetically by title or author.
- Update a book's details or delete records by title.
- Save the catalogue to `updated_books.csv`.
- Display session duration and counts of add and search operations when exiting.

When adding or updating a book, the application rejects empty titles, non-numeric years, years beyond the current year and duplicate title–author–year combinations. File loading retries after an I/O error.

## Technology

**Java standard library:** collections, streams, comparators, console input and file I/O. No external dependencies or build tool are required.

## Project structure

The main application files are located in `BookManager/`:

```text
BookManager/
├── src/
│   ├── Book.java
│   ├── FileManagement.java
│   ├── Main.java
│   └── MenuManagement.java
└── books.csv
```

| File | Responsibility |
| --- | --- |
| `Book.java` | Represents a book: title, author and year. |
| `FileManagement.java` | Loads and saves CSV files. |
| `Main.java` | Runs the application, handles menu choices and tracks session statistics. |
| `MenuManagement.java` | Implements catalogue operations and interactive input validation. |

## Getting started

Install a Java Development Kit (JDK). The source uses Java 8 language features, including lambdas and streams.

From the repository's root directory, run:

```sh
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

At the first prompt, enter:

```text
books.csv
```

You can also enter the path to another CSV file. The application interface, messages and source comments are in English.

## Usage

| Option | Action |
| --- | --- |
| 1 | Add a book |
| 2 | Display all books |
| 3 | Search by title or author |
| 4 | Sort by title |
| 5 | Sort by author |
| 6 | Remove books by title |
| 7 | Update a book by title |
| 8 | Save and exit |

For example, choose **1** and enter a title, author and publication year. Choose **3** to find matching records, then **8** to save the catalogue and end the session.

Changes are held in memory until option **8** is selected. The output file is written to the current working directory. To continue editing the saved catalogue in a later session, enter `updated_books.csv` at startup.

## CSV format

The input must contain a header followed by three comma-separated fields per record:

```csv
Title,Author,Year
The Hobbit,J. R. R. Tolkien,1937
Pride and Prejudice,Jane Austen,1813
```

The loader skips the first line and trims surrounding whitespace from each field. The year must be a valid integer.

## Current limitations

- CSV parsing uses a simple comma split; quoted fields and commas inside titles or author names are not supported.
- Imported records do not receive the same validation as interactive entries. Rows with a field count other than three are skipped; a non-numeric imported year can terminate the application.
- Deletion removes every record with an exact, case-insensitive title match. Updating changes the first matching record.
- Sorting uses Java's default, case-sensitive string ordering.
- Session statistics count selections of the add and search options, including unsuccessful attempts.
- Saving overwrites `updated_books.csv`. Save failures are reported, but the application still exits.

## Learning outcomes

This project demonstrates class-based modelling, collection manipulation, searching and sorting, file persistence, exception handling and separation of responsibilities across classes.
