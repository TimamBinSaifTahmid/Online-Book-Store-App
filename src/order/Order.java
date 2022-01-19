package order;

public class Order {
    private Long id;
    private Long bookId;
    private Long userId;
    private String bookName;
    private String userName;
    private Double amount;
    private Double price;
    private String date;
    private Double dueAmount;
    private Double paidAmount;

    public Order() {
    }

    public Order(Long id, Long bookId, Long userId, String bookName, String userName, Double amount, Double price, String date, Double dueAmount, Double paidAmount) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.bookName = bookName;
        this.userName = userName;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.dueAmount = dueAmount;
        this.paidAmount = paidAmount;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Override
    public String toString() {
        return "Book Name: " + bookName + "\n" +
                "User Name: " + userName + "\n" +
                "Amount: " + amount + "\n" +
                "Price: " + price + "\n" +
                "Date: " + date + "\n" +
                "DueAmount: " + dueAmount + "\n" +
                "PaidAmount: " + paidAmount
                ;
    }
}
