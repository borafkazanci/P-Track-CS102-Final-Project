/**
 * this control class shows patient scene
 */
package ui.patient;

import com.jfoenix.transitions.template.JFXAnimationTemplateAction;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.util.Callback;
import ui.MasterController;
import ui.doctor.UpcomingTable;

public class PatientController extends MasterController implements Initializable {

    @FXML
    private Button patientLogOutButton;

    @FXML
    private Label patientNameLabel, patientID, patientGender, patientBloodType, patientInsurance, patientPhoneNumber, topBarPatientName;

    @FXML
    private TextArea patientAddress;

    @FXML
    private TableView<DoctorsTable> appointmentsView;

    @FXML
    private TableColumn<DoctorsTable, String> colDocName, colDocDepartment, colAppointmentDate, colAppointmentTime, colDocRoom;

    /**
     * a constructor creates new patient controller class
     */
    public PatientController(){}

    /**
     * a method which listens the log out button from patient scene
     * @param a the action which is pressing log out button
     * @throws IOException
     */
    @FXML
    private void patientLogOut(ActionEvent a) throws IOException {
        //controlling the method
        System.out.println("Logged out from Patient panel!");

        //back to auth scene
        authLoader(a);
    }

    /**
     * Gets appointments of the patient who entered to the application.
     * @throws SQLException
     */
    private void getDoctorData() throws SQLException {
        //takes appointment data to table view
        int patientId = Database.findPatientKey(Database.getUserName());
        ObservableList<DoctorsTable> obList3 = FXCollections.observableArrayList();
        try {
            ResultSet rs = myConn.createStatement().executeQuery("SELECT patient.`patient_id` , doctor.`name`, doctor.`department`, appointment.`date`, appointment.`time`, doctor.`room_number` FROM patient, doctor, appointment WHERE appointment.`patient_id` = patient.`patient_id` AND appointment.`doctor_id` = doctor.`doctor_id` AND patient.`patient_id` = '"+ patientId  +"' ORDER BY `date` DESC");

            //adds columns to a list
            while (rs.next()) {
                obList3.add(new DoctorsTable(rs.getString("name"), rs.getString("department"),
                        rs.getString("date"), rs.getString("time"), rs.getString("room_number")));
            }
        }catch (SQLException ex){}

        colDocName.setCellValueFactory(new PropertyValueFactory<>("docName"));
        colDocDepartment.setCellValueFactory(new PropertyValueFactory<>("docDepartment"));
        colAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        colAppointmentTime.setCellValueFactory(new PropertyValueFactory<>("appTime"));
        colDocRoom.setCellValueFactory(new PropertyValueFactory<>("docRoom"));

        appointmentsView.setItems(obList3);
    }

    /**
     * updates the labels of the patient scene according to that specific patient
     * @throws SQLException
     */
    private void update() throws SQLException{
        // finds patient key
        int patientKey = database.Database.findPatientKey(Database.getUserName());

        // adds the details of patient to a string infoList
        ArrayList<String> infoList = database.Database.patientDetails(patientKey);

        // sets these details to labels
        topBarPatientName.setText(infoList.get(0));
        patientNameLabel.setText(infoList.get(0));
        patientID.setText(infoList.get(1));
        patientGender.setText(infoList.get(2));
        patientBloodType.setText(infoList.get(3));
        patientAddress.setText(infoList.get(5));
        patientAddress.setEditable(false);
        patientInsurance.setText(infoList.get(6));
        patientPhoneNumber.setText(infoList.get(7));
    }

    /**
     * Puts all data in table view when the screen opened
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            update();
            getDoctorData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
