/*
 * the class which controls and shows the page for doctors
 */
package ui.doctor;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ui.MasterController;
import ui.receptionist.DoctorTable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorController extends MasterController implements Initializable {

    @FXML
    private Button logoutButton,filterButton;

    @FXML
    private Label doctorUserName,doctorRoom;

    @FXML
    private TextField filterDoctorName;

    @FXML
    private TableView<UpcomingTable> upcomingTable;

    @FXML
    private TableColumn<UpcomingTable, String> colName, colAppDate, colAppTime, colPhoneNo, colAddPrescription;

    //variables
    private UpcomingTable u;

    /**
     * logs out the doctor page returns to login page
     * @param e gives action to logout button
     * @throws IOException
     */
    @FXML
    private void logoutDoctor(ActionEvent e) throws IOException{
        System.out.println("Logged out from Doctor panel!");

        //back to auth scene
        authLoader(e);
    }

    /**
     * gets the patients data for showing in the doctor page
     * @throws SQLException
     */
    private void getPatientData() throws SQLException {
        getPatientsData();

        //Add Prescription Button
        Callback<TableColumn<UpcomingTable, String>,TableCell<UpcomingTable, String>> cellFactory = (param) -> {
            //make table cell with button
            final TableCell<UpcomingTable, String> cell = new TableCell<UpcomingTable, String>(){
                @Override
                public void updateItem(String item, boolean empty){
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else{
                        final Button addAppointmentButton = new Button("Set Prescription");
                        addAppointmentButton.setOnAction(event -> {
                            u = getTableView().getItems().get(getIndex());
                            idCarry(u.getName());
                            loadWindow("ui/doctor/FXML/doctorPrescriptionPage.fxml", "Set Prescription");
                        });
                        setGraphic(addAppointmentButton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colAddPrescription.setCellFactory(cellFactory);
    }

    /**
     * Gets the data of the patients
     * @throws SQLException
     */
    private void getPatientsData() throws SQLException {
        int doctorId = Database.findDoctorKey(Database.getUserName());
        ObservableList<UpcomingTable> obList3 = FXCollections.observableArrayList();
        try {
            ResultSet rs = myConn.createStatement().executeQuery("SELECT patient.`name`, appointment.`date`, appointment.`time`, patient.`patient_phoneNumber` FROM patient, appointment, doctor WHERE appointment.`doctor_id` = doctor.`doctor_id` AND appointment.`patient_id` = patient.`patient_id` AND doctor.`doctor_id`= '" + doctorId + "' AND appointment.`date` >= '"+ Database.date() +"' ORDER BY `date` DESC");

            while (rs.next()) {
                obList3.add(new UpcomingTable(rs.getString("name"), rs.getString("date"),
                        rs.getString("time"), rs.getString("patient_phoneNumber")));
            }
        }catch (SQLException ex){}

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAppDate.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        colAppTime.setCellValueFactory(new PropertyValueFactory<>("appTime"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        upcomingTable.setItems(obList3);
    }

    /**
     * Gets only the wanted patients data
     * @throws SQLException
     */
    private void getFilteredPatientDataByOrder() throws SQLException {
        int doctorId = Database.findDoctorKey(Database.getUserName());
        String patientNameFilter = filterDoctorName.getText();
        ObservableList<UpcomingTable> obList3 = FXCollections.observableArrayList();
        try {
            Connection con = Database.myConn;
            ResultSet rs = con.createStatement().executeQuery("SELECT patient.`name`, appointment.`date`, appointment.`time`, patient.`patient_phoneNumber` FROM patient, appointment, doctor WHERE appointment.`doctor_id` = doctor.`doctor_id` AND appointment.`patient_id` = patient.`patient_id` AND doctor.`doctor_id`= '" + doctorId + "' AND appointment.`date` >= '"+ Database.date() +"' AND patient.`name` LIKE '%" + patientNameFilter + "%' ORDER BY `date` DESC ");

            while (rs.next()) {
                obList3.add(new UpcomingTable(rs.getString("name"), rs.getString("date"),
                        rs.getString("time"), rs.getString("patient_phoneNumber")));
            }
        }catch (SQLException ex){}

        upcomingTable.setItems(obList3);
    }

    /**
     *  Finds the upcoming patients
     * @param e gives action to filter button
     * @throws SQLException
     */
    @FXML
    private void findUpcomingPatientFromList(ActionEvent e) throws SQLException {
        if (filterDoctorName.getText().equals("")) {
            getPatientData();
            System.out.println("Empty filter");
        }
        else
            getFilteredPatientDataByOrder();
        upcomingTable.refresh();
    }

    /**
     * initializes the page and setting labels information according to user
     * @param location the location of that page
     * @param resources sources for that page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            doctorUserName.setText(Database.getUserName());
            doctorRoom.setText("Room No:" + Database.doctorDetails(Database.findDoctorKey(Database.getUserName())).get(2));
            getPatientData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
