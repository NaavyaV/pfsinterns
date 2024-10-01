import javax.swing.*;
import java.sql.*;

public class AuditLogGUI extends JFrame {
    private JTextArea auditArea;

    public AuditLogGUI() {
        setTitle("Audit Log");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        auditArea = new JTextArea();
        auditArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(auditArea);
        add(scrollPane);
        loadAuditLogs();
    }

    private void loadAuditLogs() {
        auditArea.setText("");
        String query = "SELECT * FROM audit_log ORDER BY timestamp DESC";
        try (Connection conn = PayrollDBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String logEntry = String.format("Time: %s | Action: %s | Employee ID: %d | Details: %s\n",
                        rs.getTimestamp("timestamp"),
                        rs.getString("action"),
                        rs.getInt("employee_id"),
                        rs.getString("details"));
                auditArea.append(logEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
