package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Client;
import model.Drawing;
import model.Project;
import service.DBConnect;
import service.DialogWindow;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class UserController {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private int userId = LoginController.userId;
    private String saveButtonMode;
    private String saveProjectButtonMode;
    private String saveDrawingButtonMode;
    private static int currentClientId = 0;
    private static int customerToEditId = 0;
    private static int currentProjectId = 0;
    private static int projectToEditId = 0;
    //obiekty globalne
    SingleSelectionModel<Tab> selectedTab;
    private DBConnect db;
    private PreparedStatement ps;
    private ObservableList<Client> clientsList = FXCollections.observableArrayList();
    private ObservableList<Project> projectsList = FXCollections.observableArrayList();
    private ObservableList<Drawing> drawingsList = FXCollections.observableArrayList();

    @FXML
    private MenuItem m_logout;
    @FXML
    private MenuItem m_close;
    @FXML
    private MenuItem m_about;
    @FXML
    private MenuItem m_author;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tab_clients;
    @FXML
    private TableView<Client> tbl_clients;
    @FXML
    private TableColumn<Client, Integer> col_client_id;
    @FXML
    private TableColumn<Client, String> col_client_name;
    @FXML
    private TableColumn<Client, String> col_client_fname;
    @FXML
    private TableColumn<Client, String> col_client_lname;
    @FXML
    private TableColumn<Client, String> col_client_phone;
    @FXML
    private TableColumn<Client, String> col_client_email;
    @FXML
    private TextField tf_client_find;
    @FXML
    private Button btn_client_find;
    @FXML
    private Button btn_client_find_delete;

    @FXML
    private Button btn_add_customer;

    @FXML
    private Button btn_edit_customer;

    @FXML
    private Button btn_delete_customer;
    @FXML
    private AnchorPane anchor_add_client;

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
    private Button btn_go_to_projects;

    @FXML
    private Tab tab_projects;

    @FXML
    private TableView<Project> tbl_projects;

    @FXML
    private TableColumn<Project, Integer> col_project_id;

    @FXML
    private TableColumn<Project, String> col_project_name;

    @FXML
    private TableColumn<Project, Date> col_project_date;

    @FXML
    private TableColumn<Project, Date> col_project_deadline;

    @FXML
    private TableColumn<Project, Integer> col_project_rate;

    @FXML
    private TableColumn<Project, String> col_project_client_name;

    @FXML
    private TextField tf_project_find;

    @FXML
    private Button btn_project_find;
    @FXML
    private Button btn_project_find_delete;

    @FXML
    private Button btn_add_project;

    @FXML
    private Button btn_edit_project;

    @FXML
    private Button btn_delete_project;

    @FXML
    private AnchorPane anchor_add_project;

    @FXML
    private TextField tf_project_name;

    @FXML
    private TextField tf_project_rate;

    @FXML
    private Button btn_project_save;

    @FXML
    private DatePicker dp_project_date;

    @FXML
    private DatePicker dp_project_deadline;

    @FXML
    private Button btn_back_to_clients;

    @FXML
    private Button btn_go_to_drawings;

    @FXML
    private Tab tab_drawings;

    @FXML
    private TableView<Drawing> tbl_drawings;

    @FXML
    private TableColumn<Drawing, Integer> col_drawing_id;

    @FXML
    private TableColumn<Drawing, String> col_drawing_name;

    @FXML
    private TableColumn<Drawing, String> col_drawing_num;

    @FXML
    private TableColumn<Drawing, Integer> col_drawing_width;

    @FXML
    private TableColumn<Drawing, Integer> col_drawing_height;

    @FXML
    private TableColumn<Drawing, Integer> col_drawing_rate;

    @FXML
    private TableColumn<Drawing, String> col_drawing_paid;

    @FXML
    private TextField tf_drawing_find;

    @FXML
    private Button btn_drawing_find;
    @FXML
    private Button btn_drawing_find_delete;

    @FXML
    private Button btn_add_drawing;

    @FXML
    private Button btn_edit_drawing;

    @FXML
    private Button btn_delete_drawing;

    @FXML
    private AnchorPane anchor_add_drawing;

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
    private ComboBox<String> combo_drawing_paid;

    @FXML
    private Button btn_back_to_projects;

    @FXML
    void addClientAction(MouseEvent event) {
        anchor_add_client.setDisable(false);
        saveButtonMode = "SAVE";
    }

    @FXML
    void addDrawingAction(MouseEvent event) {
        anchor_add_drawing.setDisable(false);
        saveDrawingButtonMode = "SAVE";
    }

    @FXML
    void addProjectAction(MouseEvent event) {
        anchor_add_project.setDisable(false);
        saveProjectButtonMode = "SAVE";
    }

    @FXML
    void backToClientsAction(MouseEvent event) throws SQLException {
        tab_clients.setDisable(false);
        tab_projects.setDisable(true);
        tabPane.getSelectionModel().select(tab_clients);
        setClients("%");
    }

    @FXML
    void backToProjectsAction(MouseEvent event) throws SQLException {
        tab_projects.setDisable(false);
        tab_drawings.setDisable(true);
        tabPane.getSelectionModel().select(tab_projects);
    }

    @FXML
    void clientFindAction(MouseEvent event) throws SQLException {
        setClients(tf_client_find.getText());

    }

    @FXML
    void clientFindDeleteAction(MouseEvent event) throws SQLException {
        setClients("%");
        tf_client_find.clear();
    }

    @FXML
    void drawingFindAction(MouseEvent event) {

    }

    @FXML
    void drawingFindDeleteAction(MouseEvent event) {

    }

    private void editClient(int customerToEditId) throws SQLException {
        if (tf_client_name.getText().trim().isEmpty()) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd edycji klienta", "Nazwa klienta nie może być pusta!");
        } else if (tf_client_email.getText().trim().equals("")) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd edycji klienta", "Email klienta nie może być pusty!!");
        } else if (!tf_client_email.getText().matches(EMAIL_PATTERN)) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd edycji klienta", "Niepoprawny format adresu email!");
        } else {
            String clientName = tf_client_name.getText();
            String clientFirstName = tf_client_fname.getText();
            String clientLastName = tf_client_lname.getText();
            String clientPhone = tf_client_phone.getText();
            String clientEmail = tf_client_email.getText();
            db = new DBConnect();
            Connection connection = db.getCon();
            ps = connection.prepareStatement("UPDATE customers SET customers_name=?, first_name=?, last_name=?, phone_number=?, email=? WHERE id_customers=?");
            ps.setString(1, clientName);
            ps.setString(2, clientFirstName);
            ps.setString(3, clientLastName);
            ps.setString(4, clientPhone);
            ps.setString(5, clientEmail);
            ps.setInt(6, customerToEditId);
            ps.executeUpdate();
            tf_client_name.clear();
            tf_client_fname.clear();
            tf_client_lname.clear();
            tf_client_phone.clear();
            tf_client_email.clear();
            setClients("%");
            anchor_add_client.setDisable(true);
            customerToEditId = 0;
        }
    }

    @FXML
    void editClientAction(MouseEvent event) throws SQLException {
        saveButtonMode = "EDIT";
        try {
            customerToEditId = tbl_clients.getSelectionModel().getSelectedItem().getId();
            db = new DBConnect();
            Connection connection = db.getCon();
            String query = "SELECT id_customers, customers_name, first_name, last_name, phone_number, email FROM customers" +
                    " where (id_Users=" + userId + " AND id_customers=" + customerToEditId + ")";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            resultSet.first();
            tf_client_name.setText(resultSet.getString("customers_name"));
            tf_client_fname.setText(resultSet.getString("first_name"));
            tf_client_lname.setText(resultSet.getString("last_name"));
            tf_client_phone.setText(resultSet.getString("phone_number"));
            tf_client_email.setText(resultSet.getString("email"));
            anchor_add_client.setDisable(false);
        } catch (NullPointerException e) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "BŁĄD",
                    "Błąd danych",
                    "Musisz wybrać klienta z listy do edycji!");
        }
    }

    @FXML
    void editDrawingAction(MouseEvent event) {

    }

    private void editProject(int projectId) throws SQLException {
        String projectDeadline;
        String projectDate;
        String regex = "\\d+";
        if (tf_project_name.getText().trim().isEmpty()) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd edycji projektu", "Musisz podać nazwę projektu!");
        } else if (dp_project_date.getValue() == null) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd edycji projektu", "Musisz datę dodania projektu!");
        } else if (dp_project_deadline.getValue() == null) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd edycji projektu", "Musisz datę terminu projektu!");
        } else if (!tf_project_rate.getText().matches(regex)) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd edycji projektu", "Podana stawka projektu nie jest liczbą!\nDla nieznanej stawki wpisz '0'.");
        } else {
            String projectName = tf_project_name.getText();
            String projectRate = tf_project_rate.getText();
            projectDate = dp_project_date.getValue().toString();
            projectDeadline = dp_project_deadline.getValue().toString();
            db = new DBConnect();
            Connection connection = db.getCon();
//            id_projects, project_name, rate_per_drawing, project_date, project_deadline, customers_name
            ps = connection.prepareStatement("UPDATE projects SET project_name=?, rate_per_drawing=?, project_date=?, project_deadline=? WHERE id_projects=?");
            ps.setString(1, projectName);
            ps.setString(2, projectRate);
            ps.setString(3, projectDate);
            ps.setString(4, projectDeadline);
            ps.setInt(5, projectToEditId);
            ps.executeUpdate();
            tf_project_name.clear();
            tf_project_rate.clear();
            dp_project_date.setValue(null);
            dp_project_deadline.setValue(null);
            setProjects("%", userId);
            anchor_add_project.setDisable(true);
            projectToEditId = 0;
        }

    }

    @FXML
    void editProjectAction(MouseEvent event) throws SQLException {//to finish later
        saveProjectButtonMode = "EDIT";
        try {
            projectToEditId = tbl_projects.getSelectionModel().getSelectedItem().getId();
            db = new DBConnect();
            Connection connection = db.getCon();
            String query = "select id_projects, project_name, rate_per_drawing, project_date, project_deadline, customers_name " +
                    "from projects as p " +
                    "join customers as c on (p.id_customers=c.id_customers)  where id_projects=" + projectToEditId + ";";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            resultSet.first();
            tf_project_name.setText(resultSet.getString("project_name"));
            tf_project_rate.setText(resultSet.getString("rate_per_drawing"));
            String[] projectDate = resultSet.getString("project_date").split("-");
            int year = Integer.parseInt(projectDate[0]);
            int month = Integer.parseInt(projectDate[1]);
            int day = Integer.parseInt(projectDate[2]);
            dp_project_date.setValue(LocalDate.of(year, month, day));
            String[] projectDeadline = resultSet.getString("project_deadline").split("-");
            year = Integer.parseInt(projectDeadline[0]);
            month = Integer.parseInt(projectDeadline[1]);
            day = Integer.parseInt(projectDeadline[2]);
            dp_project_deadline.setValue(LocalDate.of(year, month, day));
            anchor_add_project.setDisable(false);
        } catch (NullPointerException e) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "BŁĄD",
                    "Błąd danych",
                    "Musisz wybrać projekt z listy do edycji!");
        }
    }

    @FXML
    void goToDrawingsAction(MouseEvent event) throws SQLException {
        try{
            currentProjectId = tbl_projects.getSelectionModel().getSelectedItem().getId();
            tab_projects.setDisable(true);
            tab_drawings.setDisable(false);
            setDrawings("%", currentProjectId);
            tabPane.getSelectionModel().select(tab_drawings);
        }catch (NullPointerException e){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "BŁĄD",
                    "Błąd danych",
                    "Musisz wybrać projekt z listy do prześcia do projektów!");
        }
    }

    @FXML
    void goToProjectsAction(MouseEvent event) throws SQLException {
        try {
            currentClientId = tbl_clients.getSelectionModel().getSelectedItem().getId();
            tab_clients.setDisable(true);
            tab_projects.setDisable(false);
            setProjects("%", currentClientId);
            tabPane.getSelectionModel().select(tab_projects);
        } catch (NullPointerException e) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "BŁĄD",
                    "Błąd danych",
                    "Musisz wybrać klienta z listy do prześcia do projektów!");
        }
    }

    @FXML
    void menuAbout(ActionEvent event) {
        DialogWindow dw = new DialogWindow(Alert.AlertType.INFORMATION,
                "INFO",
                "About",
                "Drawings Management Program:\nPomysł i wykonanie: Rafał Borkowski\nhttps://github.com/rafi1313");
    }

    @FXML
    void menuAuthor(ActionEvent event) {


        DialogWindow dw = new DialogWindow(Alert.AlertType.INFORMATION,
                "INFO",
                "Autor",
                "Rafał Borkowski\nhttps://github.com/rafi1313");
    }

    @FXML
    void menuClose(ActionEvent event) {
        Stage currenstage = (Stage) tabPane.getScene().getWindow();
        currenstage.close();
    }

    @FXML
    void menuLogout(ActionEvent event) throws IOException {
        Stage currenstage = (Stage) tabPane.getScene().getWindow();
        currenstage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drawings Management Program");
        Image icon = new Image(getClass().getResourceAsStream("/resources/dmpIcon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();

    }

    @FXML
    void projectFindAction(MouseEvent event) throws SQLException {
        setProjects(tf_project_find.getText(), currentClientId);
    }

    @FXML
    void projectFindDeleteAction(MouseEvent event) throws SQLException {
        setProjects("%", currentClientId);
        tf_project_find.clear();
    }

    private void saveClient() throws SQLException {
        if (tf_client_name.getText().trim().isEmpty()) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania klienta", "Musisz podać nazwę klienta!");
        } else if (tf_client_email.getText().trim().equals("")) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania klienta", "Musisz podać email klienta!");
        } else if (!tf_client_email.getText().matches(EMAIL_PATTERN)) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania klienta", "Niepoprawny format adresu email!");
        } else {
            String clientName = tf_client_name.getText();
            String clientFirstName = tf_client_fname.getText();
            String clientLastName = tf_client_lname.getText();
            String clientPhone = tf_client_phone.getText();
            String clientEmail = tf_client_email.getText();

            db = new DBConnect();
            Connection connection = db.getCon();
//            String query = "INSERT INTO customers ()";
//            id_customers, customers_name, first_name, last_name, phone_number, email, id_Users
            ps = connection.prepareStatement("INSERT INTO customers (customers_name, first_name, last_name, phone_number, email, id_Users) VALUES (?,?,?,?,?,?)");
            ps.setString(1, clientName);
            ps.setString(2, clientFirstName);
            ps.setString(3, clientLastName);
            ps.setString(4, clientPhone);
            ps.setString(5, clientEmail);
            ps.setInt(6, userId);
            ps.executeUpdate();

            tf_client_name.clear();
            tf_client_fname.clear();
            tf_client_lname.clear();
            tf_client_phone.clear();
            tf_client_email.clear();
            setClients("%");
            anchor_add_client.setDisable(true);
        }
    }

    @FXML
    void saveClientAction(MouseEvent event) throws SQLException {
        if (saveButtonMode.equals("SAVE")) {
            saveClient();
        } else {
            editClient(customerToEditId);
        }

    }
    private void saveDrawing()throws SQLException{
        String regex = "\\d+";
        if (tf_drawing_name.getText().trim().isEmpty()){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania rysunku", "Musisz podać nazwę rysunku!");
        }else if (!tf_drawing_rate.getText().matches(regex)){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania rysunku", "Podana stawka rysunku nie jest liczbą!\nDla nieznanej stawki wpisz '0'.");
        }else if (!tf_drawing_width.getText().matches(regex)){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania rysunku", "Podana szerokość rysunku nie jest liczbą!\nDla nieznanej szerokości wpisz '0'.");
        }else if (!tf_drawing_height.getText().matches(regex)){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania rysunku", "Podana wysokość rysunku nie jest liczbą!\nDla nieznanej wysokości wpisz '0'.");
        }else if (combo_drawing_paid.getValue().isEmpty()){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania rysunku", "Nie wybrano stanu rozliczenia rysunku!");
        }else{
            String drawingName = tf_drawing_name.getText();
            String drawingNum = tf_drawing_num.getText();
            int width = Integer.parseInt(tf_drawing_width.getText());
            int height = Integer.parseInt(tf_drawing_height.getText());
            int rate = Integer.parseInt(tf_drawing_rate.getText());
            String paidString = combo_drawing_paid.getValue();
            int paid;
            if (paidString.equals("NIE")){
                paid=0;
            }else {
                paid=1;
            }
            db = new DBConnect();
            Connection connection = db.getCon();
//            id_drawings, drawing_name, drawing_num, width, height, individual_rate, id_projects, paid, id_Users
            ps = connection.prepareStatement("INSERT INTO projects " +
                    "(drawing_name, drawing_num, width, height, individual_rate, id_projects, paid, id_Users) " +
                    "VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, drawingName);
            ps.setString(2, drawingNum);
            ps.setInt(3, width);
            ps.setInt(4, height);
            ps.setInt(5, rate);
            ps.setInt(6, currentProjectId);
            ps.setInt(7, paid);
            ps.setInt(8, userId);
            ps.executeUpdate();

            setDrawings("%", currentProjectId);
            anchor_add_drawing.setDisable(true);
        }
    }

    @FXML
    void saveDrawingAction(MouseEvent event) throws SQLException {
        if (saveDrawingButtonMode.equals("SAVE")) {
            saveDrawing();
        } else {
            editProject(currentProjectId);
        }
    }

    @FXML
    void saveProjectAction(MouseEvent event) throws SQLException {
        if (saveProjectButtonMode.equals("SAVE")) {
            saveProject();
        } else {
            editProject(projectToEditId);
        }

    }

    private void saveProject() throws SQLException {
        String projectDeadline;
        String projectDate;
        String regex = "\\d+";
        if (tf_project_name.getText().trim().isEmpty()) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania projektu", "Musisz podać nazwę projektu!");
        } else if (dp_project_date.getValue() == null) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania projektu", "Musisz datę dodania projektu!");
        } else if (dp_project_deadline.getValue() == null) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania projektu", "Musisz datę terminu projektu!");
        } else if (!tf_project_rate.getText().matches(regex)) {
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania projektu", "Podana stawka projektu nie jest liczbą!\nDla nieznanej stawki wpisz '0'.");
        } else {
            String projectName = tf_project_name.getText();
            String projectRate = tf_project_rate.getText();
            projectDate = dp_project_date.getValue().toString();
            projectDeadline = dp_project_deadline.getValue().toString();
            db = new DBConnect();
            Connection connection = db.getCon();
//            id_projects, project_name, rate_per_drawing, project_date, project_deadline, id_customers
            ps = connection.prepareStatement("INSERT INTO projects (project_name, rate_per_drawing, project_date, project_deadline, id_customers) VALUES (?,?,?,?,?)");
            ps.setString(1, projectName);
            ps.setInt(2, Integer.parseInt(projectRate));
            ps.setString(3, projectDate);
            ps.setString(4, projectDeadline);
            ps.setInt(5, currentClientId);
            ps.executeUpdate();

            ps = connection.prepareStatement("INSERT INTO projects_has_users(Users_id_Users, customers_id_customers) values(?,?)");
            ps.setInt(1, userId);
            ps.setInt(2, currentClientId);
            ps.executeUpdate();

            tf_project_name.clear();
            tf_project_rate.clear();
            dp_project_date.setValue(null);
            dp_project_deadline.setValue(null);

            setProjects("%", currentClientId);
            anchor_add_project.setDisable(true);

        }
    }

    public void initialize() throws SQLException {
        setClients("%");
        ObservableList<String> comboOptions = FXCollections.observableArrayList("TAK","NIE");
        combo_drawing_paid.setItems(comboOptions);
    }

    private void setClients(String param) throws SQLException {
        //        id_customers, customers_name, first_name, last_name, phone_number, email, id_Users
        String query = "SELECT id_customers, customers_name, first_name, last_name, phone_number, email FROM customers" +
                " where (id_Users=" + userId + " AND (concat(customers_name, first_name, last_name, phone_number, email) LIKE '%" + param + "%'))";
        clientsList.clear();
        db = new DBConnect();
        Connection connection = db.getCon();
        ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Client client = new Client(resultSet.getInt("id_customers"),
                    resultSet.getString("customers_name"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"));
            clientsList.add(client);
        }
        col_client_id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        col_client_name.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        col_client_fname.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        col_client_lname.setCellValueFactory(new PropertyValueFactory<Client, String>("lastName"));
        col_client_phone.setCellValueFactory(new PropertyValueFactory<Client, String>("phoneNumber"));
        col_client_email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        tbl_clients.setItems(clientsList);
    }

    private void setProjects(String parameter, int currentClient) throws SQLException {
        String query = "select id_projects, project_name, customers_name, project_date, project_deadline,rate_per_drawing,p.id_customers " +
                "from projects as p " +
                "join customers as c on (c.id_customers=p.id_customers) " +
                "where (p.id_customers =" + currentClient + " AND concat(id_projects, project_name, customers_name, project_date, project_deadline, rate_per_drawing, p.id_customers) LIKE '%" + parameter + "%' )order by project_name";
        projectsList.clear();
        db = new DBConnect();
        Connection connection = db.getCon();
        ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Project project = new Project(resultSet.getInt("id_projects"),
                    resultSet.getString("project_name"),
                    resultSet.getInt("rate_per_drawing"),
                    resultSet.getDate("project_date"),
                    resultSet.getDate("project_deadline"),
                    resultSet.getInt("p.id_customers"),
                    resultSet.getString("customers_name"));
            projectsList.add(project);
        }
        col_project_id.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        col_project_name.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
        col_project_date.setCellValueFactory(new PropertyValueFactory<Project, Date>("projectDate"));
        col_project_deadline.setCellValueFactory(new PropertyValueFactory<Project, Date>("deadline"));
        col_project_rate.setCellValueFactory(new PropertyValueFactory<Project, Integer>("rate"));
        col_project_client_name.setCellValueFactory(new PropertyValueFactory<Project, String>("clientName"));
        tbl_projects.setItems(projectsList);
    }

    private void setDrawings(String parameter, int currentProject) throws SQLException {
        String query = "select id_drawings, drawing_name, drawing_num, width, height, individual_rate, paid " +
                "from drawings where (id_projects=" + currentProject + " AND id_Users = " + userId + " " +
                "AND concat(drawing_name, drawing_num, width, height, individual_rate) LIKE '%" + parameter + "%' )";
//        id_drawings, drawing_name, drawing_num, width, height, individual_rate, paid
        drawingsList.clear();
        db = new DBConnect();
        Connection connection = db.getCon();
        ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Drawing drawing = new Drawing();
            int id_drawings = resultSet.getInt("id_drawings");
            String drawing_name = resultSet.getString("drawing_name");
            String drawing_num = resultSet.getString("drawing_num");
            int width = resultSet.getInt("width");
            int height = resultSet.getInt("height");
            int individual_rate = resultSet.getInt("individual_rate");
            int paid01= resultSet.getInt("paid");
            String paidString;
            if (paid01==0){
                paidString="NIE";
            }else {
                paidString="TAK";
            }
            drawing.setId_drawings(id_drawings);
            drawing.setDrawing_name(drawing_name);
            drawing.setDrawing_num(drawing_num);
            drawing.setWidth(width);
            drawing.setHeight(height);
            drawing.setIndividual_rate(individual_rate);
            drawing.setPaid(paidString);


            drawingsList.add(drawing);
        }
        col_drawing_id.setCellValueFactory(new PropertyValueFactory<Drawing, Integer>("id_drawings"));
        col_drawing_name.setCellValueFactory(new PropertyValueFactory<Drawing, String>("drawing_name"));
        col_drawing_num.setCellValueFactory(new PropertyValueFactory<Drawing, String>("drawing_num"));
        col_drawing_width.setCellValueFactory(new PropertyValueFactory<Drawing, Integer>("width"));
        col_drawing_height.setCellValueFactory(new PropertyValueFactory<Drawing, Integer>("height"));
        col_drawing_rate.setCellValueFactory(new PropertyValueFactory<Drawing, Integer>("individual_rate"));
        col_drawing_paid.setCellValueFactory(new PropertyValueFactory<Drawing, String>("paid"));
        tbl_drawings.setItems(drawingsList);
    }

}

