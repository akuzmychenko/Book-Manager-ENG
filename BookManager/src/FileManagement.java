import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManagement {
    public static List<Book> loadBooks(String fileName) throws IOException {
        List<Book> books = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));  // supports absolute and relative paths

        String line;
        reader.readLine(); //skips the header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String title = parts[0].trim();
                String author = parts[1].trim();
                int year = Integer.parseInt(parts[2].trim());
                books.add(new Book(title, author, year));
            }
        }
        reader.close();
        return books;
    }

    static void saveBooks(String filename, List<Book> books) {
        /**8. Save books
         - The program saves data to updated_books.csv.*/
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("Title,Author,Year");
            for (Book b : books) {
                pw.println(b.title + "," + b.author + "," + b.year);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
        }
    }
}
