package register;

import database.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public AnchorPane signUpPane;
    private UserDatabase userDb;
    public TextField name, email, phone, address, dob, password;

    public void onSignUpButtonClick(ActionEvent event) throws IOException {
        boolean isValid = userDb.addUser(getName(), getEmail(), getPassword(), getPhone(), getAddress(), getDoB(), 1, null);
        if (isValid) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("register.fxml"));
            signUpPane.getChildren().setAll(pane);
        }
    }

    public void onBackButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("register.fxml"));
        signUpPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDb = new UserDatabase();
        userDb.connect();
    }


    private String getEmail() {
        return email.getText();
    }

    private String getPhone() {
        return phone.getText();
    }

    private String getPassword() {
        return password.getText();
    }

    private String getAddress() {
        return address.getText();
    }

    private String getDoB() {
        return dob.getText();
    }

    private String getName() {
        return name.getText();
    }
}
