package profile;

import databaseUser.DatabaseUser;
import database.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Label l_name, l_email, l_phone, l_address, l_dob;
    public AnchorPane profilePane;
    private Profile profile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDatabase userDb = new UserDatabase();/*DatabaseUser.getUserId()*/
        userDb.connect();
        profile = userDb.getProfileById(DatabaseUser.getUserId());
        l_name.setText("Name: " + profile.getName());
        l_email.setText("Email : " + profile.getEmail());
        l_phone.setText("Phone : " + profile.getPhone());
        l_address.setText("Address : " + profile.getAddress());
        l_dob.setText("Date Of Birth : " + profile.getDob());
    }

    public void onBackButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../dashboard/dashboard.fxml"));
        profilePane.getChildren().setAll(pane);
    }

}
