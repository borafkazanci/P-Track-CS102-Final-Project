/*
 * this class controls and shows add patient scene for receptionist
 */
package ui.receptionist.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;

public class AddPatientController {
    @FXML
    private TextField addName,addID,addSurname,addAddress,addCity,addPatientPhonenumber,addEmergencyName,addEmergencySurname,addEmergencyNo;

    @FXML
    private DatePicker addBirthDate;

    @FXML
    private Button savingButton;

    @FXML
    private MenuButton sexMenu,bloodTypeMenu,insuranceMenu;

    //error labels
    @FXML
    private Label errorLabelAddPatient;

    //variables

    /**
     * a constructor creates add Patient controller class
     */
    public AddPatientController(){}

    /**
     * if male is chosen from the sexMenu
     * @param e gives action the menu button
     */
    @FXML
    private void sexMaleChoice(ActionEvent e){
        sexMenu.setText("Male");
    }

    /**
     * if female is chosen from the sexMenu
     * @param e gives action the menu button
     */
    @FXML
    private void sexFemaleChoice(ActionEvent e){
        sexMenu.setText("Female");
    }

    /**
     * if ARH+ is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void aRHpositiveChoice(ActionEvent e){
        bloodTypeMenu.setText("A RH+");
    }

    /**
     * if ARH- is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void aRHnegativeChoice(ActionEvent e){
        bloodTypeMenu.setText("A RH-");
    }

    /**
     * if BRH+ is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void bRHpositiveChoice(ActionEvent e){
        bloodTypeMenu.setText("B RH+");
    }

    /**
     * if BRH- is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void bRHnegativeChoice(ActionEvent e){
        bloodTypeMenu.setText("B RH-");
    }

    /**
     * if ABRH+ is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void abRHpositiveChoice(ActionEvent e){
        bloodTypeMenu.setText("AB RH+");
    }

    /**
     * if ABRH- is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void abRHnegativeChoice(ActionEvent e){
        bloodTypeMenu.setText("AB RH-");
    }

    /**
     * if 0RH+ is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void zeroRHpositiveChoice(ActionEvent e){
        bloodTypeMenu.setText("0 RH+");
    }

    /**
     * if 0RH- is chosen from bloodTypeMenu
     * @param e gives action to menu button
     */
    @FXML
    private void zeroRHnegativeChoice(ActionEvent e){
        bloodTypeMenu.setText("0 RH-");
    }

    /**
     * if sgk is chosen from insurance menu
     * @param e gives action to menu button
     */
    @FXML
    private void sgkChoice(ActionEvent e) {
        insuranceMenu.setText("SGK");
    }

    /**
     * if bagkur is chosen from insurance menu
     * @param e gives action to menu button
     */
    @FXML
    private void bagkurChoice(ActionEvent e) {
        insuranceMenu.setText("BAGKUR");
    }

    /**
     * if private is chosen from insurance menu
     * @param e gives action to menu button
     */
    @FXML
    private void privateChoice(ActionEvent e) {
        insuranceMenu.setText("PRIVATE");
    }

    /**
     * converting date value to string for database
     * @return the string version of date
     */
    private String dateValue(){
        String date = String.valueOf(addBirthDate.getValue());
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        return day+"/"+ month+"/"+ year;
    }

    /**
     * saving patient in database
     * @param e gives action to save button for the patient
     * @throws SQLException
     */
    @FXML
    public void savePatient(ActionEvent e) throws SQLException {
        // show alert message when user tries to save a patient
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        // if user presses button yes save the patient to database
        if (alert.getResult() == ButtonType.YES) {
            database.Database.addPatient(addName.getText() + " " + addSurname.getText(),Long.parseLong(addID.getText()),sexMenu.getText(),bloodTypeMenu.getText(),""+ dateValue(),
                    addAddress.getText() + " " + addCity.getText(),addPatientPhonenumber.getText(),
                    insuranceMenu.getText(),addEmergencyName.getText() + " " + addEmergencySurname.getText(), addEmergencyNo.getText());
            Stage stage = (Stage) savingButton.getScene().getWindow();
            stage.close();
        }
    }
}