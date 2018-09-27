/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package K;

import Model.MK;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class KController implements Initializable {

    private ObservableList<MK> data;

    private ResultSet rs;
    private Connection conn;
    String query = "";
    private PreparedStatement pst;

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane JEE;
    @FXML
    private TextField ns, ks,cari;

    @FXML
    private TextArea ket;
    @FXML
    private TableView<MK> tabstatus;

    @FXML
    private TableColumn<MK, String> kolks;

    @FXML
    private TableColumn<MK, String> kolnmstatus;

    @FXML
    private TableColumn<MK, String> kolket;

    public void konek() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbmonitoring", "root", "666");
            pst = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            pst.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Exception" + e.getMessage());
        }
    }

    @FXML
    public void reTable(ActionEvent event) throws SQLException {
        refresh();
        hapus();
    }

    @FXML
    public void simpan(ActionEvent event) {
        simpan();
    }

    private void simpan() {

        try {
            Statement stt = conn.createStatement();
            String sql = "select * from tbl_lokasi where kd_lokasi = '" + ks.getText() + "'";
            rs = stt.executeQuery(sql);
            if (rs.next() == true) {
                TrayNotification tn = new TrayNotification("WARNING", "Kode Status Sudah Terdaftar", NotificationType.NOTICE);
                tn.setAnimationType(AnimationType.SLIDE);
                tn.showAndDismiss(Duration.seconds(1));
                Toolkit.getDefaultToolkit().beep();
                //kp.setFocusTraversable(true);
                //  refresh();
            } else {
                konek();
                String insert = "insert into tbl_lokasi(kd_lokasi,nm_lokasi,ket) values (?,?,?)";
                try {
                    pst = conn.prepareStatement(insert);
                    pst.setString(1, ks.getText().toUpperCase());
                    pst.setString(2, ns.getText().toUpperCase());
                    pst.setString(3, ket.getText().toUpperCase());
                    int berhasil = pst.executeUpdate();
                    if (berhasil == 1) {
                        hapus();
                        refresh();
                        TrayNotification tn = new TrayNotification("SUCCESS", "Data Berhasil Berhasil", NotificationType.SUCCESS);
                        tn.setAnimationType(AnimationType.FADE);
                        tn.showAndDismiss(Duration.seconds(2));
                    } else {
                        TrayNotification tn = new TrayNotification("ERROR", "Data Berhasil GAGAL", NotificationType.ERROR);
                        tn.setAnimationType(AnimationType.FADE);
                        tn.showAndDismiss(Duration.seconds(2));
                    }

                } catch (SQLException ex) {
                    System.err.println("Error inserting" + ex);
                    TrayNotification tn = new TrayNotification("WARNING", "Periksa Kembali Inputan Anda", NotificationType.WARNING);
                    tn.setAnimationType(AnimationType.FADE);
                    tn.showAndDismiss(Duration.seconds(2));
                }
            }

        } catch (SQLException ex) {
            System.err.println("Error inserting" + ex);
            TrayNotification tn = new TrayNotification("WARNING", "Periksa Kembali Inputan Anda", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.FADE);
            tn.showAndDismiss(Duration.seconds(2));
        }
    }

    private void hapus() {
        ks.setText("");
        ns.setText("");
        ket.setText("");
    }
       @FXML
    public void update(ActionEvent event) {
        update();
    }

    private void update() {

        konek();
        String update = "update tbl_lokasi set nm_lokasi=?, ket=? where kd_lokasi=?";
        try {
            pst = conn.prepareStatement(update);

            pst.setString(1, ns.getText().toUpperCase());
            pst.setString(2, ket.getText().toUpperCase());
            pst.setString(3, ks.getText().toUpperCase());

            int berhasil = pst.executeUpdate();
            if (berhasil == 1) {
                hapus();
                refresh();
                TrayNotification tn = new TrayNotification("SUCCESS", "Data Berhasil Terupdate", NotificationType.SUCCESS);
                tn.setAnimationType(AnimationType.SLIDE);
                tn.showAndDismiss(Duration.seconds(2));
            } else {
                TrayNotification tn = new TrayNotification("ERROR", "Update Data Gagal", NotificationType.ERROR);
                tn.setAnimationType(AnimationType.SLIDE);
                tn.showAndDismiss(Duration.seconds(2));
            }

        } catch (SQLException ex) {
            System.err.println("Error inserting" + ex);
            TrayNotification tn = new TrayNotification("WARNING", "Periksa Kembali Inputan Anda", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.SLIDE);
            tn.showAndDismiss(Duration.seconds(2));

        }
    }

    @FXML
    public void delete(ActionEvent event) {
        konek();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("HAPUS");
        alert.setHeaderText("Kode Status : " + ks.getText() + "\nNama Status : " + ns.getText());
        alert.setContentText("Apakah anda yakin ingin menghapus data ini ?");
        String sql = "delete from tbl_lokasi where kd_lokasi = ? ";
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, ks.getText());
                int i = pst.executeUpdate();
                if (i == 1) {
                    hapus();
                    refresh();
//                        btnsimpan.setDisable(false);
//                        btnhapus.setDisable(true);
//                        btnupdate.setDisable(true);
                    TrayNotification tn = new TrayNotification("SUKSES", "Data Berhasil Terhapus", NotificationType.SUCCESS);
                    tn.setAnimationType(AnimationType.POPUP);
                    tn.showAndDismiss(Duration.seconds(2));
                } else {
                    TrayNotification tn = new TrayNotification("GAGAL", "Tidak ada data yang terhapus", NotificationType.WARNING);
                    tn.setAnimationType(AnimationType.POPUP);
                    tn.showAndDismiss(Duration.seconds(2));

                }
            } catch (SQLException ex) {

            }
        } else {

        }
    }
       @FXML
    public void setdaritabel(MouseEvent event) throws SQLException {
        try {
            MK pr = tabstatus.getItems().get(tabstatus.getSelectionModel().getSelectedIndex());
            ks.setText(pr.getMks());
            ns.setText(pr.getMns());
            ket.setText(pr.getMket());

//            btnsimpan.setDisable(true);
//            btnhapus.setDisable(false);
//            btnupdate.setDisable(false);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
      @FXML
    public void cariper(KeyEvent event) throws SQLException {

        if (cari.getText().equals("")) {
            refresh();
        } else {

            data.clear();
            String sql = "select * from tbl_lokasi where kd_lokasi LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_lokasi where nm_lokasi LIKE '%" + cari.getText() + "%'";
                    
            try {
                pst = (PreparedStatement) conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println("" + rs.getString(2));
                    data.add(new MK(rs.getString(1), rs.getString(2), rs.getString(3)));

                }
                tabstatus.setItems(data);
            } catch (SQLException ex) {
            }
        }

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

//        try {
//            konek();
//            refresh();
//        } catch (SQLException ex) {
//
//        }
    }

    private void tabelsta() throws SQLException {
        konek();
        try {
            data = FXCollections.observableArrayList();

            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                String mks = set.getString(1);
                String mns = set.getString(2);
                String mket = set.getString(3);
                data.add(new MK(mks, mns, mket));

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        kolks.setCellValueFactory(new PropertyValueFactory<>("mks"));
        kolnmstatus.setCellValueFactory(new PropertyValueFactory<>("mns"));
        kolket.setCellValueFactory(new PropertyValueFactory<>("mket"));

        tabstatus.setItems(null);
        tabstatus.setItems(data);
    }

    private void refresh() throws SQLException {
        konek();
        tabstatus.getItems().clear();
        query = "select * from tbl_lokasi";
        tabelsta();
    }

}
