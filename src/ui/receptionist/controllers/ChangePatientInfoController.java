/*
the class controls the change patient info in receptionist home page
 */
package ui.receptionist.controllers;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ChangePatientInfoController implements Initializable {

    @FXML
    private Label patientNameLabel,patientId,patientGender,patientBirthdate,bloodType;

    @FXML
    private TextField changeAddress,changeCity,changePatientPhone,changeEmergencyName,changeEmergencyPhone;

    @FXML
    private MenuButton changeInsurance;

    @FXML
    private Button saveButton;

    /**
     * default constructor
     */
    public ChangePatientInfoController(){}

    /**
     * sets textfield to SGK
     * @param e gives action to textField
     */
    @FXML
    private void sgkChoice(ActionEvent e) {
        changeInsurance.setText("SGK");
    }

    /**
     * sets textfield to BAGKUR
     * @param e gives action to textField
     */
    @FXML
    private void bagkurChoice(ActionEvent e) {
        changeInsurance.setText("BAGKUR");
    }
    /**
     * sets textfield to PRIVATE
     * @param e gives action to textField
     */
    @FXML
    private void privateChoice(ActionEvent e) {
        changeInsurance.setText("PRIVATE");
    }

    /**
     * saves changed patient info
     * @param e gives action to save button
     * @throws SQLException
     */
    @FXML
    public void savePatientInfo(ActionEvent e) throws SQLException {
        // showing an alert message
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        // if yes button is pressed save changed patient details
        if (alert.getResult() == ButtonType.YES) {
            File file = null;
            Scanner scan = null;
            try {
                file = new File("outFile.txt");
                String path = file.getAbsolutePath();
                scan = new Scanner(file);
            } catch (FileNotFoundException f) {
                Alert fileAlert = new Alert(Alert.AlertType.INFORMATION);
                fileAlert.setContentText("Could not access file. Please try again.");
                fileAlert.show();
            }
            String id = scan.next();
            scan.close();

            // finding patient key
            int patientKey = database.Database.findPatientKey(id);

            // updating the patient information in database
            Database.updatePatient( patientKey, changeAddress.getText(), changePatientPhone.getText(), changeInsurance.getText(), changeEmergencyName.getText(), changeEmergencyPhone.getText());
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * setting labes according to that specific patient
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

        // finding patient key
        int patientKey = database.Database.findPatientKey(id);

        // getting the details from database and sets the labels accordingly
        ArrayList<String> infoList = database.Database.patientDetails(patientKey);
        System.out.println(infoList.toString());
        patientNameLabel.setText(infoList.get(0));
        patientId.setText(infoList.get(1));
        patientGender.setText(infoList.get(2));
        patientBirthdate.setText(infoList.get(4));
        bloodType.setText(infoList.get(3));
        changeAddress.setText(infoList.get(5));
        changePatientPhone.setText(infoList.get(7));
        changeInsurance.setText(infoList.get(6));
        changeEmergencyName.setText(infoList.get(8));
        changeEmergencyPhone.setText(infoList.get(9));
    }

    /**
     * initializes the  change patient information page
     * @param location the location of the page
     * @param resources needed soureces of the page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            update();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
