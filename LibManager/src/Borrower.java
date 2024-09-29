public class Borrower {
    private int borrowerId;
    private String name;
    private String email;

    public Borrower(int borrowerId, String name, String email) {
        this.borrowerId = borrowerId;
        this.name = name;
        this.email = email;
    }

    public Borrower() {

    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
