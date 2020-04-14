package ui;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class MasterController extends Database {

    /**
     * Loads the window by given location and title
     * @param location of the given file
     * @param title of the stage
     */
    public void loadWindow(String location, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(location));
            Stage newStage = new Stage(StageStyle.DECORATED);
            newStage.setTitle(title);
            Image icon = new Image("ui/icons/hospital.png");
            newStage.getIcons().add(icon);
            newStage.setScene(new Scene(parent));
            newStage.setResizable(false);
            newStage.show();
        }catch (IOException exception){}
    }

    /**
     * Takes a string item to print write
     * @param printItem string that want to carry by text file
     */
    public void idCarry(String printItem){
        PrintWriter outFile = null;
        File file = new File("outFile.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outFile = new PrintWriter(file);
        } catch (FileNotFoundException fileE) {}

        //write the patient id in a different txt file
        outFile.println(printItem);
        outFile.close();
    }

    /**
     * When pressed logout button loads auth screen
     * @param e action when pressed button
     * @throws IOException
     */
    public void authLoader(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ui/authentication/authentication.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setResizable(false);
        app_stage.show();
    }
}
