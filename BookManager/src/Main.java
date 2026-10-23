import java.io.*;
import java.util.*;

/**PROJECT: SIMPLE BOOK MANAGEMENT SYSTEM*/

public class Main {
    // Compare final values and row order, not object identities or action counts.
    private static boolean hasChanges(List<Book> originalBooks, List<Book> books) {
        if (originalBooks.size() != books.size()) {
            return true;
        }
        for (int i = 0; i < books.size(); i++) {
            Book original = originalBooks.get(i);
            Book current = books.get(i);
            if (!Objects.equals(original.title, current.title)
                    || !Objects.equals(original.author, current.author)
                    || original.year != current.year) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        List<Book> books = new ArrayList<>();
        boolean fileLoaded = false;

        /**Load data from a CSV file*/
        while (!fileLoaded) {
            System.out.print("Enter a CSV filename or path (default file: 'books.csv'): ");
            String fileName = scanner.nextLine();
            try {
                books = FileManagement.loadBooks(fileName);
                fileLoaded = true;
            } catch (IOException e) {
                System.out.println("Could not load the file. Please try again.");
            }
        }

        // Keep a deep copy: editing a book changes its existing object.
        List<Book> originalBooks = new ArrayList<>();
        for (Book book : books) {
            originalBooks.add(new Book(book.title, book.author, book.year));
        }

        boolean running = true;
        int addCount = 0, searchCount = 0;
        while (running) {
            MenuManagement.displayMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    // Count only successful additions, not rejected attempts.
                    int countBefore = books.size();
                    MenuManagement.addBook(books, scanner);
                    if (books.size() > countBefore) {
                        addCount++;
                    }
                    break;
                }

                case "2":
                    MenuManagement.printBooks(books);
                    break;

                case "3":
                    MenuManagement.searchBooks(books, scanner);
                    searchCount++;
                    break;

                case "4":
                    MenuManagement.sortByTitle(books);
                    break;

                case "5":
                    MenuManagement.sortByAuthor(books);
                    break;

                case "6":
                    MenuManagement.removeBook(books, scanner);
                    break;

                case "7":
                    MenuManagement.updateBook(books, scanner);
                    break;

                case "8":
                    if (hasChanges(originalBooks, books)) {
                        MenuManagement.saveBooks("updated_books.csv", books);
                    } else {
                        System.out.println("No changes detected. updated_books.csv was not created or overwritten.");
                    }
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        System.out.printf("Session duration: %.2f seconds%n", duration);
        System.out.println("Books added: " + addCount + ", Searches: " + searchCount);
    }
}
/**ADDITIONAL NOTES:
 - Handle input errors (e.g. entering text instead of a number).
 - The program loops until the exit option is selected.
 - Data is loaded from a CSV file specified by the user.*/