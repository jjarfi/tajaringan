/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Function.Function;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * FXML Controller class
 *
 * @author Rexnet
 */
public class B implements Initializable {

    private final String icon = "/Icon/icon.png";
    Function function = new Function();
    private double initX;
    private double initY;
    AnchorPane D, E, F, G, H, L, R;
    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane J;

    @FXML
    private FontAwesomeIconView ic1;

    @FXML
    private FontAwesomeIconView ic2;

    @FXML
    private FontAwesomeIconView ic3;

    @FXML
    private FontAwesomeIconView ic4;

    @FXML
    private FontAwesomeIconView ic5;

    @FXML
    private FontAwesomeIconView ic6;

    @FXML
    private FontAwesomeIconView ic7;

    @FXML
    private FontAwesomeIconView ic71;

    @FXML
    private FontAwesomeIconView ic8;

    @FXML
    private JFXButton m;

    @FXML
    private JFXButton c;

    @FXML
    private Label tittleapp;

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
            D = FXMLLoader.load(getClass().getResource(Function.PERANGKAT));
            E = FXMLLoader.load(getClass().getResource(Function.DASHBOARD));
            F = FXMLLoader.load(getClass().getResource(Function.STATUS));
            G = FXMLLoader.load(getClass().getResource(Function.DETAIL));
            H = FXMLLoader.load(getClass().getResource(Function.JARINGAN));
            L = FXMLLoader.load(getClass().getResource(Function.DETAILPERANGKAT));
            R = FXMLLoader.load(getClass().getResource(Function.REPORT));

        } catch (IOException e) {
            System.err.println("Error di " + e);
        }
        setNode(E);
        tittleapp.setText("" + Function.NAMAAPP + Function.NAMADASHBOARD);
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
        alert.setHeaderText(null);
        alert.setContentText("Yakin Ingin Keluar ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
            //System.exit(0);
        } else {

        }

    }

    @FXML
    private void tutup2(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setContentText("Yakin Ingin Log Out ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent roort = FXMLLoader.load(getClass().getResource(Function.HOME));
            Stage stage = new Stage();
            Scene scene = new Scene(roort);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(TRANSPARENT);
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResource(icon).toExternalForm()));
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
    private void mosenter71(MouseEvent event) {
        ic71.setFill(Color.web("#00e9ff"));
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
    private void mosexit71(MouseEvent event) {
        ic71.setFill(Color.WHITE);
    }

    @FXML
    private void mosexit8(MouseEvent event) {
        ic8.setFill(Color.WHITE);
    }

    @FXML
    private void moveD(ActionEvent event) {
        try {
            setNode(E);
            tittleapp.setText("" + Function.NAMAAPP + Function.NAMADASHBOARD);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @FXML
    private void moveE(ActionEvent event) {
        try {
            setNode(D);
            tittleapp.setText("" + Function.NAMAAPP + Function.NAMADATAPERANGKAT);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveK(ActionEvent event) {
        try {
            setNode(F);
            tittleapp.setText("" + Function.NAMAAPP + Function.NAMADATASTATUSPERANGKAT);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveL(ActionEvent event) {
        try {
            setNode(G);
            tittleapp.setText("" + Function.NAMAAPP + Function.NAMADATAJARINGANKOMPUTER);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveN(ActionEvent event) {
        try {
            setNode(H);
            tittleapp.setText("" + Function.NAMAAPP + Function.NAMAMONITORINGJARINGAN);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveO(ActionEvent event) {
        try {
            setNode(L);
            tittleapp.setText("" + Function.NAMAAPP + Function.NAMADETAILPERANGKAT);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    public void more(ActionEvent event) {
        try {
            setNode(R);
            tittleapp.setText("" + Function.NAMAAPP + Function.LAPORAN);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    public void moni(ActionEvent event) {

        try {
            Parent roort = FXMLLoader.load(getClass().getResource(Function.MONITORING));
            Stage stage = new Stage();
            Scene scene = new Scene(roort);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(TRANSPARENT);
            stage.setTitle("" + Function.PAKETSNIFFING);
            stage.getIcons().add(new Image(getClass().getResource(icon).toExternalForm()));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {

        }
    }

}
