/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.DBProperti;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Function.Function;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * FXML Controller class
 *
 * @author Rexnet
 */
public class home implements Initializable {

    Function function = new Function();
    private double initX;
    private double initY;
    private PreparedStatement pst;
    private Connection con;
    private ResultSet rs;

    DBProperti DBProperti = new DBProperti();
    String db = DBProperti.loadFileProperti();

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton la;

    @FXML
    private JFXButton ma;

    @FXML
    private JFXButton m;

    @FXML
    private JFXButton c;

    @FXML
    private Label tittleapp;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void drag(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setX(event.getScreenX() - initX);
        stage.setY(event.getScreenY() - initY);
    }

    @FXML
    private void lo() throws IOException {
        Parent rooot = FXMLLoader.load(getClass().getResource(Function.LOGIN));
        Scene scene = new Scene(rooot);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.initStyle(TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Monitoring App");
        stage.show();
        Stage s = (Stage) root.getScene().getWindow();
        s.close();

    }

    @FXML
    private void mo() throws IOException {
        Parent rooot4 = FXMLLoader.load(getClass().getResource(Function.PACKETSSNIFFING));
        Scene scene = new Scene(rooot4);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.initStyle(TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Monitoring App");
        stage.show();
        Stage s = (Stage) root.getScene().getWindow();
        s.close();
    }

    @FXML
    private void pres(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        initX = event.getScreenX() - stage.getX();
        initY = event.getScreenY() - stage.getY();
    }

    @FXML
    private void sembunyi(MouseEvent event) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void tutup(MouseEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("KELUAR");
            alert.setHeaderText(null);
            alert.setContentText("Yakin Ingin Keluar ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            } else {

            }
        } catch (Exception e) {

        }
    }
}
