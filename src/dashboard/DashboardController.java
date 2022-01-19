package dashboard;

import book.Book;
import database.BookDatabase;
import database.OrderDatabase;
import databaseUser.DatabaseUser;
import databaseUser.UserType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import order.CurrentOrder;
import order.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private static final String BOOK_LIST_STATE = "BOOK";
    private static final String WISH_LIST_STATE = "Wishlist";
    private static final String DUE_ORDER_LIST_STATE = "DUE_ORDER";
    private static final String ORDER_LIST_STATE = "ORDER";
    public AnchorPane dashboardPane, bar_pane, optionPane, customerPane;
    public ListView<String> listView;
    private BookDatabase bookDb;
    private OrderDatabase orderDb;
    public Label listName_label;
    private List<Book> books = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private String listViewState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (DatabaseUser.getUserType().equals(UserType.ADMIN))
            loadAdminView();
        else
            loadCustomerView();

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = listView.getSelectionModel().getSelectedIndex();

                if (listViewState.equals(BOOK_LIST_STATE)) {
                    onBookItemClick(index);
                } else if (listViewState.equals(WISH_LIST_STATE)) {
                    onBookItemClick(index);
                } else if (listViewState.equals(ORDER_LIST_STATE)) {
                    onOrderItemClick(index);
                } else if (listViewState.equals(DUE_ORDER_LIST_STATE)) {
                    onOrderItemClick(index);
                }
            }
        });
    }

    public void onProfileButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../profile/profile.fxml"));
        dashboardPane.getChildren().setAll(pane);
    }

    public void onPendingRatingButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../pendingRating/pendingRating.fxml"));
        dashboardPane.getChildren().setAll(pane);
    }

    public void onSearchButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../search/search.fxml"));
        dashboardPane.getChildren().setAll(pane);
    }

    public void onDueOrderClick(ActionEvent event) {
        listViewState = DUE_ORDER_LIST_STATE;
        showDueOrders();
    }

    public void onAllOrdersButtonClick(ActionEvent event) {
        listViewState = ORDER_LIST_STATE;
        showAllOrders();
    }

    public void onAllBooksButtonClick(ActionEvent event) {
        listViewState = BOOK_LIST_STATE;
        showAllBooks();
    }

    public void onWishlistButtonClick(ActionEvent event) {
        listViewState = WISH_LIST_STATE;

        showWishListBooks();
    }


    private void showAllBooks() {
        books.clear();
        listView.getItems().clear();
        listName_label.setText("Books");
        books = bookDb.getBooks();
        for (Book book : books) {
            listView.getItems().add(book.toString());
        }
    }

    private void showWishListBooks() {
        books.clear();
        listView.getItems().clear();
        listName_label.setText("WishList Books");
        books = bookDb.getWishListBooks(DatabaseUser.getUserId());
        for (Book book : books) {
            listView.getItems().add(book.toString());
        }
    }

    private void loadAdminView() {
        listViewState = ORDER_LIST_STATE;
        orderDb = new OrderDatabase();
        orderDb.connect();

        bar_pane.setVisible(false);
        optionPane.setVisible(true);
        customerPane.setVisible(false);
        showAllOrders();
    }

    private void loadCustomerView() {
        listViewState = BOOK_LIST_STATE;
        bookDb = new BookDatabase();
        bookDb.connect();

        bar_pane.setVisible(true);
        optionPane.setVisible(false);
        customerPane.setVisible(true);
        showAllBooks();
    }

    private void showAllOrders() {
        listName_label.setText("All Orders");
        orders.clear();
        listView.getItems().clear();
        orders = orderDb.getAllOrders();

        for (Order order : orders) {
            listView.getItems().add(order.toString());
        }
    }

    private void showDueOrders() {
        listName_label.setText("Due Orders");
        orders.clear();
        listView.getItems().clear();
        orders = orderDb.getDueOrders();

        for (Order order : orders) {
            listView.getItems().add(order.toString());
        }
    }

    private void onOrderItemClick(int position) {
        Order order = orders.get(position);
        if (order == null) return;
        AnchorPane pane = null;
        try {
            CurrentOrder.setOrder(order);
            pane = FXMLLoader.load(getClass().getResource("../reviewOrder/reviewOrder.fxml"));
            dashboardPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onBookItemClick(int position) {
        CurrentOrder.setBook(books.get(position));
        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("../order/order.fxml"));
            dashboardPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
