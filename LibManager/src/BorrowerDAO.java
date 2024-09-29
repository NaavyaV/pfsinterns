import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO {
    private Connection conn;

    public BorrowerDAO(Connection conn) {
        this.conn = conn;
    }

    public void addBorrower(String name, String email) throws SQLException {
        String sql = "INSERT INTO Borrowers (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }

    public List<Borrower> getAllBorrowers() throws SQLException {
        List<Borrower> borrowers = new ArrayList<>();
        String sql = "SELECT * FROM Borrowers";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Borrower borrower = new Borrower();
                borrower.setBorrowerId(rs.getInt("borrower_id"));
                borrower.setName(rs.getString("name"));
                borrower.setEmail(rs.getString("email"));
                borrowers.add(borrower);
            }
        }
        return borrowers;
    }

    public void deleteBorrower(String email) throws SQLException {
        String sql = "DELETE FROM Borrowers WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    public boolean isBorrowerExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Borrowers WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if count is greater than 0
                }
            }
        }
        return false; // Borrower does not exist
    }

    public void updateBorrower(String name, String email) throws SQLException {
        String sql = "UPDATE Borrowers SET name = ? WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        }
    }


}
