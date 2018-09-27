/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javafx.stage.StageStyle.TRANSPARENT;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class AController implements Initializable {

    private double initX;
    private double initY;

    @FXML
    private AnchorPane root;
    @FXML
    private StackPane st;
    @FXML
    private JFXButton m;
    @FXML
    private JFXButton c;
    @FXML
    private JFXTextField u;
    @FXML
    private JFXPasswordField p;
    @FXML
    private JFXButton b;
    @FXML
    private Hyperlink l;

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

    @FXML
    private void tanya(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contact Person");
        alert.setContentText("Email : opmarmy@protonmail.com");
        alert.show();

    }


    @FXML
    private void l(ActionEvent event) {

        try {
            if (u.getText().equals("admin") && p.getText().equals("123")) {
                Parent roort = FXMLLoader.load(getClass().getResource("/B/B.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(roort);
                scene.setFill(Color.TRANSPARENT);
                stage.initStyle(TRANSPARENT);
                stage.setScene(scene);
                stage.show();
                Stage s = (Stage) root.getScene().getWindow();
                s.close();
            } else {
                TrayNotification tn = new TrayNotification("PERINGATAN", "Periksa Kembali Username & Password Anda !", NotificationType.NOTICE);
                tn.setAnimationType(AnimationType.SLIDE);
                tn.showAndDismiss(Duration.seconds(1));
                Toolkit.getDefaultToolkit().beep();
            }
        } catch (IOException ex) {

        }

    }

}
