import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection conn;

    public BookDAO(Connection conn) {
        this.conn = conn;
    }

    public void addBook(String title, String author) throws SQLException {
        String sql = "INSERT INTO Books (title, author, availability) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        }
    }


    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setAvailable(rs.getBoolean("availability"));
                book.setCurrentHolder(rs.getString("current_holder"));
                books.add(book);
            }
        }
        return books;
    }

    public void issueBook(int bookId, String borrowerEmail) throws SQLException {
        String sql = "UPDATE Books SET availability = false, current_holder = ? WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, borrowerEmail);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        }
    }

    public void returnBook(int bookId) throws SQLException {
        String sql = "UPDATE Books SET availability = true, current_holder = NULL WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    public void deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM Books WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM Books WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the current book details
                    return new Book(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getBoolean("availability"),
                            rs.getInt("publication_year"),
                            rs.getString("current_holder")
                    );
                }
            }
        }
        return null; // Return null if no book is found
    }

    public void updateBook(int bookId, String title, String author, boolean availability) throws SQLException {
        String sql = "UPDATE Books SET title = ?, author = ? WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, bookId);
            pstmt.executeUpdate();
        }
    }
}
