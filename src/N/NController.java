/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package N;

import Model.MN;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author pacevil
 */

public class NController implements Initializable {

    private ObservableList<MN> data;
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
    private AnchorPane JEE;
    @FXML
    private TextField cari;
    @FXML
    private TableView tabdet;
    @FXML
    private TableColumn<MN, String> kip;
    @FXML
    private TableColumn<MN, String> kmac;
    @FXML
    private TableColumn<MN, String> kkdprg;
    @FXML
    private TableColumn<MN, String> knmprg;
    @FXML
    private TableColumn<MN, String> kkdlok;
    @FXML
    private TableColumn<MN, String> knmlok;
   @FXML
    private JFXTextArea txtping,  txttracert;
    

    
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
           try {
            konek();
            refresh();
            warna();
        txtping.selectRange(10, 100);
        } catch (SQLException ex) {

        }
           
    }
    private void warna() {
        txtping.setStyle("-fx-text-fill: #34f11f;");
    }
    
     
    @FXML
    public void setdaritabel(MouseEvent event) throws SQLException {
        try {
            MN pr = (MN) tabdet.getItems().get(tabdet.getSelectionModel().getSelectedIndex());
            
        //    txtping.setText(pr.getMip());
        
//            btnsimpan.setDisable(true);
//            btnhapus.setDisable(false);
//            btnupdate.setDisable(false);

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
     private void tabelper() throws SQLException {
        konek();
        try {
            data = FXCollections.observableArrayList();

            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                String mip = set.getString(1);
                String mmac = set.getString(2);
                String mkdprg = set.getString(3);
                String mnmprg = set.getString(4);
                String mkdlok = set.getString(5);
                String mnmlok = set.getString(6);

                data.add(new MN(mip, mmac, mkdprg, mnmprg, mkdlok, mnmlok));

            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        kip.setCellValueFactory(new PropertyValueFactory<>("mip"));
        kmac.setCellValueFactory(new PropertyValueFactory<>("mmac"));
        kkdprg.setCellValueFactory(new PropertyValueFactory<>("mkdprg"));
        knmprg.setCellValueFactory(new PropertyValueFactory<>("mnmprg"));
        kkdlok.setCellValueFactory(new PropertyValueFactory<>("mkdlok"));
        knmlok.setCellValueFactory(new PropertyValueFactory("mnmlok"));
        tabdet.setItems(null);
        tabdet.setItems(data);
    }

   
    private void refresh() throws SQLException {
        konek();
        tabdet.getItems().clear();
        query = "select * from vdetail";
        tabelper();
    }


    @FXML
    public void reTable(ActionEvent event) throws SQLException {
        refresh();
       // hapus();

}
    @FXML
    private void ping(ActionEvent e){
              try {
            MN pr = (MN) tabdet.getItems().get(tabdet.getSelectionModel().getSelectedIndex());
            
            txtping.setText(pr.getMip());
        
//            btnsimpan.setDisable(true);
//            btnhapus.setDisable(false);
//            btnupdate.setDisable(false);

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
 
     @FXML
    private void tracert(ActionEvent e){
              try {
            MN pr = (MN) tabdet.getItems().get(tabdet.getSelectionModel().getSelectedIndex());
            
            txttracert.setText(pr.getMip());
        
//            btnsimpan.setDisable(true);
//            btnhapus.setDisable(false);
//            btnupdate.setDisable(false);

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}