import java.io.*;
import java.util.*;

public class MenuManagement {
    public static void displayMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add a book");
        System.out.println("2. Display all books");
        System.out.println("3. Search (title or author)");
        System.out.println("4. Sort by title");
        System.out.println("5. Sort by author");
        System.out.println("6. Remove a book");
        System.out.println("7. Update a book");
        System.out.println("8. Save and exit");
        System.out.print("Choose an option: ");
    }
    //1
     static void addBook(List<Book> books, Scanner scanner) {
         /** 1. Add a book
          The user enters the title, author and year.
          The record is added to the book list.
          VALIDATION RULES:
          -The book title must not be empty.
          -The year must not be later than the current year.
          -Adding a duplicate book is not allowed.*/
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                throw new IllegalArgumentException("The title must not be empty.");
            }
            System.out.print("Author: ");
            String author = scanner.nextLine().trim();
            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year > currentYear) {
                throw new IllegalArgumentException("The year must not be later than " + currentYear + ".");
            }
            boolean exists = books.stream().anyMatch(b ->
                    b.title.equalsIgnoreCase(title) &&
                            b.author.equalsIgnoreCase(author) &&
                            b.year == year
            );
            if (exists) {
                throw new IllegalArgumentException("This book already exists.");
            }

            books.add(new Book(title, author, year));
            System.out.println("Book added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid year format. Please try again.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //2
     static void printBooks(List<Book> books) {
        /** 2. Display all books
         All books are displayed in a table.*/
        System.out.printf("%-30s %-20s %4s%n", "Title", "Author", "Year");
        for (Book b : books) {
            System.out.printf("%-30s %-20s %4d%n", b.title, b.author, b.year);
        }
    }
    //3
     static void searchBooks(List<Book> books, Scanner scanner) {
        /** 3. Search for a book
         - Search by title or author (case-insensitive).
         - If there are no matches, display: "No matching book found."*/
        System.out.print("Search term (title/author): ");
        String search = scanner.nextLine().toLowerCase();
        int count = 0;
        for (Book b : books) {
            if (b.title.toLowerCase().contains(search) || b.author.toLowerCase().contains(search)) {
                System.out.println(b);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No matching book found");
        }
    }
    //4
     static void sortByTitle(List<Book> books) {
        /** 4. Sort by title
         - Sort the list alphabetically by title.*/
        books.sort(Comparator.comparing(b -> b.title));
        System.out.println("Sorted by title.");
    }
    //5
     static void sortByAuthor(List<Book> books) {
        /** 5. Sort by author
         - Sort the list alphabetically by author.*/
        books.sort(Comparator.comparing(b -> b.author));
        System.out.println("Sorted by author.");
    }
    //6
     static void removeBook(List<Book> books, Scanner scanner) {
        /** 6. Remove a book
         - Remove books by title (removes every matching title).*/
        System.out.print("Title of the book to remove: ");
        String toRemove = scanner.nextLine().toLowerCase();
        books.removeIf(b -> b.title.toLowerCase().equals(toRemove));
        System.out.println("Matching books removed.");
    }
    //7
     static void updateBook(List<Book> books, Scanner scanner) {
        /** 7. Update a book
         - The user enters the title of an existing book.
         - The program allows the title, author and year to be changed.
         VALIDATION RULES:
         -The book title must not be empty.
         -The year must not be later than the current year.
         -Adding a duplicate book is not allowed.*/
        System.out.print("Title of the book to update: ");
        String toUpdate = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Book b : books) {
            if (b.title.toLowerCase().equals(toUpdate)) {
                found = true;
                try {
                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine().trim();
                    if (newTitle.isEmpty()) {
                        throw new IllegalArgumentException("The title must not be empty.");
                    }

                    System.out.print("New author: ");
                    String newAuthor = scanner.nextLine().trim();

                    System.out.print("New year: ");
                    int newYear = Integer.parseInt(scanner.nextLine());
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    if (newYear > currentYear) {
                        throw new IllegalArgumentException("The year must not be later than " + currentYear + ".");
                    }

                    boolean duplicate = books.stream().anyMatch(book ->
                            book != b &&
                                    book.title.equalsIgnoreCase(newTitle) &&
                                    book.author.equalsIgnoreCase(newAuthor) &&
                                    book.year == newYear
                    );
                    if (duplicate) {
                        throw new IllegalArgumentException("This book already exists.");
                    }

                    b.title = newTitle;
                    b.author = newAuthor;
                    b.year = newYear;
                    System.out.println("Book updated.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year format.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            }
        }
        if (!found) {
            System.out.println("No matching book found to update.");
        }
    }
    //8
     static void saveBooks(String filename, List<Book> books) {
        /**8. Save books
         - The program saves data to updated_books.csv.*/
        FileManagement.saveBooks(filename, books);
    }


}
