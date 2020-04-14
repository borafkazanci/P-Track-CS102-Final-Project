/*
the class which controls and shows the patient details page
 */
package ui.receptionist.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;


public class PatientDetailsController implements Initializable {

    @FXML
    private Label detailedNameLabel,detailedIDLabel,detailedGenderLabel,detailedBloodTypeLabel,detailedBirthDateLabel,detailedAddressLabel,detailedPhoneNumber,detailedInsurance,detailedEmergencyContactName,detailedEmergencyContactPhoneNumber;

    @FXML
    private Button backButton;

    /**
     * default constructor
     */
    public PatientDetailsController(){}

    /**
     * setting the patient details information by database
     * @throws SQLException
     */
    public void update() throws SQLException{
        File file = null;
        Scanner scan = null;
        try {
            file = new File("outFile.txt");
            String path = file.getAbsolutePath();
            scan = new Scanner(file);
        }catch( FileNotFoundException e){
            // if database is not reached properly
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Could not access database. Please try again.");
            alert.show();
        }
        String id = scan.next();
        scan.close();
        file.delete();

        // finding patient key
        int patientKey = database.Database.findPatientKey(id);

        // finding patient details by database and setting them accordingly
        ArrayList<String> infoList = database.Database.patientDetails(patientKey);
        System.out.println(infoList.toString());
        detailedNameLabel.setText(infoList.get(0));
        detailedIDLabel.setText(infoList.get(1));
        detailedGenderLabel.setText(infoList.get(2));
        detailedBirthDateLabel.setText(infoList.get(4));
        detailedBloodTypeLabel.setText(infoList.get(3));
        detailedAddressLabel.setText(infoList.get(5));
        detailedPhoneNumber.setText(infoList.get(7));
        detailedInsurance.setText(infoList.get(6));
        detailedEmergencyContactName.setText(infoList.get(8));
        detailedEmergencyContactPhoneNumber.setText(infoList.get(9));
    }

    /**
     * initializes the page
     * @param location the location of the page
     * @param resources needed sources for the page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * closing the page
     * @param c gives action to back button
     */
    @FXML private void pageClose(ActionEvent c)
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

}