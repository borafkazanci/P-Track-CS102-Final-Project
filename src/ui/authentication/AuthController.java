package ui.authentication;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

public class AuthController {
    @FXML
    private Label errorLabel, errorLabel1;

    @FXML
    private Button loginButton;

    @FXML
    private MenuButton roleMenu;

    @FXML
    private MenuItem patientSec, doctorSec, receptionistSec;

    @FXML
    private TextField userName, citizenshipIDField;

    @FXML
    private PasswordField password;

    //variables
    private int roleChooser = 0;

    //constructor
    public AuthController(){}

    //methods
    @FXML
    /**
     * Sets roleChooser 1 to get an action
     * @param e changes the menu item text when it's selected
     */
    private void doctorChoice(ActionEvent e){
        roleMenu.setText("Doctor");
        roleChooser = 1;
    }

    @FXML
    /**
     * Sets roleChooser 2 to get an action
     * @param e changes the menu item text when it's selected
     */
    private void receptionistChoice(ActionEvent e){
        roleMenu.setText("Receptionist");
        roleChooser = 2;
    }

    @FXML
    /**
     * Controls the patient's login button
     * @param event gives action to a button
     */
    private void enterPatientButton(ActionEvent event) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        if (roleChooser == 0){
            System.out.println(citizenshipIDField.getText());
            String loginPatient = Database.patientAuth( citizenshipIDField.getText() );
            if (loginPatient.equals(citizenshipIDField.getText())) {
                loader.setLocation(getClass().getClassLoader().getResource("ui/patient/patientScene.fxml"));
                try {
                    Parent parent = loader.load();
                    Scene scene = new Scene(parent);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.setScene(scene);
                    app_stage.setResizable(false);
                    app_stage.show();
                }catch (RuntimeException r){
                    System.out.println("Role is not selected!");
                }

            }
            else{
                citizenshipIDField.clear();
                errorLabel1.setText(loginPatient);
                errorLabel1.setTextFill(Color.RED);
            }
        }
        else{
            errorLabel1.setText("Please enter a citizen id number");
            errorLabel1.setTextFill(Color.RED);
        }
    }

    @FXML
    /**
     * Controls the hospital staff's login button
     * @param event gives action to a button
     */
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();

        //if role is doctor enters doctor
        if (roleChooser == 1){
            System.out.println("Entered Doctor");
            String loginDoctor = database.Database.doctorAuth(userName.getText(), password.getText());
            if (loginDoctor.equals(userName.getText()))
                loader.setLocation(getClass().getClassLoader().getResource("ui/doctor/doctorHomePage.fxml"));
            else{
                userName.clear();
                password.clear();
                errorLabel.setText(loginDoctor);
                errorLabel.setTextFill(Color.RED);
            }
        }
        //if role is receptionist enters receptionist
        else if (roleChooser == 2){
            System.out.println("Entered Receptionist");
            String loginReceptionist = database.Database.registrationAuth(userName.getText(), password.getText());
            if (loginReceptionist.equals(userName.getText()))
                loader.setLocation(getClass().getClassLoader().getResource("ui/receptionist/receptionistHomePage.fxml"));
            else{
                userName.clear();
                password.clear();
                errorLabel.setText(loginReceptionist);
                errorLabel.setTextFill(Color.RED);
            }
        }
        //role select error
        else{
            errorLabel.setText("Please select a role to log in!");
            errorLabel.setTextFill(Color.RED);
        }
        //page loader
        try {
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.setResizable(false);
            app_stage.show();
        }catch (RuntimeException r){
            System.out.println("Role is not selected!");
        }
    }
}