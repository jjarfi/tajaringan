/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Function.Function;
import Model.MNN;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.hyperic.sigar.Sigar;

/**
 * FXML Controller class
 *
 * @author Rexnet
 */
public class N implements Initializable {

    private static final Sigar sig = new Sigar();
    private final String interfacciascelta = null;
    private static String ip;
    private ResultSet rs;
    Monitor mon = new Monitor();
    private Function function;
    private Connection conn;
    String query = "";
    private PreparedStatement pst;
    private ObservableList<MNN> data;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane JEE;

    @FXML
    private JFXTextArea pingout, txttracert;
    @FXML
    private AnchorPane J;
    @FXML
    private TextField txtip;
    @FXML
    private TableView<MNN> tabdata;

    @FXML
    private TableColumn<String, MNN> kolip;
    @FXML
    private TableColumn<String, MNN> kolmac;
    @FXML
    private TableColumn<String, MNN> kolkdp;
    @FXML
    private TableColumn<String, MNN> kolnmp;

    @FXML
    private TableColumn<String, MNN> kolkl;

    @FXML
    private TableColumn<String, MNN> kolnml;

    @FXML
    private JFXButton btnpingstop;

    public void konek() {
        conn = function.konekDB();
    }

    @FXML
    public void kliktab(MouseEvent event) {
        setdartabel();
    }

    @FXML
    public void ping(ActionEvent event) throws InterruptedException {
        ping();
    }

    @FXML
    public void tracert(ActionEvent event) throws InterruptedException {
        tracert();
    }

    @FXML
    public void stopPing(ActionEvent event) throws InterruptedException {
        stopPing();
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
        function = new Function();
        try {
            refresh();
            warna();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void tabelsta() throws SQLException {
        konek();
        try {
            data = FXCollections.observableArrayList();
            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                String mno = set.getString(1);
                String mip = set.getString(1);
                String mmac = set.getString(2);
                String mkdp = set.getString(3);
                String mnmp = set.getString(4);
                String kdl = set.getString(5);
                String knml = set.getString(6);

                data.add(new MNN(mno, mip, mmac, mkdp, mnmp,kdl,knml));

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        kolip.setCellValueFactory(new PropertyValueFactory<>("mip"));
        kolmac.setCellValueFactory(new PropertyValueFactory<>("mmac"));
        kolkdp.setCellValueFactory(new PropertyValueFactory<>("mkdp"));
        kolnmp.setCellValueFactory(new PropertyValueFactory<>("mnmp"));
        kolkl.setCellValueFactory(new PropertyValueFactory<>("kdl"));
        kolnml.setCellValueFactory(new PropertyValueFactory<>("knml"));
        tabdata.setItems(null);
        tabdata.setItems(data);
    }

    private void refresh() throws SQLException {
        konek();
        tabdata.getItems().clear();
        query = "select * from pingip";
        tabelsta();
    }

    private void ping() throws InterruptedException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("PING");
            alert.setHeaderText("IP Address : " + txtip.getText());
            alert.setContentText("Apakah anda yakin melakukan Ping ke Alamat ini ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            }
            mon.executor.execute(mon.ping(txtip.getText(),
                    pingout));
            if (pingout.getText().isEmpty()) {
                btnpingstop.setVisible(true);
            } else {
                btnpingstop.setVisible(false);
            }
            Thread.sleep(100);
        } catch (InterruptedException ex) {

        }

    }

    private void tracert() throws InterruptedException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("TRACERT ROUTE");
        alert.setHeaderText("IP Address : " + txtip.getText());
        alert.setContentText("Apakah anda yakin melakukan tracert route ke Alamat ini ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        }
        try {

            mon.executor.execute(mon.tracert(txtip.getText(),
                    txttracert));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Thread.sleep(10);
    }

    private void stopPing() {
        try {
            pingout.setText("");
            btnpingstop.setVisible(false);
        } catch (Exception ex) {
            System.err.println("Error" + ex);
        }
    }

    private void warna() {
        pingout.setStyle("-fx-text-fill: #00ff00;");
        txttracert.setStyle("-fx-text-fill: #00ff00;");
    }

    private void setdartabel() {
        try {
            MNN pr = tabdata.getItems().get(tabdata.getSelectionModel().getSelectedIndex());
            txtip.setText(pr.getMip());
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
