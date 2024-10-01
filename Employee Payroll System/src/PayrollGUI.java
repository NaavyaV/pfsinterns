import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class PayrollGUI extends JFrame {
    private JTextField nameField, positionField, salaryField, hoursWorkedField, taxField;
    private JTextArea employeeArea;
    private JComboBox<Integer> employeeIdCombo;
    private NumberFormat currencyFormat;

    public PayrollGUI() {
        setTitle("Employee Payroll System");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); // Format in dollars

        // Main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Name field
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height)); // Full width
        namePanel.add(Box.createHorizontalStrut(10)); // Add spacing
        namePanel.add(nameField);
        mainPanel.add(namePanel);

        // Position field
        JPanel positionPanel = new JPanel();
        positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.X_AXIS));
        positionPanel.add(new JLabel("Position:"));
        positionField = new JTextField(20);
        positionField.setMaximumSize(new Dimension(Integer.MAX_VALUE, positionField.getPreferredSize().height)); // Full width
        positionPanel.add(Box.createHorizontalStrut(10)); // Add spacing
        positionPanel.add(positionField);
        mainPanel.add(positionPanel);

        // Salary field
        JPanel salaryPanel = new JPanel();
        salaryPanel.setLayout(new BoxLayout(salaryPanel, BoxLayout.X_AXIS));
        salaryPanel.add(new JLabel("Hourly Rate:"));
        salaryField = new JTextField(20);
        salaryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, salaryField.getPreferredSize().height)); // Full width
        salaryPanel.add(Box.createHorizontalStrut(10)); // Add spacing
        salaryPanel.add(salaryField);
        mainPanel.add(salaryPanel);

        JButton viewAuditLogButton = new JButton("View Audit Log");
        viewAuditLogButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAuditLogButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, viewAuditLogButton.getPreferredSize().height));
        viewAuditLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuditLogGUI auditLogGUI = new AuditLogGUI();
                auditLogGUI.setVisible(true);
            }
        });
        mainPanel.add(viewAuditLogButton);


        // Add Employee button
        JButton addButton = new JButton("Add Employee");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addButton.getPreferredSize().height)); // Full width
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameField.getText().isEmpty() || salaryField.getText().isEmpty() || positionField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please fill in all required fields for Employee creation", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String name = nameField.getText();
                    String position = positionField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    int employeeId = PayrollDBHelper.addEmployee(name, position, salary); // Get the generated employee ID
                    loadEmployees();

                    // Clear fields after adding employee
                    nameField.setText("");
                    positionField.setText("");
                    salaryField.setText("");
                    JOptionPane.showMessageDialog(null, "Employee added with ID: " + employeeId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        mainPanel.add(addButton);

        // Employee ID combo box
        JPanel employeeIdPanel = new JPanel();
        employeeIdPanel.setLayout(new BoxLayout(employeeIdPanel, BoxLayout.X_AXIS));
        employeeIdPanel.add(new JLabel("Employee ID:"));
        employeeIdCombo = new JComboBox<>();
        employeeIdCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, employeeIdCombo.getPreferredSize().height)); // Full width
        employeeIdPanel.add(Box.createHorizontalStrut(10)); // Add spacing
        employeeIdPanel.add(employeeIdCombo);
        mainPanel.add(employeeIdPanel);

        // Hours Worked field
        JPanel hoursWorkedPanel = new JPanel();
        hoursWorkedPanel.setLayout(new BoxLayout(hoursWorkedPanel, BoxLayout.X_AXIS));
        hoursWorkedPanel.add(new JLabel("Hours Worked:"));
        hoursWorkedField = new JTextField(20);
        hoursWorkedField.setMaximumSize(new Dimension(Integer.MAX_VALUE, hoursWorkedField.getPreferredSize().height)); // Full width
        hoursWorkedPanel.add(Box.createHorizontalStrut(10)); // Add spacing
        hoursWorkedPanel.add(hoursWorkedField);
        mainPanel.add(hoursWorkedPanel);

        // Tax Percentage field
        JPanel taxPanel = new JPanel();
        taxPanel.setLayout(new BoxLayout(taxPanel, BoxLayout.X_AXIS));
        taxPanel.add(new JLabel("Tax Percentage:"));
        taxField = new JTextField("15", 20);
        taxField.setMaximumSize(new Dimension(Integer.MAX_VALUE, taxField.getPreferredSize().height)); // Full width
        taxPanel.add(Box.createHorizontalStrut(10)); // Add spacing
        taxPanel.add(taxField);
        mainPanel.add(taxPanel);

        // Calculate Payroll button
        JButton calculatePayrollButton = new JButton("Calculate Payroll");
        calculatePayrollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculatePayrollButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, calculatePayrollButton.getPreferredSize().height)); // Full width
        calculatePayrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (hoursWorkedField.getText().isEmpty() || taxField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please fill in both 'Hours Worked' and 'Tax Percentage' fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int employeeId = (Integer) employeeIdCombo.getSelectedItem();
                    double hoursWorked = Double.parseDouble(hoursWorkedField.getText());
                    double taxPercentage = Double.parseDouble(taxField.getText()) / 100.0;
                    Employee employee = PayrollDBHelper.getEmployeeById(employeeId);
                    if (employee != null) {
                        Payroll payroll = new Payroll(hoursWorked, employee.getSalary());
                        payroll.setTaxPercentage(taxPercentage);
                        payroll.savePayroll(employeeId);

                        // Show employee ID, name, net salary, and taxes, formatted in dollars
                        String formattedSalary = currencyFormat.format(payroll.getNetSalary());
                        String formattedTaxes = currencyFormat.format(payroll.getTaxes());

                        JOptionPane.showMessageDialog(null, "Employee ID: " + employeeId + "\nName: " + employee.getName() + "\nNet Salary: " + formattedSalary + "\nTaxes Cut: " + formattedTaxes);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(calculatePayrollButton);

        // Delete Employee button
        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, deleteButton.getPreferredSize().height)); // Full width
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = (Integer) employeeIdCombo.getSelectedItem();
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete employee ID " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        PayrollDBHelper.deleteEmployeePayroll(id);
                        PayrollDBHelper.deleteEmployee(id);
                        loadEmployees();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(deleteButton);

        // Employee list display area
        employeeArea = new JTextArea(10, 50);
        employeeArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(employeeArea);
        mainPanel.add(scrollPane);

        // Add main panel to frame
        add(mainPanel);

        loadEmployees();
    }

    // Load employee list from DB
    private void loadEmployees() {
        employeeArea.setText("");
        employeeIdCombo.removeAllItems();
        try {
            ResultSet rs = PayrollDBHelper.getAllEmployees();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");

                // Format salary in dollars
                String formattedSalary = currencyFormat.format(salary);

                employeeArea.append("ID: " + id + ", Name: " + name + ", Position: " + position + ", Hourly Rate: " + formattedSalary + "\n");
                employeeIdCombo.addItem(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load employee IDs into the combo box
    private void loadEmployeeIds() {
        try {
            ResultSet rs = PayrollDBHelper.getAllEmployees();
            while (rs.next()) {
                int id = rs.getInt("id");
                employeeIdCombo.addItem(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PayrollGUI gui = new PayrollGUI();
        gui.setVisible(true);
    }
}
