/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.DBProperti;
import Function.Function;
import Model.MJ;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Rexnet
 */
public class J implements Initializable {

    DBProperti dBProperties = new DBProperti();
    String db = dBProperties.loadFileProperti();
    private ObservableList<MJ> data;

    private ResultSet rs;
    private Function function;
    private Connection conn;
    String query = "";
    private PreparedStatement pst;
    private File file;
    String s;
    private FileInputStream fis;
    BufferedImage imgt;
    private Image image;
    private FileChooser filechos;
    private Stage stage;
    private final Desktop desktop = Desktop.getDesktop();

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane JEE;
    @FXML
    private AnchorPane je;
    @FXML
    private TextField fp, kp, np, jp, cari;

    @FXML
    private ComboBox lp;

    @FXML
    private ComboBox konpe;

    @FXML
    private ImageView ip;
    @FXML
    private TableView<MJ> tabper;

    @FXML
    private TableColumn<MJ, String> kolkp;

    @FXML
    private TableColumn<MJ, String> kolnp;

    @FXML
    private TableColumn<MJ, String> koljp;

    @FXML
    private TableColumn<MJ, String> kollp;

    @FXML
    private TableColumn<MJ, String> kolkonpe;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        komboload();
        function = new Function();
        try {
            refresh();
        } catch (SQLException ex) {

        }
        filechos = new FileChooser();
        filechos.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "* *"),
                new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.gif", "*.jpeg"));

    }

    public void konek() {
        conn = function.konekDB();
    }

    @FXML
    public void setdaritabel(MouseEvent event) throws SQLException {
        try {
            MJ pr = tabper.getItems().get(tabper.getSelectionModel().getSelectedIndex());
            kp.setText(pr.getMkp());
            np.setText(pr.getMnp());
            jp.setText(pr.getMjp());
            lp.setValue(pr.getMlp());
            konpe.setValue(pr.getMkonpe());
            lihatgambar(pr.getMkp());
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
    public void simpan(ActionEvent event) {
        try {
            simpan();
        } catch (FileNotFoundException ex) {

        }
    }

    private void simpan() throws FileNotFoundException {
        if (kp.getText().isEmpty()) {
            TrayNotification tn = new TrayNotification("WANING", "Kode Perangkat Tidak Boleh Kosong", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
        } else if (np.getText().isEmpty()) {
            TrayNotification tn = new TrayNotification("WANING", "Nama Perangkat Tidak Boleh Kosong", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));

        } else if (lp.getValue() == null) {
            TrayNotification tn = new TrayNotification("WANING", "Status Perangkat Tidak Boleh Kosong", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));

        } else {

            try {
                Statement stt = conn.createStatement();
                String sql = "select * from tbl_perangkat where kd_prg = '" + kp.getText() + "'";
                rs = stt.executeQuery(sql);
                if (rs.next() == true) {
                    TrayNotification tn = new TrayNotification("WARNING", "Kode Perangkat Sudah Terdaftar", NotificationType.NOTICE);
                    tn.setAnimationType(AnimationType.SLIDE);
                    tn.showAndDismiss(Duration.seconds(1));
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    konek();
                    String insert = "insert into tbl_perangkat(kd_prg,nm_prg,jns_prg,status,kondisi,foto) values (?,?,?,?,?,?)";
                    try {
                        pst = conn.prepareStatement(insert);
                        pst.setString(1, kp.getText().toUpperCase());
                        pst.setString(2, np.getText().toUpperCase());
                        pst.setString(3, jp.getText().toUpperCase());
                        pst.setString(4, lp.getValue().toString().toUpperCase());
                        pst.setString(5, konpe.getValue().toString().toUpperCase());
                        fis = new FileInputStream(file);
                        pst.setBinaryStream(6, fis, file.length());
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
        try {
            update();
        } catch (Exception ex) {

        }

    }

    private void update() {
        konek();
        String update = "update tbl_perangkat set nm_prg=?, jns_prg=?, status=?, kondisi=?,foto=? where kd_prg=?";
        try {
            pst = conn.prepareStatement(update);

            pst.setString(1, np.getText().toUpperCase());
            pst.setString(2, jp.getText().toUpperCase());
            pst.setString(3, lp.getValue().toString().toUpperCase());
            pst.setString(4, konpe.getValue().toString().toUpperCase());
            fis = new FileInputStream(file);
            pst.setBinaryStream(5, fis, file.length());
            pst.setString(6, kp.getText().toUpperCase());
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

        } catch (FileNotFoundException ex) {

        }

    }

    @FXML
    public void cariper(KeyEvent event) throws SQLException {

        if (cari.getText().equals("")) {
            refresh();
        } else {

            data.clear();
            String sql = "select * from tbl_perangkat where kd_prg LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_perangkat where nm_prg LIKE '%" + cari.getText() + "%'"
                    + "UNION select * from tbl_perangkat where kondisi LIKE '%" + cari.getText() + "%'";
            try {
                pst = (PreparedStatement) conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println("" + rs.getString(2));
                    data.add(new MJ(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

                }
                tabper.setItems(data);
            } catch (SQLException ex) {
            }
        }

    }

    private void hapus() {
        kp.setText("");
        np.setText("");
        jp.setText("");
        lp.setValue("");
        konpe.setValue("");
        ip.setImage(null);
        fp.setText("");
    }

    @FXML
    public void delete(ActionEvent event) {
        try {
            konek();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("HAPUS");
            alert.setHeaderText("Kode Perangkat : " + kp.getText() + "\nNama Perangkat : " + np.getText());
            alert.setContentText("Apakah anda yakin ingin menghapus data ini ?");
            String sql = "delete from tbl_perangkat where kd_prg = ? ";
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    pst.setString(1, kp.getText());
                    int i = pst.executeUpdate();
                    if (i == 1) {
                        hapus();
                        refresh();
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

    public void komboload() {
        lp.getItems().addAll("Belum Terpakai", "Sudah Terpakai");
        lp.getSelectionModel();
        konpe.getItems().addAll("Bagus", "Rusak");
        konpe.getSelectionModel();

    }

    @FXML
    public void browse(ActionEvent event) {

        stage = (Stage) root.getScene().getWindow();
        file = filechos.showOpenDialog(stage);
        if (file != null) {
            image = new Image(file.getAbsoluteFile().toURI().toString());
            ip.setImage(image);

            fp.setText(file.getAbsolutePath());
        }

    }

    private void tabelper() throws SQLException {
        konek();
        try {
            data = FXCollections.observableArrayList();

            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                String mkp = set.getString(1);
                String mnp = set.getString(2);
                String mjp = set.getString(3);
                String mlp = set.getString(4);
                String mkonpe = set.getString(5);
                String mfoto = set.getString(6);
                data.add(new MJ(mkp, mnp, mjp, mlp, mkonpe, mfoto));

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        kolkp.setCellValueFactory(new PropertyValueFactory<>("mkp"));
        kolnp.setCellValueFactory(new PropertyValueFactory<>("mnp"));
        koljp.setCellValueFactory(new PropertyValueFactory<>("mjp"));
        kollp.setCellValueFactory(new PropertyValueFactory<>("mlp"));
        kolkonpe.setCellValueFactory(new PropertyValueFactory<>("mkonpe"));
        tabper.setItems(null);
        tabper.setItems(data);
    }

    private void refresh() throws SQLException {
        konek();
        tabper.getItems().clear();
        query = "select * from tbl_perangkat";
        tabelper();
    }

    private boolean valid() {
        if (kp.getText().isEmpty()) {

        } else if (np.getText().isEmpty()) {
            TrayNotification tn = new TrayNotification("WANING", "Nama Perangkat Tidak Boleh Kosong", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
        }
        return true;
    }

    private void lihatgambar(String jarfi) {
        try {
            pst = conn.prepareStatement("Select foto from tbl_perangkat where kd_prg = ?");
            pst.setString(1, jarfi);
            rs = pst.executeQuery();
            if (rs.next()) {
                InputStream is = rs.getBinaryStream(1);
                OutputStream os = new FileOutputStream(new File("jarfi.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }

                image = new Image("file:jarfi.jpg");
                ip.setImage(image);
            }

        } catch (IOException | SQLException ex) {

        }
    }
}
