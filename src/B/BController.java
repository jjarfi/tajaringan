/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class BController implements Initializable {

    private double initX;
    private double initY;
    AnchorPane D, E, F, G, H, L;

    @FXML
    private AnchorPane root, J, JE;
    @FXML
    private JFXButton m;
    @FXML
    private FontAwesomeIconView ic1, ic2, ic3, ic4, ic5, ic6, ic7, ic8;
    @FXML
    private JFXButton c;
    @FXML
    private Label title;

    private void setNode(Node node) {
        J.getChildren().clear();
        J.getChildren().add(node);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            D = FXMLLoader.load(getClass().getResource("/J/J.fxml"));
            E = FXMLLoader.load(getClass().getResource("/E/E.fxml"));
            F = FXMLLoader.load(getClass().getResource("/K/K.fxml"));
            G = FXMLLoader.load(getClass().getResource("/L/L.fxml"));
            H = FXMLLoader.load(getClass().getResource("/N/N.fxml"));
            L = FXMLLoader.load(getClass().getResource("/O/O.fxml"));

        } catch (IOException e) {

        }
        setNode(E);
        title.setText("DASHBOARD");
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("KELUAR");
        alert.setContentText("Yakin Ingin Keluar ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        } else {

        }

    }

    @FXML
    private void tutup1(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setContentText("Yakin Ingin Log Out ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent roort = FXMLLoader.load(getClass().getResource("/A/A.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(roort);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(TRANSPARENT);
            stage.setScene(scene);
            stage.show();
            Stage s = (Stage) root.getScene().getWindow();
            s.close();

        } else {

        }

    }

    @FXML
    private void mosenter1(MouseEvent event) {
        ic1.setFill(Color.web("#00e9ff"));
    }

    @FXML
    private void mosenter2(MouseEvent event) {
        ic2.setFill(Color.web("#00e9ff"));
    }

    @FXML
    private void mosenter3(MouseEvent event) {
        ic3.setFill(Color.web("#00e9ff"));
    }

    @FXML
    private void mosenter4(MouseEvent event) {
        ic4.setFill(Color.web("#00e9ff"));
    }

    @FXML
    private void mosenter5(MouseEvent event) {
        ic5.setFill(Color.web("#00e9ff"));
    }

    @FXML
    private void mosenter6(MouseEvent event) {
        ic6.setFill(Color.web("#00e9ff"));
    }

    @FXML
    private void mosenter7(MouseEvent event) {
        ic7.setFill(Color.web("#00e9ff"));
    }

    @FXML
    private void mosenter8(MouseEvent event) {
        ic8.setFill(Color.RED);
    }

    @FXML
    private void mosexit1(MouseEvent event) {
        ic1.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit2(MouseEvent event) {
        ic2.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit3(MouseEvent event) {
        ic3.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit4(MouseEvent event) {
        ic4.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit5(MouseEvent event) {
        ic5.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit6(MouseEvent event) {
        ic6.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit7(MouseEvent event) {
        ic7.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit8(MouseEvent event) {
        ic8.setFill(Color.WHITE);
    }

    @FXML
    private void moveD(ActionEvent event) {
        try {
            setNode(E);
            title.setText("DASHBOARD");
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @FXML
    private void moveE(ActionEvent event) {
        try {
            setNode(D);
            title.setText("DATA PERANGKAT");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveK(ActionEvent event) {
        try {
            setNode(F);
            title.setText("DATA STATUS PERANGKAT");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveL(ActionEvent event) {
        try {
            setNode(G);
            title.setText("DATA JARINGAN KOMPUTER");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveN(ActionEvent event) {
        try {
            setNode(H);
            title.setText("MONITORING JARINGAN");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveO(ActionEvent event) {
        try {
            setNode(L);
            title.setText("DATA DETAIL PERANGKAT");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
