package ui.receptionist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import database.Database;
import javafx.util.Callback;
import ui.MasterController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceptionistController extends MasterController implements Initializable {
    //Dashboard
    @FXML
    private Button logoutButton;

    @FXML
    private Label receptionistUsernameLabel, numberOfAppointmentsLabel, timeLabel, dateLabel;

    @FXML
    private TextArea noteTextArea;

    //Recent patient in Dashboard
    //Recent Patient 1
    @FXML
    private Label recentPatientLabel1, idNoLabel1, passedTimeLabel1, passedDateLabel1, phoneNumberLabel1, doctorName1, departmentName1;

    @FXML
    private Button detailsButton1;

    //Recent Patient 2
    @FXML
    private Label recentPatientLabel2, idNoLabel2, passedTimeLabel2, passedDateLabel2, phoneNumberLabel2, doctorName2, departmentName2;

    @FXML
    private Button detailsButton2;

    //Recent Patient 3
    @FXML
    private Label recentPatientLabel3, idNoLabel3, passedTimeLabel3, passedDateLabel3, phoneNumberLabel3, doctorName3, departmentName3;

    @FXML
    private Button detailsButton3;

    //Recent Patient 4
    @FXML
    private Label recentPatientLabel4, idNoLabel4, passedTimeLabel4, passedDateLabel4, phoneNumberLabel4, doctorName4, departmentName4;

    @FXML
    private Button detailsButton4;

    //Recent Patient 5
    @FXML
    private Label recentPatientLabel5, idNoLabel5, passedTimeLabel5, passedDateLabel5, phoneNumberLabel5, doctorName5, departmentName5;

    @FXML
    private Button detailsButton5;

    //Recent Patient in Patient Board
    //Recent 11
    @FXML
    private Label recentPatientLabel11, idNoLabel11, passedTimeLabel11, passedDateLabel11, phoneNumberLabel11, doctorName11, departmentName11;

    @FXML
    private Button detailsButton11;

    //Recent21
    @FXML
    private Label recentPatientLabel21, idNoLabel21, passedTimeLabel21, passedDateLabel21, phoneNumberLabel21, doctorName21, departmentName21;

    @FXML
    private Button detailsButton21;

    //Recent 31
    @FXML
    private Label recentPatientLabel31, idNoLabel31, passedTimeLabel31, passedDateLabel31, phoneNumberLabel31, doctorName31, departmentName31;

    @FXML
    private Button detailsButton31;

    //Recent 41
    @FXML
    private Label recentPatientLabel41, idNoLabel41, passedTimeLabel41, passedDateLabel41, phoneNumberLabel41, doctorName41, departmentName41;

    @FXML
    private Button detailsButton41;

    //Recent 51
    @FXML
    private Label recentPatientLabel51, idNoLabel51, passedTimeLabel51, passedDateLabel51, phoneNumberLabel51, doctorName51, departmentName51;

    @FXML
    private Button detailsButton51;

    //add new patient
    @FXML
    private Button addNewPatientButton;

    //Patient Table View Variables
    @FXML
    private TableView<ModelTable> patientTable;

    @FXML
    private TableColumn<ModelTable,String> colName, colBirthDate, colID, colSex, colBloodType, colAddAppointment, colDetails, colChangeInfo;

    @FXML
    private TextField filterPatientName;

    //Doctor Table View Variables
    @FXML
    private TableView<DoctorTable> doctorTable;

    @FXML
    private TableColumn<DoctorTable,String> colDoctorName, colDoctorDepartment, colDoctorRoom, colCheckApp, colAvailability, colPhoneNo;

    @FXML
    private TextField filterDoctorName;

    //variables
    private ModelTable p;
    private DoctorTable d;

    /**
     * Default constructor
     */
    public ReceptionistController() {}

    /**
     * Opens add patient fxml file
     * @param e takes button as an action
     * @throws IOException
     */
    @FXML
    private void openAddPatient(ActionEvent e) throws IOException{
        System.out.println("Add patient opened!");
        loadWindow("ui/receptionist/FXML/addPatientScene.fxml", "Add New Patient");
    }

    /**
     * Quits from receptionist page
     * @param e takes button as an action
     * @throws IOException
     */
    @FXML
    private void logoutReceptionist(ActionEvent e) throws IOException {
        System.out.println("Logged out from Receptionist panel!");

        //back to auth scene
        authLoader(e);
    }

    /**
     * Gets patient data from database and adds 3 dynamic table view button
     */
    private void getPatientData(){
        getPatientsData();

        //Adding Appointment Button
        Callback<TableColumn<ModelTable, String>,TableCell<ModelTable, String>> cellFactory = (param) -> {
            //make table cell with button
            final TableCell<ModelTable, String> cell = new TableCell<ModelTable, String>(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else{
                        final Button addAppointmentButton = new Button("Add Appointment");
                        addAppointmentButton.setOnAction(event -> {
                            p = getTableView().getItems().get(getIndex());
                            idCarry(p.getId());
                            loadWindow("ui/receptionist/FXML/addAppointment.fxml", "Add Appointment");
                        });
                        setGraphic(addAppointmentButton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        //add button to the table
        colAddAppointment.setCellFactory(cellFactory);

        //adding patient details button
        Callback<TableColumn<ModelTable, String>,TableCell<ModelTable, String>> cellFactory1 = (param) -> {
            //make table cell with button
            final TableCell<ModelTable, String> cell1 = new TableCell<ModelTable, String>(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                    }
                    else{
                        final Button patientDetailsButton = new Button("Details");
                        patientDetailsButton.setOnAction(event -> {
                            p = getTableView().getItems().get(getIndex());
                            idCarry(p.getId());
                            loadWindow("ui/receptionist/FXML/patientDetails.fxml", "Patient Details");
                        });
                        setGraphic(patientDetailsButton);
                    }
                    setText(null);
                }
            };
            return cell1;
        };
        //add button to the table
        colDetails.setCellFactory(cellFactory1);

        //Adding change patient info button
        Callback<TableColumn<ModelTable, String>,TableCell<ModelTable, String>> cellFactory2 = (param) -> {
            //make table cell with button
            final TableCell<ModelTable, String> cell2 = new TableCell<ModelTable, String>(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else{
                        final Button changePatientInfoButton = new Button("Change Info");
                        changePatientInfoButton.setOnAction(event -> {
                            p = getTableView().getItems().get(getIndex());
                            idCarry(p.getId());
                            loadWindow("ui/receptionist/FXML/changePatientInfo.fxml", "Change Patient Info");
                        });
                        setGraphic(changePatientInfoButton);
                        setText(null);
                    }
                }
            };
            return cell2;
        };
        //add button to the table
        colChangeInfo.setCellFactory(cellFactory2);
    }

    /**
     * Gets patient data from database and adds 3 dynamic table view button
     */
    private void getPatientsData(){
        ObservableList<ModelTable> obList = FXCollections.observableArrayList();
        try {
            Connection con = Database.myConn;
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM patient");

            while (rs.next()) {
                obList.add(new ModelTable(rs.getString("name"), rs.getString("birth_date"),
                        rs.getString("citizenship_id"), rs.getString("insurance"),
                        rs.getString("gender"), rs.getString("blood_type")));
            }
        }catch (SQLException ex){}

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colBloodType.setCellValueFactory(new PropertyValueFactory<>("bloodType"));

        patientTable.setItems(obList);
        patientTable.refresh();
    }

    /**
     * Gets filtered patient data from database and adds 3 dynamic table view button
     */
    private void getFilteredPatientData(){
        ObservableList<ModelTable> listFiltered = FXCollections.observableArrayList();
        try{
            String nameFilter = filterPatientName.getText();
            ResultSet rs = myConn.createStatement().executeQuery("SELECT * FROM patient WHERE name LIKE '%" + nameFilter + "%' ");

            while (rs.next()) {
                listFiltered.add(new ModelTable(rs.getString("name"), rs.getString("birth_date"),
                        rs.getString("citizenship_id"), rs.getString("insurance"),
                        rs.getString("gender"), rs.getString("blood_type")));
            }
        }catch (SQLException ex){}

        patientTable.setItems(listFiltered);
        patientTable.refresh();
    }

    /**
     * Gets doctor data from database and adds 1 dynamic table view button
     */
    private void getDoctorData(){
        getDoctorsData();

        //Check Appointment Button
        Callback<TableColumn<DoctorTable, String>,TableCell<DoctorTable, String>> cellFactory3 = (param) -> {
            //make table cell with button
            final TableCell<DoctorTable, String> cell3 = new TableCell<DoctorTable, String>(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else{
                        final Button currentAppointmentButton = new Button("Current Appointments");
                        currentAppointmentButton.setOnAction(event -> {
                            d = getTableView().getItems().get(getIndex());
                            idCarry(d.getName());
                            loadWindow("ui/receptionist/FXML/doctorAppointments.fxml", "Current Appointments");
                        });
                        setGraphic(currentAppointmentButton);
                        setText(null);
                    }
                }
            };
            return cell3;
        };
        //add button to the table
        colCheckApp.setCellFactory(cellFactory3);
    }

    /**
     * Gets the all data of the hospital doctors
     */
    private void getDoctorsData(){
        ObservableList<DoctorTable> obList2 = FXCollections.observableArrayList();
        String text = "";
        try {
            ResultSet rs2 = myConn.createStatement().executeQuery("SELECT * FROM doctor");

            while (rs2.next()) {
                int id = rs2.getInt("doctor_id");
                boolean availability = Database.doctorAvailability(id);
                if ( availability )
                    text = "Available";
                else
                    text = "Not Available";

                obList2.add(new DoctorTable(rs2.getString("name"), rs2.getString("department"),
                        rs2.getString("room_number"), text ,rs2.getString("phone_number") ));
            }
        }catch (SQLException ex){}

        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDoctorDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colDoctorRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colAvailability.setCellValueFactory( new PropertyValueFactory<>("availability"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        doctorTable.setItems(obList2);
        doctorTable.refresh();
    }

    /**
     * Gets filtered doctor data from database and adds 1 dynamic table view button
     */
    private void getFilteredDoctorData(){
        ObservableList<DoctorTable> listFiltered2 = FXCollections.observableArrayList();
        try{
            String nameFilter = filterDoctorName.getText();
            ResultSet rs2 = myConn.createStatement().executeQuery("SELECT * FROM doctor WHERE name LIKE '%" + nameFilter + "%' ");

            while (rs2.next()) {
                //sets availability of doctor
                int id = rs2.getInt("doctor_id");
                boolean availability = Database.doctorAvailability(id);
                String text;
                if ( availability )
                {
                    text = "Available";
                }
                else
                {
                    text = "Not Available";
                }

                listFiltered2.add(new DoctorTable(rs2.getString("name"), rs2.getString("department"),
                        rs2.getString("room_number"), text ,rs2.getString("phone_number") ));
            }
        }catch (SQLException ex){}

        doctorTable.setItems(listFiltered2);
        doctorTable.refresh();
    }


    /**
     * Initialize the screen
     * @param location, resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //loads the patient and doctor data to table
        getPatientData();
        System.out.println("Patient data is uploaded");
        getDoctorData();
        System.out.println("Doctor data is uploaded");
        initializeRecentPatients();
        System.out.println("Recent patient appointment data is uploaded");
        recent1EqualsRecent2();
        System.out.println("All data is uploaded");
        try {
            timeLabel.setText(Database.time());
            dateLabel.setText(Database.date());
            receptionistUsernameLabel.setText(Database.getUserName());
            numberOfAppointmentsLabel.setText("" + Database.totalAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        patientTable.refresh();
        doctorTable.refresh();
    }

    /**
     * finds patient name from list
     * @param e takes button as an action
     */
    @FXML
    private void findPatientNameFromList(ActionEvent e) throws InvocationTargetException {
        if (filterPatientName.getText().equals("")) {
            getPatientsData();
            System.out.println("Empty filter");
        }
        else
            getFilteredPatientData();
        patientTable.refresh();
        initializeRecentPatients();
        recent1EqualsRecent2();
        try {
            timeLabel.setText(Database.time());
            dateLabel.setText(Database.date());
            numberOfAppointmentsLabel.setText("" + Database.totalAppointments());
        }catch( SQLException ex){}
    }

    /**
     * finds doctor name from list
     * @param e takes button as an action
     */
    @FXML
    private void findDoctorNameFromList(ActionEvent e) throws InvocationTargetException {
        if (filterDoctorName.getText().equals("")) {
            getDoctorData();
            System.out.println("Empty filter");
        }
        else
            getFilteredDoctorData();
        doctorTable.refresh();
    }

    /**
     * Opens details pages for recent patients from detail button
     * @param e takes button as an action
     */
    @FXML
    private void recentInDashboard(ActionEvent e){
        recent1EqualsRecent2();

        int size = 0;
        try {
            size = Database.appointmentOrder().size();
            System.out.println(size);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        int finalSize = size;
        if (finalSize >= 3) {
            detailsButton1.setOnAction(event -> {
                getButtonAction(1, finalSize);
            });

            detailsButton11.setOnAction(event -> {
                getButtonAction(1, finalSize);
            });
        }
        if (finalSize >= 6) {
            detailsButton2.setOnAction(event -> {
                getButtonAction(2, finalSize);
            });

            detailsButton21.setOnAction(event -> {
                getButtonAction(2, finalSize);
            });
        }
        if (finalSize >= 9) {
            detailsButton3.setOnAction(event -> {
                getButtonAction(3, finalSize);
            });

            detailsButton31.setOnAction(event -> {
                getButtonAction(3, finalSize);
            });
        }
        if (finalSize >= 12) {
            detailsButton4.setOnAction(event -> {
                getButtonAction(4, finalSize);
            });

            detailsButton41.setOnAction(event -> {
                getButtonAction(4, finalSize);
            });
        }
        if (finalSize >= 15) {
            detailsButton5.setOnAction(event -> {
                getButtonAction(5, finalSize);
            });

            detailsButton51.setOnAction(event -> {
                getButtonAction(5, finalSize);
            });
        }
    }

    /**
     * From button no it takes different patient's details
     * @param buttonNo for the define which button clicked
     * @param arraySize places the given array's size
     */
    private void getButtonAction(int buttonNo, int arraySize){
        if (buttonNo == 1){
            ArrayList<String> infoList = null;
            try {
                String id1 = (String) Database.appointmentOrder().get(arraySize - 3);
                infoList = database.Database.patientDetails(Integer.parseInt(id1));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            idCarry(infoList.get(1));
            loadWindow("ui/receptionist/FXML/patientDetails.fxml", "Patient Details");
        }
        else if (buttonNo == 2){
            ArrayList<String> infoList2 = null;
            try {
                String id2 = (String) Database.appointmentOrder().get(arraySize - 6);
                infoList2 = database.Database.patientDetails(Integer.parseInt(id2));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            idCarry(infoList2.get(1));
            loadWindow("ui/receptionist/FXML/patientDetails.fxml", "Patient Details");
        }
        else if (buttonNo == 3){
            ArrayList<String> infoList3 = null;
            try {
                String id3 = (String) Database.appointmentOrder().get(arraySize - 9);
                infoList3 = database.Database.patientDetails(Integer.parseInt(id3));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            idCarry(infoList3.get(1));
            loadWindow("ui/receptionist/FXML/patientDetails.fxml", "Patient Details");
        }
        else if (buttonNo == 4){
            ArrayList<String> infoList4 = null;
            try {
                String id4 = (String) Database.appointmentOrder().get(arraySize - 12);
                infoList4 = database.Database.patientDetails(Integer.parseInt(id4));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            idCarry(infoList4.get(1));
            loadWindow("ui/receptionist/FXML/patientDetails.fxml", "Patient Details");
        }
        else if (buttonNo == 5){
            ArrayList<String> infoList5 = null;
            try {
                String id5 = (String) Database.appointmentOrder().get(arraySize - 15);
                infoList5 = database.Database.patientDetails(Integer.parseInt(id5));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            idCarry(infoList5.get(1));
            loadWindow("ui/receptionist/FXML/patientDetails.fxml", "Patient Details");
        }
    }

    /**
     * Initializes all recent patient data
     */
    private void initializeRecentPatients(){
        int size = 0;
        try {
            size = Database.appointmentOrder().size();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            if (size >= 3) {
                //initialize recent patient1
                String id1 = (String) Database.appointmentOrder().get(size - 3);
                String idDoctor1 = (String) Database.appointmentOrder().get(size - 2);
                passedTimeLabel1.setText((String) Database.appointmentOrder().get(size - 1));
                ArrayList<String> infoList = database.Database.patientDetails(Integer.parseInt(id1));
                ArrayList<String> doctorList = database.Database.doctorDetails(Integer.parseInt(idDoctor1));

                recentPatientLabel1.setText(infoList.get(0));
                idNoLabel1.setText(infoList.get(1));
                phoneNumberLabel1.setText(infoList.get(7));
                doctorName1.setText(doctorList.get(0));
                departmentName1.setText(doctorList.get(1));
            }
            if (size >= 6) {
                //initialize recent patient2
                String id2 = (String) Database.appointmentOrder().get(size - 6);
                String idDoctor2 = (String) Database.appointmentOrder().get(size - 5);
                passedTimeLabel2.setText((String) Database.appointmentOrder().get(size - 4));
                ArrayList<String> infoList2 = database.Database.patientDetails(Integer.parseInt(id2));
                ArrayList<String> doctorList2 = database.Database.doctorDetails(Integer.parseInt(idDoctor2));

                recentPatientLabel2.setText(infoList2.get(0));
                idNoLabel2.setText(infoList2.get(1));
                phoneNumberLabel2.setText(infoList2.get(7));
                doctorName2.setText(doctorList2.get(0));
                departmentName2.setText(doctorList2.get(1));
            }
            if (size >= 9) {
                //initialize recent patient3
                String id3 = (String) Database.appointmentOrder().get(size - 9);
                String idDoctor3 = (String) Database.appointmentOrder().get(size - 8);
                passedTimeLabel3.setText((String) Database.appointmentOrder().get(size - 7));
                ArrayList<String> infoList3 = database.Database.patientDetails(Integer.parseInt(id3));
                ArrayList<String> doctorList3 = database.Database.doctorDetails(Integer.parseInt(idDoctor3));

                recentPatientLabel3.setText(infoList3.get(0));
                idNoLabel3.setText(infoList3.get(1));
                phoneNumberLabel3.setText(infoList3.get(7));
                doctorName3.setText(doctorList3.get(0));
                departmentName3.setText(doctorList3.get(1));
            }
            if (size >= 12) {
                //initialize recent patient4
                String id4 = (String) Database.appointmentOrder().get(size - 12);
                String idDoctor4 = (String) Database.appointmentOrder().get(size - 11);
                passedTimeLabel4.setText((String) Database.appointmentOrder().get(size - 10));
                ArrayList<String> infoList4 = database.Database.patientDetails(Integer.parseInt(id4));
                ArrayList<String> doctorList4 = database.Database.doctorDetails(Integer.parseInt(idDoctor4));

                recentPatientLabel4.setText(infoList4.get(0));
                idNoLabel4.setText(infoList4.get(1));
                phoneNumberLabel4.setText(infoList4.get(7));
                doctorName4.setText(doctorList4.get(0));
                departmentName4.setText(doctorList4.get(1));
            }
            if (size >= 15) {
                //initialize recent patient5
                String id5 = (String) Database.appointmentOrder().get(size - 15);
                String idDoctor5 = (String) Database.appointmentOrder().get(size - 14);
                passedTimeLabel5.setText((String) Database.appointmentOrder().get(size - 13));
                ArrayList<String> infoList5 = database.Database.patientDetails(Integer.parseInt(id5));
                ArrayList<String> doctorList5 = database.Database.doctorDetails(Integer.parseInt(idDoctor5));

                recentPatientLabel5.setText(infoList5.get(0));
                idNoLabel5.setText(infoList5.get(1));
                phoneNumberLabel5.setText(infoList5.get(7));
                doctorName5.setText(doctorList5.get(0));
                departmentName5.setText(doctorList5.get(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Defines recent patients in patient board from dashboard
     */
    private void recent1EqualsRecent2(){
        //names
        recentPatientLabel11.setText(recentPatientLabel1.getText());
        recentPatientLabel21.setText(recentPatientLabel2.getText());
        recentPatientLabel31.setText(recentPatientLabel3.getText());
        recentPatientLabel41.setText(recentPatientLabel4.getText());
        recentPatientLabel51.setText(recentPatientLabel5.getText());

        //ids
        idNoLabel11.setText(idNoLabel1.getText());
        idNoLabel21.setText(idNoLabel2.getText());
        idNoLabel31.setText(idNoLabel3.getText());
        idNoLabel41.setText(idNoLabel4.getText());
        idNoLabel51.setText(idNoLabel5.getText());

        //phone numbers
        phoneNumberLabel11.setText(phoneNumberLabel1.getText());
        phoneNumberLabel21.setText(phoneNumberLabel2.getText());
        phoneNumberLabel31.setText(phoneNumberLabel3.getText());
        phoneNumberLabel41.setText(phoneNumberLabel4.getText());
        phoneNumberLabel51.setText(phoneNumberLabel5.getText());

        //passed times
        passedTimeLabel11.setText(passedTimeLabel1.getText());
        passedTimeLabel21.setText(passedTimeLabel2.getText());
        passedTimeLabel31.setText(passedTimeLabel3.getText());
        passedTimeLabel41.setText(passedTimeLabel4.getText());
        passedTimeLabel51.setText(passedTimeLabel5.getText());

        //passed dates
        passedDateLabel11.setText(passedDateLabel1.getText());
        passedDateLabel21.setText(passedDateLabel2.getText());
        passedDateLabel31.setText(passedDateLabel3.getText());
        passedDateLabel41.setText(passedDateLabel4.getText());
        passedDateLabel51.setText(passedDateLabel5.getText());

        //doctor names
        doctorName11.setText(doctorName1.getText());
        doctorName21.setText(doctorName2.getText());
        doctorName31.setText(doctorName3.getText());
        doctorName41.setText(doctorName4.getText());
        doctorName51.setText(doctorName5.getText());

        //doctor departments
        departmentName11.setText(departmentName1.getText());
        departmentName21.setText(departmentName2.getText());;
        departmentName31.setText(departmentName3.getText());;
        departmentName41.setText(departmentName4.getText());;
        departmentName51.setText(departmentName5.getText());;
    }
}
