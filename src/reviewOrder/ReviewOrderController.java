package reviewOrder;

import database.OrderDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import order.CurrentOrder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReviewOrderController implements Initializable {
    public AnchorPane reviewPane;
    public Label orderDetails_label;
    public Button markAsPaid_btn;
    private OrderDatabase orderDb;

    public void onBackButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../dashboard/dashboard.fxml"));
        reviewPane.getChildren().setAll(pane);
    }

    public void onPaidButtonClick(ActionEvent event) {
        orderDb.updateOrder(CurrentOrder.getOrder().getId());
        CurrentOrder.getOrder().setDueAmount(0.0);
        CurrentOrder.getOrder().setPaidAmount(CurrentOrder.getOrder().getPrice());

        orderDetails_label.setText(CurrentOrder.getOrder().toString());
        disablePaidButton();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderDetails_label.setText(CurrentOrder.getOrder().toString());
        orderDb = new OrderDatabase();
        orderDb.connect();

        if (!orderDb.isDueOrder(CurrentOrder.getOrder().getId())) {
            disablePaidButton();
        }
    }

    private void disablePaidButton() {
        markAsPaid_btn.setDisable(true);
        markAsPaid_btn.setText("Paid");
    }


}
