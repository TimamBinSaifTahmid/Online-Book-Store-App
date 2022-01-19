package search;

import book.Book;
import database.BookDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import order.CurrentOrder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    public AnchorPane searchPane;
    public TextField tf_search;
    private BookDatabase bookDb;
    public ListView<String> listview_search;
    private List<Book> books;

    public void onBackButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../dashboard/dashboard.fxml"));
        searchPane.getChildren().setAll(pane);
    }

    public void onSearchButtonClick(ActionEvent event) {
        books = new ArrayList<>();
        Book book = bookDb.getBookByName(tf_search.getText());
        if (book != null) books.add(book);
        else
            books = bookDb.getBookByCategory(tf_search.getText());

        listview_search.getItems().clear();
        if (!books.isEmpty())
            showInList();
    }

    private void showInList() {
        for (Book book : books)
            listview_search.getItems().add(book.toString());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookDb = new BookDatabase();
        bookDb.connect();
        listview_search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = listview_search.getSelectionModel().getSelectedIndex();
                Book book = books.get(index);
                if (book == null) return;
                AnchorPane pane = null;
                try {

                    CurrentOrder.setBook(book);
                    pane = FXMLLoader.load(getClass().getResource("../order/order.fxml"));
                    searchPane.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
