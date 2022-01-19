package book;

public class Book {
    private long id;
    private String name;
    private long quantity;
    private double price;
    private String description;
    private String status;
    private double rating;

    public Book() {
    }

    public Book(long id,
                String name,
                long quantity, 
                double price,
                String description,
                String status,
                double rating) {
        
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.status = status;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "name: " + name + "\n" +
                "Description: " + description + "\n" +
                "price: " + price + "\n" +
                "status: " + status + "\n" +
                "rating: " + rating;
    }
}