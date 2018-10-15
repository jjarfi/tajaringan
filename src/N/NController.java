/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package N;

import O.OController;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class NController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane JEE;
    @FXML
    private TextField cari;
    @FXML
    private ComboBox cbdev;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void scann(ActionEvent event) {
        try {
            dapatDev();
        } catch (SocketException ex) {
            Logger.getLogger(OController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dapatDev() throws SocketException {
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) interfaces.nextElement();
            System.out.println(ni);
            //  cbdev.selectionModelProperty(ni);
            //cbdev.setItems(null);
            cbdev.getItems().addAll(ni);
            cbdev.getSelectionModel();
        }

    }

  
}
