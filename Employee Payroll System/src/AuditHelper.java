import java.sql.*;
public class AuditHelper {
    public static void logAction(String action, int employeeId, String details) {
        String query = "INSERT INTO audit_log (action, employee_id, details) VALUES (?, ?, ?)";
        try (Connection conn = PayrollDBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, action);
            pstmt.setInt(2, employeeId);
            pstmt.setString(3, details);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
