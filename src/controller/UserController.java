package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UserController {

    @FXML
    private MenuItem m_logout;

    @FXML
    private MenuItem m_close;

    @FXML
    private MenuItem m_about;

    @FXML
    private MenuItem m_author;

    @FXML
    private TableColumn<?, ?> col_client_id;

    @FXML
    private TableColumn<?, ?> col_client_name;

    @FXML
    private TableColumn<?, ?> col_client_fname;

    @FXML
    private TableColumn<?, ?> col_client_lname;

    @FXML
    private TableColumn<?, ?> col_client_phone;

    @FXML
    private TableColumn<?, ?> col_client_email;

    @FXML
    private TextField tf_client_find;

    @FXML
    private Button btn_client_find;

    @FXML
    private Button btn_add_customer;

    @FXML
    private Button btn_edit_customer;

    @FXML
    private Button btn_delete_customer;

    @FXML
    private TextField tf_client_name;

    @FXML
    private TextField tf_client_fname;

    @FXML
    private TextField tf_client_lname;

    @FXML
    private TextField tf_client_phone;

    @FXML
    private TextField tf_client_email;

    @FXML
    private Button btn_client_save;

    @FXML
    private TableColumn<?, ?> col_project_id;

    @FXML
    private TableColumn<?, ?> col_project_name;

    @FXML
    private TableColumn<?, ?> col_project_date;

    @FXML
    private TableColumn<?, ?> col_project_deadline;

    @FXML
    private TableColumn<?, ?> col_project_rate;

    @FXML
    private TableColumn<?, ?> col_project_client_name;

    @FXML
    private TextField tf_project_find;

    @FXML
    private Button btn_project_find;

    @FXML
    private Button btn_add_project;

    @FXML
    private Button btn_edit_project;

    @FXML
    private Button btn_delete_project;

    @FXML
    private TextField tf_projekt_name;

    @FXML
    private TextField tf_project_rate;

    @FXML
    private Button btn_project_save;

    @FXML
    private ComboBox<?> dp_project_date;

    @FXML
    private ComboBox<?> dp_project_deadline;

    @FXML
    private TableColumn<?, ?> col_drawing_id;

    @FXML
    private TableColumn<?, ?> col_drawing_name;

    @FXML
    private TableColumn<?, ?> col_drawing_num;

    @FXML
    private TableColumn<?, ?> col_drawing_width;

    @FXML
    private TableColumn<?, ?> col_drawing_height;

    @FXML
    private TableColumn<?, ?> col_drawing_rate;

    @FXML
    private TableColumn<?, ?> col_drawing_paid;

    @FXML
    private TextField tf_drawing_find;

    @FXML
    private Button btn_drawing_find;

    @FXML
    private Button btn_add_drawing;

    @FXML
    private Button btn_edit_drawing;

    @FXML
    private Button btn_delete_drawing;

    @FXML
    private TextField tf_drawing_name;

    @FXML
    private TextField tf_drawing_num;

    @FXML
    private Button btn_drawing_save;

    @FXML
    private TextField tf_drawing_width;

    @FXML
    private TextField tf_drawing_height;

    @FXML
    private TextField tf_drawing_rate;

    @FXML
    private ComboBox<?> combo_drawing_paid;

    @FXML
    void addClientAction(MouseEvent event) {

    }

    @FXML
    void addDrawingAction(MouseEvent event) {

    }

    @FXML
    void clientFindAction(MouseEvent event) {

    }

    @FXML
    void drawingFindAction(MouseEvent event) {

    }

    @FXML
    void editClientAction(MouseEvent event) {

    }

    @FXML
    void editDrawingAction(MouseEvent event) {

    }

    @FXML
    void menuAbout(ActionEvent event) {

    }

    @FXML
    void menuAuthor(ActionEvent event) {

    }

    @FXML
    void menuClose(ActionEvent event) {

    }

    @FXML
    void menuLogout(ActionEvent event) {

    }

    @FXML
    void projectFindAction(MouseEvent event) {

    }

    @FXML
    void saveClientAction(MouseEvent event) {

    }

}
