import java.sql.*;
public class Payroll {
    private double hoursWorked;
    private double grossSalary;
    private double taxes;
    private double netSalary;
    private double taxPercentage = 0.15;  // Default 15%

    public Payroll(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.grossSalary = hoursWorked * hourlyRate;
        calculateDeductions();
    }

    // Allow custom tax percentage
    public void setTaxPercentage(double taxPercentage) {
        this.taxPercentage = taxPercentage;
        calculateDeductions(); // Recalculate with new tax rate
    }

    private void calculateDeductions() {
        this.taxes = grossSalary * taxPercentage;
        this.netSalary = grossSalary - taxes;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public double getTaxes() {
        return taxes;
    }

    public void savePayroll(int employeeId) throws SQLException {
        String query = "INSERT INTO Payroll (employee_id, hours_worked, taxes, net_salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = PayrollDBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, employeeId);
            pstmt.setDouble(2, hoursWorked);
            pstmt.setDouble(3, taxes);
            pstmt.setDouble(4, netSalary);
            pstmt.executeUpdate();
        }
    }
}
