import java.sql.*;

public class PayrollDBHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/payroll_db";
    private static final String USER = "root";
    private static final String PASS = "[PASSWORD HERE]";

    // Establish connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Create tables for employee and payroll
    public static void createTables() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String createEmployeeTable = "CREATE TABLE IF NOT EXISTS Employee (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100), " +
                    "position VARCHAR(100), " +
                    "salary DOUBLE)";

            String createPayrollTable = "CREATE TABLE IF NOT EXISTS Payroll (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "employee_id INT, " +
                    "hours_worked DOUBLE, " +
                    "taxes DOUBLE, " +
                    "net_salary DOUBLE, " +
                    "FOREIGN KEY (employee_id) REFERENCES Employee(id))";

            stmt.executeUpdate(createEmployeeTable);
            stmt.executeUpdate(createPayrollTable);
        }
    }

    // Add a new employee
    public static int addEmployee(String name, String position, double salary) {
        String query = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";
        int generatedId = -1; // Variable to hold the generated employee ID
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();

            // Retrieve the generated keys
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1); // Get the generated ID
            }

            // Log the action with the newly generated ID
            AuditHelper.logAction("INSERT", generatedId, "Added employee: " + name + ", Position: " + position + ", Salary: " + salary);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId; // Return the generated employee ID
    }


    // Retrieve all employees
    public static ResultSet getAllEmployees() throws SQLException {
        String query = "SELECT * FROM Employee WHERE is_active = TRUE";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    // Delete employee by ID
    public static void deleteEmployee(int id) {
        String deleteEmployeeQuery = "UPDATE employee SET is_active = FALSE WHERE id = ?";
        String auditLogQuery = "INSERT INTO audit_log (action, employee_id, details) VALUES (?, ?, ?)";

        try (Connection conn = getConnection()) {
            // First, log the deletion action in the audit log
            try (PreparedStatement auditStmt = conn.prepareStatement(auditLogQuery)) {
                auditStmt.setString(1, "DELETE");
                auditStmt.setInt(2, id); // ID of the deleted employee
                auditStmt.setString(3, "Deleted employee with ID: " + id);
                auditStmt.executeUpdate(); // This inserts a new record into audit_log
            }

            // Now delete the employee from the employee table
            try (PreparedStatement pstmt = conn.prepareStatement(deleteEmployeeQuery)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate(); // This deletes the employee
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    // Update employee details by ID
    public static void updateEmployee(int id, String name, String position, double salary) throws SQLException {
        String query = "UPDATE Employee SET name = ?, position = ?, salary = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            AuditHelper.logAction("UPDATE", id, "Updated employee: " + id + ", Name: " + name + ", Position: " + position + ", Salary: " + salary);
        }
    }

    // Retrieve specific employee by ID
    public static Employee getEmployeeById(int id) throws SQLException {
        String query = "SELECT * FROM Employee WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Employee(rs.getString("name"), rs.getString("position"), rs.getDouble("salary"));
            }
        }
        return null;
    }

    public static void deleteEmployeePayroll(int employeeId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM payroll WHERE employee_id = ?");
        stmt.setInt(1, employeeId);
        stmt.executeUpdate();
        conn.close();
    }

}
