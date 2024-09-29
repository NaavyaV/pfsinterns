public class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean availability;
    private int publicationYear;
    private String currentHolder;

    public Book(int bookId, String title, String author, boolean availability, int publicationYear, String currentHolder) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.availability = availability;
        this.publicationYear = publicationYear;
        this.currentHolder = currentHolder;
    }

    public Book() {

    }

    // Getters and setters
    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return availability;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getCurrentHolder() {
        return currentHolder;
    }

    public void setAvailable(boolean availability) {
        this.availability = availability;
    }

    public void setCurrentHolder(String currentHolder) {
        this.currentHolder = currentHolder;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
