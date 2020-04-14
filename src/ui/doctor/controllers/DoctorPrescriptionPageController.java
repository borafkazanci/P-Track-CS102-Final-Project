/*
 * class which controls the prescription page for doctors
 */
package ui.doctor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DoctorPrescriptionPageController implements Initializable {

    @FXML
    private Label doctorName,patientName;

    @FXML
    private TextArea addPrescription;

    @FXML
    private Button printPrescription, backToDoctorPage;

    /**
     * default constructor
     */
    public DoctorPrescriptionPageController(){}

    /**
     * goes back to the doctor page from prescription page
     * @param e gives action the back button
     */
    @FXML
    public void handleBackToDoctorPage(ActionEvent e){
        Stage stage = (Stage) backToDoctorPage.getScene().getWindow();
        stage.close();
    }

    /**
     * setting the patient name
     * @throws SQLException
     */
    public String update() throws SQLException {
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
        String name = scan.nextLine();
        scan.close();
        file.delete();

        return name;
    }

    /**
     * initializes the page and setting labels information according to user
     * @param location the location of that page
     * @param resources sources for that page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            patientName.setText(update());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
