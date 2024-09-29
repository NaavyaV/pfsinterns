import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LibraryManagementUI extends JFrame {

    private JTextField titleField;
    private JTextField authorField;
    private JTextField bookIdField;
    private JTextField borrowerNameField;
    private JTextField borrowerEmailField;

    private JButton addBookButton;
    private JButton viewBooksButton;
    private JButton issueBookButton;
    private JButton returnBookButton;
    private JButton updateBookButton;
    private JButton deleteBookButton;

    private JButton addBorrowerButton;
    private JButton viewBorrowersButton;
    private JButton updateBorrowerButton;
    private JButton deleteBorrowerButton;

    private JButton showBooksButton; // Added to show all books
    private JButton showBorrowersButton; // Added to show all borrowers

    private BookDAO bookDAO;
    private BorrowerDAO borrowerDAO;

    public LibraryManagementUI(Connection conn) {
        setTitle("Library Management System");
        setSize(550, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        bookDAO = new BookDAO(conn);
        borrowerDAO = new BorrowerDAO(conn);

        // Labels and Fields for Book
        JLabel titleLabel = new JLabel("Book Title:");
        titleField = new JTextField();

        JLabel authorLabel = new JLabel("Book Author:");
        authorField = new JTextField();

        JLabel bookIdLabel = new JLabel("Book ID (Only for accessing purposes):");
        bookIdField = new JTextField();

        // Buttons for Book
        addBookButton = new JButton("Add Book");
        viewBooksButton = new JButton("View Books");
        issueBookButton = new JButton("Issue Book");
        returnBookButton = new JButton("Return Book");
        updateBookButton = new JButton("Update Book");
        deleteBookButton = new JButton("Delete Book");
        showBooksButton = new JButton("Show Books"); // Button to show all books

        // Labels and Fields for Borrower
        JLabel borrowerNameLabel = new JLabel("Borrower Name:");
        borrowerNameField = new JTextField();

        JLabel borrowerEmailLabel = new JLabel("Borrower Email:");
        borrowerEmailField = new JTextField();

        // Buttons for Borrower
        addBorrowerButton = new JButton("Add Borrower");
        viewBorrowersButton = new JButton("View Borrowers");
        updateBorrowerButton = new JButton("Update Borrower");
        deleteBorrowerButton = new JButton("Delete Borrower");
        showBorrowersButton = new JButton("Show Borrowers"); // Button to show all borrowers

        // Adding Components for Books
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridy++;
        add(titleField, gbc);

        gbc.gridy++;
        add(authorLabel, gbc);

        gbc.gridy++;
        add(authorField, gbc);

        gbc.gridy++;
        add(bookIdLabel, gbc);

        gbc.gridy++;
        add(bookIdField, gbc);

        gbc.gridy++;
        add(addBookButton, gbc);
        gbc.gridwidth = 1; // Reset grid width to 1
        add(viewBooksButton, gbc);

        gbc.gridx = 0; // Reset to first column
        gbc.gridy++;   // Next row
        add(issueBookButton, gbc);
        gbc.gridx = 1; // Next column for the last button
        add(returnBookButton, gbc);

        gbc.gridx = 0; // Reset to first column
        gbc.gridy++;   // Next row
        add(updateBookButton, gbc);
        gbc.gridx = 1; // Next column for the last button
        add(deleteBookButton, gbc);

        // Show Books Button
        gbc.gridx = 0; // Reset to first column
        gbc.gridy++;   // Next row
        add(showBooksButton, gbc);

        // Adding Components for Borrowers
        gbc.gridx = 0;
        gbc.gridy++;  // Next row
        gbc.gridwidth = 2; // Span across two columns
        add(borrowerNameLabel, gbc);

        gbc.gridy++;
        add(borrowerNameField, gbc);

        gbc.gridy++;
        add(borrowerEmailLabel, gbc);

        gbc.gridy++;
        add(borrowerEmailField, gbc);

        gbc.gridy++;
        add(addBorrowerButton, gbc);
        gbc.gridwidth = 1; // Reset grid width to 1
        add(viewBorrowersButton, gbc);

        gbc.gridx = 0; // Reset to first column
        gbc.gridy++;   // Next row
        add(updateBorrowerButton, gbc);
        gbc.gridx = 1; // Next column for the last button
        add(deleteBorrowerButton, gbc);

        // Show Borrowers Button
        gbc.gridx = 0; // Reset to first column
        gbc.gridy++;   // Next row
        add(showBorrowersButton, gbc);

        // Action listeners
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();

                if (title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(LibraryManagementUI.this, "Please fill all fields.");
                    return;
                }

                try {
                    bookDAO.addBook(title, author);
                    JOptionPane.showMessageDialog(LibraryManagementUI.this, "Book added successfully.");
                    titleField.setText(""); // Clear fields
                    authorField.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(LibraryManagementUI.this, "Error adding book: " + ex.getMessage());
                }
            }
        });

        showBooksButton.addActionListener(e -> {
            try {
                List<Book> books = bookDAO.getAllBooks();
                StringBuilder bookList = new StringBuilder();
                for (Book book : books) {
                    bookList.append("ID: ").append(book.getBookId())
                            .append(", Title: ").append(book.getTitle())
                            .append(", Author: ").append(book.getAuthor())
                            .append(", Available: ").append(book.isAvailable())
                            .append(", Current Holder: ").append(book.getCurrentHolder() == null ? "None" : book.getCurrentHolder())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, bookList.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching books!");
            }
        });

        issueBookButton.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(bookIdField.getText());
                String borrowerEmail = borrowerEmailField.getText();

                // Issue the book to the borrower
                bookDAO.issueBook(bookId, borrowerEmail);
                JOptionPane.showMessageDialog(null, "Book issued successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error issuing book!");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter a valid Book ID!");
            }
        });

        addBorrowerButton.addActionListener(e -> {
            try {
                String borrowerName = borrowerNameField.getText();
                String borrowerEmail = borrowerEmailField.getText();
                borrowerDAO.addBorrower(borrowerName, borrowerEmail);
                JOptionPane.showMessageDialog(null, "Borrower added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding borrower!");
            }
        });

        showBorrowersButton.addActionListener(e -> {
            try {
                List<Borrower> borrowers = borrowerDAO.getAllBorrowers();
                StringBuilder borrowerList = new StringBuilder();
                for (Borrower borrower : borrowers) {
                    borrowerList.append("ID: ").append(borrower.getBorrowerId())
                            .append(", Name: ").append(borrower.getName())
                            .append(", Email: ").append(borrower.getEmail())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, borrowerList.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching borrowers!");
            }
        });

        returnBookButton.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(bookIdField.getText());
                bookDAO.returnBook(bookId);
                JOptionPane.showMessageDialog(null, "Book returned successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error returning book!");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter a valid Book ID!");
            }
        });

        deleteBookButton.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(bookIdField.getText());
                bookDAO.deleteBook(bookId);
                JOptionPane.showMessageDialog(null, "Book deleted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting book!");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Please enter a valid Book ID!");
            }
        });

        deleteBorrowerButton.addActionListener(e -> {
            try {
                String borrowerEmail = borrowerEmailField.getText();
                borrowerDAO.deleteBorrower(borrowerEmail);
                JOptionPane.showMessageDialog(null, "Borrower deleted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting borrower!");
            }
        });

        updateBorrowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String borrowerName = borrowerNameField.getText();
                    String borrowerEmail = borrowerEmailField.getText();

                    // Check if the borrower exists before trying to update
                    if (borrowerDAO.isBorrowerExists(borrowerEmail)) {
                        borrowerDAO.updateBorrower(borrowerName, borrowerEmail);
                        JOptionPane.showMessageDialog(null, "Borrower information updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Borrower does not exist! Please check the email.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating borrower information!");
                }
            }
        });

        updateBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the values from the text fields
                String bookIdStr = bookIdField.getText();
                String title = titleField.getText();
                String author = authorField.getText();

                // Validate input
                if (bookIdStr.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }

                int bookId = Integer.parseInt(bookIdStr);

                try {
                    // Get current book details
                    Book currentBook = bookDAO.getBookById(bookId);
                    if (currentBook == null) {
                        JOptionPane.showMessageDialog(null, "Book not found.");
                        return;
                    }

                    // Update book details
                    bookDAO.updateBook(bookId, title, author, currentBook.isAvailable());
                    JOptionPane.showMessageDialog(null, "Book updated successfully.");

                    // Optionally, you can clear the fields after updating
                    titleField.setText("");
                    authorField.setText("");
                    bookIdField.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error updating book: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Book ID. Please enter a valid number.");
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            SwingUtilities.invokeLater(() -> {
                new LibraryManagementUI(conn).setVisible(true);
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
