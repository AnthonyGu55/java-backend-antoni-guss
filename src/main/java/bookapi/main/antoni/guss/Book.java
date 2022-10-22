package bookapi.main.antoni.guss;

public class Book {

    private String id;
    private String title;
    private Double rating;
    private String author;

    public Book() {
    }


    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Double getRating() {
        return this.rating;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String toString() {
        return "Book(id=" + this.getId() + ", title=" + this.getTitle() + ", rating=" + this.getRating() + ", author=" + this.getAuthor() + ")";
    }
}
