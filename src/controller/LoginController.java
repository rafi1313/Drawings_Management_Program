package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.DBConnect;
import service.DialogWindow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Accordion accordionLogin;
    @FXML
    private TitledPane titledPaneLogin;
    @FXML
    private TextField tf_login;
    @FXML
    private TextField tf_password;
    @FXML
    private PasswordField pf_password;
    @FXML
    private CheckBox cb_show;
    @FXML
    private Button btn_login;
    @FXML
    private TextField reg_tf_login;
    @FXML
    private PasswordField reg_pf_password1;
    @FXML
    private PasswordField reg_pf_password2;
    @FXML
    private Button btn_register;

    private String login;
    private String password;
    static int userId = 0;

    @FXML
    void showPasswordAction(MouseEvent event) {
        if (cb_show.isSelected()) {
            tf_password.setText(pf_password.getText());
            tf_password.setVisible(true);
            pf_password.setVisible(false);
            pf_password.clear();
        }else {
            pf_password.setText(tf_password.getText());
            tf_password.setVisible(false);
            pf_password.setVisible(true);
            tf_password.clear();
        }

    }

    public void initialize() {
        accordionLogin.setExpandedPane(titledPaneLogin);
    }

    @FXML
    void loginAction(MouseEvent event) throws SQLException, IOException {
        login = tf_login.getText();
        if (cb_show.isSelected()) {
            password = tf_password.getText();
        } else {
            password = pf_password.getText();
        }
        externalLoginAction(login, password);
    }

    private void externalLoginAction(String login, String password) throws SQLException, IOException {
        Stage currenstage = (Stage) btn_login.getScene().getWindow();
        DBConnect dbConnect = new DBConnect();
        Connection connection = dbConnect.getCon();
        PreparedStatement ps = connection.prepareStatement("SELECT id_Users, login_Users, pass_Users, access from users where login_Users=? AND pass_Users=?");
        //        id_Users, login_Users, pass_Users, access
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet resultSet = ps.executeQuery();
        String permission = "1";
        if (resultSet.next()) {
            System.out.println("Zalogowano");
            permission = resultSet.getString("access");
            userId = resultSet.getInt("id_Users");
        }
        if (permission.equals("USER")) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/userView.fxml"));
            Scene scene = new Scene(root);
            Stage userStage = new Stage();
            userStage.setScene(scene);
            userStage.setTitle("Panel użytkownika");
            Image icon = new Image(getClass().getResourceAsStream("../resources/dmpIcon.png"));
            userStage.getIcons().add(icon);
            userStage.show();
            currenstage.close();
        } else if (permission.equals("ADMIN")) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/userView.fxml"));
            Scene scene = new Scene(root);
            Stage userStage = new Stage();
            userStage.setScene(scene);
            userStage.setTitle("Panel użytkownika");
            Image icon = new Image(getClass().getResourceAsStream("../resources/dmpIcon.png"));
            userStage.getIcons().add(icon);
            userStage.show();
            currenstage.close();
        } else {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "Błąd",
                    "Wystąpił błąd logowania.",
                    "Podano błędne dane. Spróbuj ponownie.");
        }
    }

    @FXML
    void registerAction(MouseEvent event) throws SQLException, IOException {
        DBConnect dbConnect = new DBConnect();
        Connection connection = dbConnect.getCon();
        // sprawdzenie dostępności loginu
        PreparedStatement ps = connection.prepareStatement("SELECT login_Users FROM users WHERE login_Users=?");
        ps.setString(1, reg_tf_login.getText());
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.isBeforeFirst()) {
            System.out.println("Login już istnieje!");
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "Błąd",
                    "Wystąpił błąd rejestracji",
                    "Podany login jest zajęty. Użyj innego i spróbuj ponownie.");
        } else if (reg_pf_password1 != reg_pf_password2) {
            System.out.println("Różne hasła!");
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "Błąd",
                    "Wystąpił błąd rejestracji",
                    "Podane hasła są różne. Spróbuj ponownie.");

        } else {
            System.out.println("ok");
            //        id_Users, login_Users, pass_Users, access
            ps = connection.prepareStatement("INSERT INTO users (login_Users, pass_Users) VALUES (?,?)");
            login = reg_tf_login.getText();
            password = reg_pf_password1.getText();
            ps.setString(1, login);
            ps.setString(2, password);
            ps.executeUpdate();
            DialogWindow dw = new DialogWindow(Alert.AlertType.INFORMATION,
                    "Rejestracja",
                    "Rejestracja przebiegła pomyślnie",
                    " ");
            externalLoginAction(login, password);
        }


    }


}
