/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Function.Function;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Rexnet
 */
public class E implements Initializable {

    Function function = new Function();
    @FXML
    private AnchorPane J, jendela;
    AnchorPane F, I;

    private void setNode(Node node) {
        jendela.getChildren().clear();
        jendela.getChildren().add(node);
    }

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
            I = FXMLLoader.load(getClass().getResource(Function.GAUGE));
            F = FXMLLoader.load(getClass().getResource(Function.PINGTRACERT));
        } catch (IOException ex) {

        }
        setNode(I);
  
    }

    @FXML
    private void moveI(ActionEvent event) {
        try {
            setNode(I);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void moveF(ActionEvent event) {
        try {
            setNode(F);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
