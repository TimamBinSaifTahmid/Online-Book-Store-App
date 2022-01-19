package order;

import book.Book;

public class CurrentOrder {
    private static Order order;
    private static Book book;

    public static Order getOrder() {
        return order;
    }

    public static void setOrder(Order order) {
        CurrentOrder.order = order;
    }

    public static Book getBook() {
        return book;
    }

    public static void setBook(Book book) {
        CurrentOrder.book = book;
    }
}
