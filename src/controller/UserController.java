package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Client;
import model.Drawing;
import model.Project;
import service.DBConnect;
import service.DialogWindow;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserController {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private int userId = LoginController.userId;
    private String saveButtonMode;
    private String saveProjectButtonMode;
    private static int customerToEditId=0;
    private static int currentProjectId =0;
    private static int projectToEditId=0;
    //obiekty globalne
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
        setClients("%");
    }

    @FXML
    void backToProjectsAction(MouseEvent event) throws SQLException {
        tab_projects.setDisable(false);
        tab_drawings.setDisable(true);
    }

    @FXML
    void clientFindAction(MouseEvent event) throws SQLException {
        setClients(tf_client_find.getText());

    }
    @FXML
    void clientFindDeleteAction(MouseEvent event) throws SQLException {
        setClients("%");
    }

    @FXML
    void drawingFindAction(MouseEvent event) {

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
        try{
            customerToEditId = tbl_clients.getSelectionModel().getSelectedItem().getId();
            db = new DBConnect();
            Connection connection = db.getCon();
            String query = "SELECT id_customers, customers_name, first_name, last_name, phone_number, email FROM customers" +
                    " where (id_Users="+userId+" AND id_customers="+customerToEditId+")";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            resultSet.first();
            tf_client_name.setText(resultSet.getString("customers_name"));
            tf_client_fname.setText(resultSet.getString("first_name"));
            tf_client_lname.setText(resultSet.getString("last_name"));
            tf_client_phone.setText(resultSet.getString("phone_number"));
            tf_client_email.setText(resultSet.getString("email"));
            anchor_add_client.setDisable(false);
        }catch (NullPointerException e){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "BŁĄD",
                    "Błąd danych",
                    "Musisz wybrać klienta z listy do edycji!");
        }
    }

    @FXML
    void editDrawingAction(MouseEvent event) {

    }
    private void editProject(int projectId){

    }
    @FXML
    void editProjectAction(MouseEvent event) throws SQLException {//to finish later
        saveProjectButtonMode = "EDIT";
        try{
            projectToEditId = tbl_projects.getSelectionModel().getSelectedItem().getId();
            db = new DBConnect();
            Connection connection = db.getCon();
            String query = "SELECT id_customers, customers_name, first_name, last_name, phone_number, email FROM customers" +
                    " where (id_Users="+userId+" AND id_customers="+customerToEditId+")";
            ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            resultSet.first();
            tf_client_name.setText(resultSet.getString("customers_name"));
            tf_client_fname.setText(resultSet.getString("first_name"));
            tf_client_lname.setText(resultSet.getString("last_name"));
            tf_client_phone.setText(resultSet.getString("phone_number"));
            tf_client_email.setText(resultSet.getString("email"));
            anchor_add_project.setDisable(false);
        }catch (NullPointerException e){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "BŁĄD",
                    "Błąd danych",
                    "Musisz wybrać projekt z listy do edycji!");
        }
    }

    @FXML
    void goToDrawingsAction(MouseEvent event) {
        tab_projects.setDisable(true);
        tab_drawings.setDisable(false);
    }

    @FXML
    void goToProjectsAction(MouseEvent event) throws SQLException {
        try{
            currentProjectId = tbl_clients.getSelectionModel().getSelectedItem().getId();
            tab_clients.setDisable(true);
            tab_projects.setDisable(false);
            setProjects("%",currentProjectId);

        }catch (NullPointerException e){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR,
                    "BŁĄD",
                    "Błąd danych",
                    "Musisz wybrać klienta z listy do prześcia do projektów!");
        }
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
     private void saveClient() throws SQLException {
        if (tf_client_name.getText().trim().isEmpty()){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania klienta", "Musisz podać nazwę klienta!");
        }else if (tf_client_email.getText().trim().equals("")){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania klienta", "Musisz podać email klienta!");
        }else if (!tf_client_email.getText().matches(EMAIL_PATTERN)){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania klienta", "Niepoprawny format adresu email!");
        }else{
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
            ps.setString(1,clientName);
            ps.setString(2,clientFirstName);
            ps.setString(3,clientLastName);
            ps.setString(4,clientPhone);
            ps.setString(5,clientEmail);
            ps.setInt(6,userId);
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
        if (saveButtonMode.equals("SAVE")){
            saveClient();
        }else {
            editClient(customerToEditId);
        }

    }

    @FXML
    void saveDrawingAction(MouseEvent event) {

    }

    @FXML
    void saveProjectAction(MouseEvent event) throws SQLException {
        if (saveProjectButtonMode.equals("SAVE")){
            saveProject();
        }else {
            editProject(currentProjectId);
        }

    }
    private void saveProject() throws SQLException {

        if (tf_project_name.getText().trim().isEmpty()){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania projektu", "Musisz podać nazwę projektu!");
        }else if (dp_project_date.getValue()==null){
            DialogWindow dw = new DialogWindow(Alert.AlertType.ERROR, "BŁĄD", "Błąd dodawania projektu", "Musisz datę dodania projektu!");
        }else {
            String projectName = tf_project_name.getText();
            String projectRate = tf_project_rate.getText();
            String projectDate = dp_project_date.getValue().toString();
            String projectDeadline = dp_project_deadline.getValue().toString();

            db = new DBConnect();
            Connection connection = db.getCon();
//            id_projects, project_name, rate_per_drawing, project_date, project_deadline, id_customers
            ps = connection.prepareStatement("INSERT INTO projects (project_name, rate_per_drawing, project_date, project_deadline, id_customers) VALUES (?,?,?,?,?)");
            ps.setString(1,projectName);
            ps.setString(2,projectRate);
            ps.setString(3,projectDate);
            ps.setString(4,projectDeadline);
            ps.setInt(5,currentProjectId);
            ps.executeUpdate();

            tf_project_name.clear();
            tf_project_rate.clear();
            dp_project_date.setValue(null);
            dp_project_deadline.setValue(null);

            setProjects("%",currentProjectId);
            anchor_add_project.setDisable(true);

    }}
    public void initialize() throws SQLException {
        setClients("%");
    }
    private void setClients(String param) throws SQLException {
        //        id_customers, customers_name, first_name, last_name, phone_number, email, id_Users
        String query = "SELECT id_customers, customers_name, first_name, last_name, phone_number, email FROM customers" +
                " where (id_Users="+userId+" AND (concat(customers_name, first_name, last_name, phone_number, email) LIKE '%"+param+"%'))";
        clientsList.clear();
        db = new DBConnect();
        Connection connection = db.getCon();
        ps = connection.prepareStatement(query);
        ResultSet resultSet=ps.executeQuery();
        while (resultSet.next()){
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
        col_client_fname.setCellValueFactory(new PropertyValueFactory<Client,String>("firstName"));
        col_client_lname.setCellValueFactory(new PropertyValueFactory<Client,String>("lastName"));
        col_client_phone.setCellValueFactory(new PropertyValueFactory<Client,String>("phoneNumber"));
        col_client_email.setCellValueFactory(new PropertyValueFactory<Client,String>("email"));
        tbl_clients.setItems(clientsList);
    }
    private void setProjects(String parameter,int currentProject) throws SQLException{
        String query ="select id_projects, project_name, customers_name, project_date, project_deadline,rate_per_drawing,p.id_customers from projects as p join customers as c on (c.id_customers=p.id_customers) where id_users ="+userId+" order by project_name";
        projectsList.clear();
        db =new DBConnect();
        Connection connection = db.getCon();
        ps = connection.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            Project project = new Project(resultSet.getInt("id_projects"),
                    resultSet.getString("project_name"),
                    resultSet.getInt("rate_per_drawing"),
                    resultSet.getDate("project_date"),
                    resultSet.getDate("project_deadline"),
                    resultSet.getInt("p.id_customers"),
                    resultSet.getString("customers_name"));
            projectsList.add(project);
        }
        col_project_id.setCellValueFactory(new PropertyValueFactory<Project,Integer>("id"));
        col_project_name.setCellValueFactory(new PropertyValueFactory<Project,String>("projectName"));
        col_project_date.setCellValueFactory(new PropertyValueFactory<Project,Date>("projectDate"));
        col_project_deadline.setCellValueFactory(new PropertyValueFactory<Project,Date>("deadline"));
        col_project_rate.setCellValueFactory(new PropertyValueFactory<Project,Integer>("rate"));
        col_project_client_name.setCellValueFactory(new PropertyValueFactory<Project,String>("clientName"));
        tbl_projects.setItems(projectsList);
    }
    private void setDrawings(){
        //        String query = "select d.id_drawings, drawing_name, drawing_num, width, height, individual_rate,project_name " +
//                "from drawings as d left " +
//                "join projects as p on (d.id_projects=p.id_projects) " +
//                "join projects_has_users as phu on (phu.projects_id_projects = p.id_projects) " +
//                "where Users_id_Users =?";
    }

}

