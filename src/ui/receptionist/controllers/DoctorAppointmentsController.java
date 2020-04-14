package ui.receptionist.controllers;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.MasterController;
import ui.receptionist.DocAppointmentTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DoctorAppointmentsController extends MasterController implements Initializable {
    @FXML
    private Label doctorUserName,doctorRoom;

    @FXML
    private TextField filterDoctorName;

    @FXML
    private TableView<DocAppointmentTable> docAppointmentTable;

    @FXML
    private TableColumn<DocAppointmentTable, String> colName, colAppDate, colAppTime, colPhoneNo;

    /**
     * Updates the appointments of the doctor
     * @return name the name of the doctor
     * @throws SQLException
     */
    private String update() throws SQLException {
        File file = null;
        Scanner scan = null;
        try {
            file = new File("outFile.txt");
            String path = file.getAbsolutePath();
            scan = new Scanner(file);
        }catch( FileNotFoundException e) {
            // if database is not reached properly
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Could not access database. Please try again.");
            alert.show();
        }
        String name = scan.nextLine();
        scan.close();
        //file.delete();

        return name;
    }

    /**
     * Gets the all patients data
     * @throws SQLException
     */
    private void getPatientData() throws SQLException {
        int doctorKey = database.Database.findDoctorKey(update());

        ObservableList<DocAppointmentTable> obList4 = FXCollections.observableArrayList();
        try {
            ResultSet rs = myConn.createStatement().executeQuery("SELECT patient.`name`, appointment.`date`, appointment.`time`, patient.`patient_phoneNumber` FROM patient, appointment, doctor WHERE appointment.`doctor_id` = doctor.`doctor_id` AND appointment.`patient_id` = patient.`patient_id` AND doctor.`doctor_id`= '" + doctorKey + "' AND appointment.`date` >= '"+ Database.date() +"' ORDER BY `date` DESC");

            while (rs.next()) {
                obList4.add(new DocAppointmentTable(rs.getString("name"), rs.getString("date"),
                        rs.getString("time"), rs.getString("patient_phoneNumber")));
            }
        }catch (SQLException ex){}

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAppDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAppTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        docAppointmentTable.setItems(obList4);
    }

    /**
     * Gets the filtered data in wanted order
     * @throws SQLException
     */
    private void getFilteredPatientDataByOrder() throws SQLException {
        int doctorKey = database.Database.findDoctorKey(update());

        String patientNameFilter = filterDoctorName.getText();
        ObservableList<DocAppointmentTable> obList3 = FXCollections.observableArrayList();
        try {
            Connection con = Database.myConn;
            ResultSet rs = con.createStatement().executeQuery("SELECT patient.`name`, appointment.`date`, appointment.`time`, patient.`patient_phoneNumber` FROM patient, appointment, doctor WHERE appointment.`doctor_id` = doctor.`doctor_id` AND appointment.`patient_id` = patient.`patient_id` AND doctor.`doctor_id`= '" + doctorKey + "' AND appointment.`date` >= '"+ Database.date() +"' AND patient.`name` LIKE '%" + patientNameFilter + "%' ORDER BY `date` DESC ");

            while (rs.next()) {
                obList3.add(new DocAppointmentTable(rs.getString("name"), rs.getString("date"),
                        rs.getString("time"), rs.getString("patient_phoneNumber")));
            }
        }catch (SQLException ex){}

        docAppointmentTable.setItems(obList3);
    }

    /**
     * filters the patients
     * @param e finds filtered upcoming patients
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
        docAppointmentTable.refresh();
    }

    /**
     * initializes the page and setting labels information according to user
     * @param location the location of that page
     * @param resources sources for that page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            update();
            getPatientData();
            doctorUserName.setText(update());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
