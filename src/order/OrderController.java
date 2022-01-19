package order;

import databaseUser.DatabaseUser;
import database.OrderDatabase;
import database.WishListDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static order.CurrentOrder.*;

public class OrderController implements Initializable {
    private OrderDatabase orderDb;
    private WishListDatabase wishListDb;
    public Label label_details;
    public AnchorPane orderPane;
    public Button btn_order;
    public Button btn_addToWishList;

    public void onOrderButtonClick(ActionEvent event) {
        boolean isSuccessful = orderDb.addOrder(getBook().getId(), DatabaseUser.getUserId(), getBook().getPrice());
        if (isSuccessful) {
            btn_order.setText("Ordered");
            btn_order.setDisable(true);
        }
    }

    public void onAddToWishlistButtonClick(ActionEvent event) {
        boolean isSuccessful = wishListDb.addToWishList(getBook().getId(), DatabaseUser.getUserId());
        if (isSuccessful) {
            btn_addToWishList.setText("added to Wishlist");
            btn_addToWishList.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderDb = new OrderDatabase();
        wishListDb = new WishListDatabase();
        wishListDb.connect();
        orderDb.connect();
        label_details.setText(getBook().toString());
        if (wishListDb.isWishListExist(getBook().getId(), DatabaseUser.getUserId())) {
            btn_addToWishList.setText("added to Wishlist");
            btn_addToWishList.setDisable(true);
        }
    }

    public void onBackButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../dashboard/dashboard.fxml"));
        orderPane.getChildren().setAll(pane);
    }

}
