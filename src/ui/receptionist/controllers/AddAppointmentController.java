/*
 * the class which controls and shows add appointment feature of the receptionist page
 */
package ui.receptionist.controllers;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.stage.Stage;
import ui.MasterController;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddAppointmentController extends MasterController implements Initializable {

    @FXML
    private Label patientName,patientSurname;

    @FXML
    private DatePicker appointmentDate;

    @FXML
    private Button saveAppointmentButton;

    @FXML
    private MenuButton hourButton, minuteButton;

    @FXML
    private MenuItem hour08, hour09, hour10, hour11, hour12, hour13, hour14, hour15, hour16, minute00, minute15, minute30, minute45;

    @FXML
    private ChoiceBox<String> choiceBox;

    //variables
    private String patientdbId;

    /**
     * the constructor which initializes patient who will have the appointment
     */
    public AddAppointmentController(){
        patientdbId = "";
    }

    /**
     * sets menu item to 08
     * @param e gives action to menu item
     */
    @FXML
    private  void hourChosen8( ActionEvent e){
        hourButton.setText("08");
    }

    /**
     * sets menu item to 09
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen9( ActionEvent e){
        hourButton.setText("09");
    }

    /**
     * sets menu item to 10
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen10( ActionEvent e){
        hourButton.setText("10");
    }

    /**
     * sets menu item to 11
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen11( ActionEvent e){
        hourButton.setText("11");
    }

    /**
     * sets menu item to 12
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen12( ActionEvent e){
        hourButton.setText("12");
    }

    /**
     * sets menu item to 13
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen13( ActionEvent e){
        hourButton.setText("13");
    }

    /**
     * sets menu item to 14
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen14( ActionEvent e){
        hourButton.setText("14");
    }

    /**
     * sets menu item to 15
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen15( ActionEvent e){
        hourButton.setText("15");
    }

    /**
     * sets menu item to 16
     * @param e gives action to menu item
     */
    @FXML
    private void hourChosen16( ActionEvent e){
        hourButton.setText("16");
    }

    /**
     * sets menu item to 00
     * @param e gives action to menu item
     */
    @FXML
    private void minuteChosen00( ActionEvent e){
        minuteButton.setText("00");
    }

    /**
     * sets menu item to 15
     * @param e gives action to menu item
     */
    @FXML
    private void minuteChosen15( ActionEvent e){
        minuteButton.setText("15");
    }

    /**
     * sets menu item to 30
     * @param e gives action to menu item
     */
    @FXML
    private void minuteChosen30( ActionEvent e){
        minuteButton.setText("30");
    }

    /**
     * sets menu item to 45
     * @param e gives action to menu item
     */
    @FXML
    private void minuteChosen45( ActionEvent e){
        minuteButton.setText("45");
    }

    /**
     * converting date to string for database
     * @return string version of date
     */
    private String dateValue(){
        String date = String.valueOf(appointmentDate.getValue());
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);
        String result = year + "-" + month + "-" + day;
        System.out.println(result);

        return result;
    }

    /**
     * converting time to string for database
     * @return string version of time
     */
    private String timeValue(){
        String hour = hourButton.getText();
        String minute = minuteButton.getText();

        return hour + ":" + minute;
    }

    /**
     * saves the appointment
     * @param e gives action to save button
     * @throws SQLException
     */
    @FXML
    public void saveAppointment(ActionEvent e) throws SQLException
    {
        // finding patient id
        int patientId = Database.findPatientKey( patientdbId );

        // controlling patient id
        System.out.println(patientId);

        String fullString = choiceBox.getValue();
        int index = fullString.indexOf('-');
        String docName = fullString.substring(0, index - 1);
        System.out.println(docName);
        int docId = Database.findDoctorKey( docName );

        // looking doctor's availability
        if ( Database.doctorAvailability( docId ))
        {
            // if appointment is added successfully show a alert information
            boolean trial = Database.addAppointment(patientId, docId, dateValue(), timeValue() );
            if (trial)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Appointment added successfully.");
                alert.show();
                Stage stage = (Stage) saveAppointmentButton.getScene().getWindow(); stage.close();
            }

            // if appointment is not added successfully show an alert information
            else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("A given info is wrong.");
                    alert.show();
                }
        }
        // if doctor is not available at that time
        else
            { Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Doctor is not available at given time. Please choose a different time.");
            alert.show();
            }
    }

    /**
     * updating the page for that specifying patient name
     * @throws SQLException
     */
    public void update( ) throws SQLException {
        File file = null;
        Scanner scan = null;
        try {
            file = new File("outFile.txt");
            String path = file.getAbsolutePath();
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Could not access file. Please try again.");
            alert.show();
        }
        String id = scan.next();
        scan.close();

        // finding patient key and their detail for that patient
        int patientKey = database.Database.findPatientKey(id);
        ArrayList<String> infoList = database.Database.patientDetails(patientKey);

        // getting the name from database and sets the labels
        String fullName = infoList.get(0);
        int index = fullName.lastIndexOf(' ');
        String firstName = fullName.substring(0, index);
        String lastName = fullName.substring( index + 1);

        patientName.setText(firstName);
        patientSurname.setText(lastName);
        patientdbId = infoList.get(1);
    }

    /**
     * initializing the add appointment page
     * @param location the location of add appointment page
     * @param resources the needed sources for that page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            update();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // setting the choice box for doctor's list from the database information
        ObservableList<String> observerList = FXCollections.observableArrayList();
        try {
            ResultSet rs2 = myConn.createStatement().executeQuery("SELECT * FROM doctor");

            while (rs2.next()) {
                observerList.add(rs2.getString("name") + " - " + rs2.getString("department"));
            }
        }catch (SQLException ex){}
        choiceBox.getItems().addAll(observerList);
    }
}