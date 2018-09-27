/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L;

import Model.MJ;
import Model.ML;
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
public class LController implements Initializable {

    private ObservableList<ML> data;

    private ResultSet rs;
    private Connection conn;
    String query = "";
    private PreparedStatement pst;
    @FXML
    private TextField ip, sm, g, ma, cari;

    //@FXML
    //private TextArea ket;

    @FXML
    private TableView<ML> tabjar;

    @FXML
    private TableColumn<ML, String> kolip;

    @FXML
    private TableColumn<ML, String> kolsm;

    @FXML
    private TableColumn<ML, String> kolg;

    @FXML
    private TableColumn<ML, String> kolma;

   // @FXML
   // private TableColumn<ML, String> kolket;

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane JEE;

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
    public void simpan(ActionEvent event) {
        try{
             simpan();
        }catch(Exception e){
            System.out.println(e);
        }
       
    }

    private void simpan() {

        try {
            Statement stt = conn.createStatement();
            String sql = "select * from tbl_logic_ip where ip_add = '" + ip.getText() + "'";
            rs = stt.executeQuery(sql);
            if (rs.next() == true) {
                TrayNotification tn = new TrayNotification("WARNING", "IP Address Sudah Terdaftar", NotificationType.NOTICE);
                tn.setAnimationType(AnimationType.SLIDE);
                tn.showAndDismiss(Duration.seconds(1));
                Toolkit.getDefaultToolkit().beep();
                //  refresh();
            } else {
                konek();
                String insert = "insert into tbl_logic_ip (ip_add,subnet_mask,getway,mac_add) values (?,?,?,?) ";
                try {
                    pst = conn.prepareStatement(insert);
                    pst.setString(1, ip.getText().toUpperCase());
                    pst.setString(2, sm.getText().toUpperCase());
                    pst.setString(3, g.getText().toUpperCase());
                    pst.setString(4, ma.getText().toUpperCase());
         
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

    
    @FXML
    public void cariip(KeyEvent event) throws SQLException {

        if (cari.getText().equals("")) {
            refresh();
        } else {

            data.clear();
            String sql = "select * from tbl_logic_ip where ip_add LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_logic_ip where subnet_mask LIKE '%" + cari.getText() + "%'"
                 //   + "UNION select * from tbl_logic_ip where getway LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_logic_ip where mac_add LIKE '%" + cari.getText() + "%'";
            try {
                pst = (PreparedStatement) conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println("" + rs.getString(2));
                    data.add(new ML(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));

                }
                tabjar.setItems(data);
            } catch (SQLException ex) {
            }
        }

    }

    private void hapus() {
        ip.setText("");
        sm.setText("");
        g.setText("");
        ma.setText("");
 
    }
           @FXML
    public void setdaritabel(MouseEvent event) throws SQLException {
        try {
            ML pr = tabjar.getItems().get(tabjar.getSelectionModel().getSelectedIndex());
            ip.setText(pr.getMip());
            sm.setText(pr.getMsm());
            g.setText(pr.getMg());
            ma.setText(pr.getMma());
           

//            btnsimpan.setDisable(true);
//            btnhapus.setDisable(false);
//            btnupdate.setDisable(false);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
   @FXML
    public void reTable(ActionEvent event) throws SQLException {
        refresh();
        hapus();
    }
         @FXML
    public void update(ActionEvent event) {
        update();
    }

    private void update() {

        konek();
        String update = "update tbl_logic_ip set subnet_mask=?, gateway=?, mac_add=? where ip_add=?";
        try {
            pst = conn.prepareStatement(update);

            pst.setString(1, sm.getText().toUpperCase());
            pst.setString(2, g.getText().toUpperCase());
            pst.setString(3, ma.getText().toUpperCase());
            pst.setString(4, ip.getText().toUpperCase());

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
        alert.setHeaderText("IP Address : " + ip.getText());
        alert.setContentText("Apakah anda yakin ingin menghapus data ini ?");
        String sql = "delete from tbl_logic_ip where ip_add = ? ";
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                pst = (PreparedStatement) conn.prepareStatement(sql);
                pst.setString(1, ip.getText());
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
            konek();
            refresh();
        } catch (SQLException ex) {

        }
    }

    private void tabelsta() throws SQLException {
        konek();
        try {
            data = FXCollections.observableArrayList();
            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                String mip = set.getString(1);
                String msm = set.getString(2);
                String mg = set.getString(3);
                String mma = set.getString(4);
                
                data.add(new ML(mip, msm, mg, mma));

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        kolip.setCellValueFactory(new PropertyValueFactory<>("mip"));
        kolsm.setCellValueFactory(new PropertyValueFactory<>("msm"));
        kolg.setCellValueFactory(new PropertyValueFactory<>("mg"));
        kolma.setCellValueFactory(new PropertyValueFactory<>("mma"));
        

        tabjar.setItems(null);
        tabjar.setItems(data);
    }

    private void refresh() throws SQLException {
        konek();
        tabjar.getItems().clear();
        query = "select * from tbl_logic_ip";
        tabelsta();
    }

}
