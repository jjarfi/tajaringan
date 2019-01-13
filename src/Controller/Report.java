/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Function.Function;
import Model.MS;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Rexnet
 */
public class Report implements Initializable {

    private Function function;
    private ResultSet rs;
    private Connection conn;

    private ObservableList<MS> data;
    String query = "";

    private PreparedStatement pst;

    @FXML
    private JFXCheckBox ckLP;

    @FXML
    private JFXCheckBox ckLK;

    @FXML
    private JFXCheckBox ckLLI;

    @FXML
    private JFXCheckBox ckLDP;
    @FXML
    private TableView<MS> tabdata;

    @FXML
    private TableColumn<MS, String> kolid;

    @FXML
    private TableColumn<MS, String> kolkode;

    @FXML
    private TableColumn<MS, String> kolnama;

    @FXML
    private TableColumn<MS, String> kolstatus;

    @FXML
    private TableColumn<MS, String> kolkondisi;

    @FXML
    private TableColumn<MS, String> kollokasi;

    @FXML
    private TableColumn<MS, String> kolnmlokasi;

    @FXML
    private TableColumn<MS, String> kolipaddr;

    @FXML
    private TableColumn<MS, String> kolmac;

    @FXML
    private TableColumn<MS, String> kolgateway;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            function = new Function();
            refresh();
        } catch (SQLException ex) {
           
            
        }
    }

    public void konek() {
        conn = function.konekDB();
    }

    private void ctkIP() {
        try {
            konek();
            String report = "src//Report//Ip.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex);

        }

    }

    private void ctkLokasi() {
        try {
            konek();
            String report = "src//Report//Lokasi.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex);

        }

    }

    private void ctkPerangkat() {
        try {
            konek();
            String report = "src//Report//Perangkat.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex);

        }

    }
      private void ctkDetail() {
        try {
            konek();
            String report = "src//Report//Detail.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex);

        }

    }

    @FXML
    private void PrintBTN(ActionEvent event) {
        if (ckLP.isSelected() == true) {
            ctkPerangkat();
        } else if (ckLK.isSelected() == true) {
            ctkLokasi();
        } else if (ckLLI.isSelected() == true) {
            ctkIP();
        }else if (ckLDP.isSelected()== true){
            ctkDetail();
        }

    }

    private void ckPerangkat() {
        if (ckLP.isSelected() == true) {
            ckLK.setDisable(true);
            ckLLI.setDisable(true);
            ckLDP.setDisable(true);
        } else {
            ckLK.setDisable(false);
            ckLLI.setDisable(false);
            ckLDP.setDisable(false);
        }
    }

    private void ckLokasi() {
        if (ckLK.isSelected() == true) {
            ckLP.setDisable(true);
            ckLLI.setDisable(true);
            ckLDP.setDisable(true);
        } else {
            ckLP.setDisable(false);
            ckLLI.setDisable(false);
            ckLDP.setDisable(false);
        }
    }

    private void ckLogic() {
        if (ckLLI.isSelected() == true) {
            ckLP.setDisable(true);
            ckLK.setDisable(true);
            ckLDP.setDisable(true);
        } else {
            ckLP.setDisable(false);
            ckLK.setDisable(false);
            ckLDP.setDisable(false);
        }
    }

    private void ckDetail() {
        if (ckLDP.isSelected() == true) {
            ckLP.setDisable(true);
            ckLK.setDisable(true);
            ckLLI.setDisable(true);
        } else {
            ckLP.setDisable(false);
            ckLK.setDisable(false);
            ckLLI.setDisable(false);
        }
    }

    @FXML
    private void ckButtonPerangkat(ActionEvent event) {
        ckPerangkat();

    }

    @FXML
    private void ckButtonLokasi(ActionEvent event) {
        ckLokasi();

    }

    @FXML
    private void ckButtonLogic(ActionEvent event) {
        ckLogic();

    }

    @FXML
    private void ckButtonDetail(ActionEvent event) {
        ckDetail();

    }

    private void tabelsta() throws SQLException {
        konek();
        try {
            data = FXCollections.observableArrayList();
            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                String id = set.getString(1);
                String kode = set.getString(2);
                String nama = set.getString(3);
                String status = set.getString(4);
                String kondisi = set.getString(5);
                String kdlokasi = set.getString(6);
                String nmlokasi = set.getString(7);
                String ipaddr = set.getString(8);
                String getway = set.getString(9);
                String mac = set.getString(10);

                data.add(new MS(id, kode, nama, status, kondisi, kdlokasi, nmlokasi, ipaddr, getway, mac));

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        kolid.setCellValueFactory(new PropertyValueFactory<>("id"));
        kolkode.setCellValueFactory(new PropertyValueFactory<>("kode"));
        kolnama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        kolstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        kolkondisi.setCellValueFactory(new PropertyValueFactory<>("kondisi"));
        kollokasi.setCellValueFactory(new PropertyValueFactory<>("kdlokasi"));
        kolnmlokasi.setCellValueFactory(new PropertyValueFactory<>("nmlokasi"));
        kolipaddr.setCellValueFactory(new PropertyValueFactory<>("ipaddr"));
        kolmac.setCellValueFactory(new PropertyValueFactory<>("mac"));
        kolgateway.setCellValueFactory(new PropertyValueFactory<>("getway"));

        tabdata.setItems(null);
        tabdata.setItems(data);
    }

    private void refresh() throws SQLException {
        konek();
        tabdata.getItems().clear();
        query = "SELECT\n"
                + "    `tbl_detail_prg`.`id_dtl_prg`\n"
                + "    , `tbl_detail_prg`.`kd_prg`\n"
                + "    , `tbl_perangkat`.`nm_prg`\n"
                + "    , `tbl_perangkat`.`status`\n"
                + "    , `tbl_perangkat`.`kondisi`\n"
                + "    , `tbl_lokasi`.`kd_lokasi`\n"
                + "    , `tbl_lokasi`.`nm_lokasi`\n"
                + "    , `tbl_logic_ip`.`ip_add`\n"
                + "    , `tbl_logic_ip`.`getway`\n"
                + "    , `tbl_logic_ip`.`mac_add`\n"
                + "FROM\n"
                + "    `dbmonitoring`.`tbl_detail_prg`\n"
                + "    INNER JOIN `dbmonitoring`.`tbl_lokasi` \n"
                + "        ON (`tbl_detail_prg`.`kd_lokasi` = `tbl_lokasi`.`kd_lokasi`)\n"
                + "    INNER JOIN `dbmonitoring`.`tbl_logic_ip` \n"
                + "        ON (`tbl_detail_prg`.`ip_add` = `tbl_logic_ip`.`ip_add`)\n"
                + "    INNER JOIN `dbmonitoring`.`tbl_perangkat` \n"
                + "        ON (`tbl_detail_prg`.`kd_prg` = `tbl_perangkat`.`kd_prg`);";
        tabelsta();
    }
}
