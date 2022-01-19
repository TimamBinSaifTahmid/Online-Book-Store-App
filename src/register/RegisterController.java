package register;

import databaseUser.DatabaseUser;
import databaseUser.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegisterController {
    public TextField tf_email, tf_password;
    public Label login_error_text;
    public AnchorPane loginPane;
    public Register register = new Register();

    public void onLoginButtonClick(ActionEvent event) throws IOException {
        if (register.isValidUser(getEmail(), getPassword())) {
            DatabaseUser.setUserId(register.getUserId());
            boolean isAdmin = register.isAdmin(getEmail(), getPassword());
            DatabaseUser.setUserType(isAdmin ? UserType.ADMIN : UserType.CUSTOMER);

            AnchorPane pane = FXMLLoader.load(getClass().getResource("../dashboard/dashboard.fxml"));
            loginPane.getChildren().setAll(pane);
        } else login_error_text.setText("invalid email or password");
    }

    public void onSignUpButtonClick(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("sign_up.fxml"));
        loginPane.getChildren().setAll(pane);
    }

    private String getEmail() {
        return tf_email.getText();
    }

    private String getPassword() {
        return tf_password.getText();
    }
}
