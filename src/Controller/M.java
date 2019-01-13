/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Function.PacketContents;
import Function.Sniffer;
import Function.Threads;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static javafx.stage.StageStyle.TRANSPARENT;
import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

/**
 * FXML Controller class
 *
 * @author Rexnet
 */
public class M implements Initializable {

    Threads THREAD;
    public static JpcapCaptor CAP;
    public static jpcap.NetworkInterface[] NETWORK_INTERFACES;
    public static int INDEX = 0;
    public static int No = 0;
    boolean CaptureState = false;
    private final String interfacekoneksi = null;
    List<Packet> packetList = new ArrayList<>();
    @FXML
    private ComboBox cbdev, cbpaket;
    @FXML
    public static TableView datatab;
    @FXML
    private AnchorPane rooot;
    @FXML
    private AnchorPane root;
    private double initX;
    private double initY;

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
    }

    @FXML
    public void drag(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setX(event.getScreenX() - initX);
        stage.setY(event.getScreenY() - initY);
    }

    @FXML
    public void pres(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        initX = event.getScreenX() - stage.getX();
        initY = event.getScreenY() - stage.getY();
    }

    @FXML
    public void clickCapture(ActionEvent event) {
        try {
            CapturePackets();
        } catch (Exception ex) {

        }

    }

    @FXML
    public void Driver(MouseEvent event) {
        cbdev.getItems().clear();
        try {
            dapatDev();
        } catch (SocketException ex) {

        }
    }

    @FXML
    private void tutup(ActionEvent event) {
        tutup();
    }

    private void dapatDev() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        while (nets.hasMoreElements()) {
            NetworkInterface netint = nets.nextElement();
            if (Sniffer.possiedeip(netint)) {
                cbdev.getItems().addAll(netint.getDisplayName());
                cbdev.getSelectionModel();
            }

        }
    }

    public void CapturePackets() {

        THREAD = new Threads() {

            @Override
            public Object construct() {

                try {

                    CAP = JpcapCaptor.openDevice(NETWORK_INTERFACES[INDEX], 65535, false, 20);

                    if (null != cbpaket.getSelectionModel().toString()) {
                        switch (cbpaket.getSelectionModel().toString()) {
                            case "UDP":
                                CAP.setFilter("udp", true);
                                break;
                            case "TCP":
                                CAP.setFilter("tcp", true);
                                break;
                            case "ICMP":
                                CAP.setFilter("icmp", true);
                                break;
                            default:
                                break;
                        }
                    }

                    while (CaptureState) {

                        CAP.processPacket(1, new PacketContents());
                        packetList.add(CAP.getPacket());
                    }
                    CAP.close();

                } catch (IOException e) {
                    System.out.print(e);
                }
                return 0;
            }

            @Override
            public void finished() {
                this.interrupt();
            }
        };

        THREAD.start();

    }

    public void komboload() {
        cbpaket.getItems().addAll("Default All", "TCP", "UDP", "ICMP");
        cbpaket.getSelectionModel();

    }

    private void tutup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setContentText("Yakin Ingin Log Out ?");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = new Stage();
            stage.initStyle(TRANSPARENT);
            Stage s = (Stage) rooot.getScene().getWindow();
            s.close();

        } else {

        }
    }
}
