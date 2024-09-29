import java.util.Date;

public class Transaction {
    private int transactionId;
    private int bookId;
    private int borrowerId;
    private Date issueDate;
    private Date returnDate;

    public Transaction(int transactionId, int bookId, int borrowerId, Date issueDate, Date returnDate) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.borrowerId = borrowerId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    // Getters and setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
