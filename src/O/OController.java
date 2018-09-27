/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package O;

import Model.MJ;
import Model.MO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class OController implements Initializable {

    private ObservableList<MO> data;
    final ObservableList options = FXCollections.observableArrayList();
    final ObservableList optionss = FXCollections.observableArrayList();
    final ObservableList optionsss = FXCollections.observableArrayList();

    private ResultSet rs;
    private Connection conn;
    String query = "";
    private PreparedStatement pst;

    @FXML
    private AnchorPane root;
    @FXML
    //  private AnchorPane JEE,
    private TextField txtnp, txtnl, txtmac, txtdet,cari;
    @FXML
    private ComboBox kp, kl, cip;
    @FXML
    private TableView<MO> tabdet;

    @FXML
    private TableColumn<MO, String> kolid;

    @FXML
    private TableColumn<MO, String> kolkp;

    @FXML
    private TableColumn<MO, String> kollok;

    @FXML
    private TableColumn<MO, String> kolip;

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

    private void prg() {
        konek();
        options.clear();
        try {
            rs = conn.createStatement().executeQuery("select * from tbl_perangkat");
            while (rs.next()) {
                options.add(rs.getString("kd_prg"));
                kp.setItems(options);

            }

        } catch (SQLException ex) {

            System.err.println("Error" + ex);
        }

    }

    private void lokasi() {
        konek();
        optionss.clear();
        try {
            rs = conn.createStatement().executeQuery("select * from tbl_lokasi");
            while (rs.next()) {
                optionss.add(rs.getString("kd_lokasi"));
                kl.setItems(optionss);

            }

        } catch (SQLException ex) {

            System.err.println("Error" + ex);
        }

    }

    private void ipA() {
        konek();
        optionsss.clear();
        try {
            rs = conn.createStatement().executeQuery("select * from tbl_logic_ip");
            while (rs.next()) {
                optionsss.add(rs.getString("ip_add"));
                cip.setItems(optionsss);

            }

        } catch (SQLException ex) {

            System.err.println("Error" + ex);
        }

    }

    public void loadmanaprgkombo(MouseEvent event) {
        prg();
    }

    public void loadprgkombo(ActionEvent event) throws SQLException {
        loadprg();
    }

    private void loadprg() throws SQLException {
        konek();
        try {
            String sql = "select * from tbl_perangkat where kd_prg = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, (String) kp.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            while (rs.next()) {

                String namaprg = rs.getString("nm_prg");
                txtnp.setText(namaprg);

            }
        } catch (SQLException ex) {

        }

    }

    public void loadmanaIPkombo(MouseEvent event) {
        ipA();
    }

    public void loadIPkombo(ActionEvent event) throws SQLException {
        loadIP();
    }

    private void loadIP() throws SQLException {
        konek();
        try {
            String sql = "select * from tbl_logic_ip where ip_add = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, (String) cip.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            while (rs.next()) {

                String namaprg = rs.getString("mac_add");
                txtmac.setText(namaprg);

            }
        } catch (SQLException ex) {

        }

    }

    public void loadmanastkombo(MouseEvent event) {
        try{
           lokasi(); 
        }catch(Exception e){
            System.out.println(e);
        }
        
    }

    public void loadstkombo(ActionEvent event) throws SQLException {
          try{
           loadlokasi(); 
        }catch(Exception e){
            System.out.println(e);
        }
        
    }

    private void loadlokasi() throws SQLException {
        konek();
        try {
            String sql = "select * from tbl_lokasi where kd_lokasi = ?";
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, (String) kl.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            while (rs.next()) {

                String namaprg = rs.getString("nm_lokasi");
                txtnl.setText(namaprg);

            }
        } catch (SQLException ex) {

        }

    }

    @FXML
    public void simpan(ActionEvent event) {
        simpan();
    }

    private void simpan() {
        if (txtdet.getText().isEmpty()) {
            TrayNotification tn = new TrayNotification("WANING", "ID Detail Tidak Boleh Kosong", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
        } else {

            try {
                Statement stt = conn.createStatement();
                String sql = "select * from tbl_detail_prg where id_dtl_prg = '" + txtdet.getText() + "'";
                rs = stt.executeQuery(sql);
                if (rs.next() == true) {
                    TrayNotification tn = new TrayNotification("WARNING", "ID Detail Sudah Terdaftar", NotificationType.NOTICE);
                    tn.setAnimationType(AnimationType.SLIDE);
                    tn.showAndDismiss(Duration.seconds(1));
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    konek();
                    String insert = "insert into tbl_detail_prg(id_dtl_prg,kd_prg,kd_lokasi,ip_add) values (?,?,?,?)";
                    try {
                        pst = conn.prepareStatement(insert);
                        pst.setString(1, txtdet.getText().toUpperCase());
                        pst.setString(2, kp.getValue().toString().toUpperCase());
                        pst.setString(3, kl.getValue().toString().toUpperCase());
                        pst.setString(4, cip.getValue().toString().toUpperCase());
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
    }
    
    @FXML
    public void update(ActionEvent event) {
        update();
    }

    private void update() {
        if (txtdet.getText().isEmpty()) {
            TrayNotification tn = new TrayNotification("WANING", "ID Detail Tidak Boleh Kosong", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
        } else {

            konek();
            String insert = "update tbl_detail_prg set kd_prg=?, kd_lokasi=?, ip_add=? where id_dtl_prg =? ";
            try {
                pst = conn.prepareStatement(insert);
                pst.setString(4, txtdet.getText().toUpperCase());
                pst.setString(1, kp.getValue().toString().toUpperCase());
                pst.setString(2, kl.getValue().toString().toUpperCase());
                pst.setString(3, cip.getValue().toString().toUpperCase());
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
    }

    @FXML
    public void cariper (KeyEvent event) throws SQLException {

        if (cari.getText().equals("")) {
            refresh();
        } else {

            data.clear();
            String sql = "select * from tbl_detail_prg where id_dtl_prg LIKE '%" + cari.getText() + "%'"
                    +"UNION select * from tbl_detail_prg where kd_prg LIKE '%" + cari.getText() + "%'"
                    +"UNION select * from tbl_detail_prg where kd_lok LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_detail_prg where ip_add LIKE '%" + cari.getText() + "%'";
            try {
                pst = (PreparedStatement) conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println("" + rs.getString(2));
                    data.add(new MO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));

                }
                tabdet.setItems(data);
            } catch (SQLException ex) {
            }
        }

    }
    
    
   @FXML
   public void caridet (KeyEvent event) throws SQLException {

        if (cari.getText().equals("")) {
            refresh();
        } else {  

            data.clear();
            String sql = "select * from tbl_detail_prg where kd_prg LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_detail_prg where kd_lokasi LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_detail_prg where ip_add LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_detail_prg where id_dtl_prg LIKE '%" + cari.getText() + "%'";
            try {
                pst = (PreparedStatement) conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println("" + rs.getString(2));
                    data.add(new MO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));

                }
                tabdet.setItems(data);
            } catch (SQLException ex) {
            }
        }

    }  

     
    
    private void hapus() {
        txtdet.setText("");
        txtnp.setText("");
        txtnl.setText("");
        txtmac.setText("");
        kp.setValue("");
        cip.setValue("");
        kl.setValue("");

    }

    private void tabelper() throws SQLException {
        konek();
        try {
            data = FXCollections.observableArrayList();

            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                String mid = set.getString(1);
                String mkdprg = set.getString(2);
                String mkds = set.getString(3);
                String mip = set.getString(4);

                data.add(new MO(mid, mkdprg, mkds, mip));

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        kolid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        kolkp.setCellValueFactory(new PropertyValueFactory<>("mkdprg"));
        kollok.setCellValueFactory(new PropertyValueFactory<>("mkds"));
        kolip.setCellValueFactory(new PropertyValueFactory<>("mip"));
        tabdet.setItems(null);
        tabdet.setItems(data);
    }

    private void refresh() throws SQLException {
        konek();
        tabdet.getItems().clear();
        query = "select * from tbl_detail_prg";
        tabelper();
    }
        @FXML
    public void setdaritabel(MouseEvent event) throws SQLException {
        try {
            MO pr = tabdet.getItems().get(tabdet.getSelectionModel().getSelectedIndex());
            txtdet.setText(pr.getMid());
            kp.setValue(pr.getMkdprg());
            cip.setValue(pr.getMip());
            kl.setValue(pr.getMkds());
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
    public void delete(ActionEvent event) {
        try {
            konek();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("HAPUS");
            alert.setHeaderText("ID Detail Perangkat : " + txtdet.getText());
            alert.setContentText("Apakah anda yakin ingin menghapus data ini ?");
            String sql = "delete from tbl_detail_prg where id_dtl_prg = ? ";
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    pst.setString(1, txtdet.getText());
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
            pst.close();
            conn.close();
        } catch (SQLException ex) {

        }
    }


}
