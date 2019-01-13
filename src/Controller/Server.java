/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author HellCat
 */
public class Server implements Initializable {

    Properties properti = new Properties();
    InputStream is;
    OutputStream output = null;

    Connection con;

    String url;
    String user;
    String pass;
    String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
    @FXML
    private TextField txthost;

    @FXML
    private TextField txtport;

    @FXML
    private TextField txtdb;

    @FXML
    private TextField txtuser;

    @FXML
    private PasswordField txtpass;
      @FXML
    private Text txtstatus;
      
       @FXML
    private void btnKonek(ActionEvent event) {
        buatDBProperti();

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
        cekMySQLStatus();
        getFileData();
    }

    public void getFileData() {
        try {
            is = new FileInputStream("dbmonitoring.properties");
            properti.load(is);
            System.err.println("Host : " + properti.getProperty("host"));
            txthost.setText(properti.getProperty("host"));
            txtdb.setText(properti.getProperty("db"));
            txtuser.setText(properti.getProperty("user"));
            txtpass.setText(properti.getProperty("password"));
            txtport.setText(properti.getProperty("port"));
            is.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(S.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(S.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buatDBProperti() {
        try {
            output = new FileOutputStream("dbmonitoring.properties");

            properti.setProperty("host", txthost.getText().trim());
            properti.setProperty("port", txtport.getText().trim());
            properti.setProperty("db", txtdb.getText().trim());
            properti.setProperty("user", txtuser.getText().trim());
            properti.setProperty("password", txtpass.getText().trim());
            properti.store(output, null);
            output.close();
            if (Konekdb()) {
                con.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Koneksi Berhasil");
                alert.setHeaderText("Silahkan Login");
                alert.setContentText("Server has been connected sucessfully \n to login now click ok");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) txtport.getScene().getWindow();
                    stage.close();
                }
            } else {
                Alert pesan = new Alert(Alert.AlertType.ERROR);
                pesan.setTitle("Koneksi Gagal");
                pesan.setHeaderText("Koneksi MySQL Gagal");
                pesan.setContentText("Silahkan Coba Lagi");
                pesan.show();
            }
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(S.class.getName()).log(Level.SEVERE, null, ex);
            TrayNotification tn = new TrayNotification("Error", "" + ex, NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
        } catch (IOException | SQLException ex) {
            //Logger.getLogger(S.class.getName()).log(Level.SEVERE, null, ex);
            TrayNotification tn = new TrayNotification("Error", "" + ex, NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
        }
    }

    public void loadFile() {
        try {
            is = new FileInputStream("dbmonitoring.properties");
            properti.load(is);
            url = "jdbc:mysql://" + properti.getProperty("host") + ":" + properti.getProperty("port") + "/";
            user = properti.getProperty("user");
            pass = properti.getProperty("password");
        } catch (IOException e) {
            System.out.println("Gagal" + e);
        }
    }

    private boolean Konekdb() {
        loadFile();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url + unicode, user, pass);
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Goneksi gagal" + ex);
        }
        return false;
    }

    public void cekMySQLStatus() {
        try {
            is = new FileInputStream("dbmonitoring.properties");
            String host = properti.getProperty("host");
            int port = 3306;
            Socket socket = new Socket(host, port);
            txtstatus.setText("Server Status On");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(S.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(S.class.getName()).log(Level.SEVERE, null, ex);
            Alert pesan = new Alert(Alert.AlertType.ERROR);
            pesan.setTitle("Koneksi Gagal");
            pesan.setHeaderText("Koneksi MySQL Gagal");
            pesan.setContentText("Silahkan periksa koneksi MySQL" + "\n" + ex);
            pesan.show();
        }
    }
}
