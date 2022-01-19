package pendingRating;

import book.Book;
import database.RatingDatabase;
import databaseUser.DatabaseUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PendingRatingController implements Initializable {
    public AnchorPane pendingRatingPane;
    public AnchorPane selectedBookPane;
    public ListView<String> listView;
    private RatingDatabase ratingDb;
    public Label bookDetails_label;
    public TextField rate_textField;
    private List<Book> books = new ArrayList<>();
    private Book selectedBook;

    public void onRateButtonClick(ActionEvent event) {
        selectedBook.setRating(Double.parseDouble(rate_textField.getText()));
        RatingDatabase database = new RatingDatabase();
        database.connect();
        database.rateBook(
                selectedBook.getId(),
                (long) DatabaseUser.getUserId(),
                selectedBook.getRating());
        reloadList();
    }

    public void onBackButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../dashboard/dashboard.fxml"));
        pendingRatingPane.getChildren().setAll(pane);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ratingDb = new RatingDatabase();
        ratingDb.connect();

        reloadList();
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (books.size() > 0) {
                    selectedBookPane.setVisible(true);
                    bookDetails_label.setText(books.get(index).toString());

                    selectedBook = books.get(index);
                }
            }
        });
    }

    private void reloadList() {
        selectedBookPane.setVisible(false);
        books = ratingDb.pendingRating(DatabaseUser.getUserId());
        for (Book book : books)
            listView.getItems().add(book.toString());
    }
}
