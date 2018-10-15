/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DashChild;

import Scan.IPrange;
import Scan.IPscanner;
import Scan.PortScanner;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafx.stage.StageStyle.TRANSPARENT;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class ScanController implements Initializable {

    private final ObservableList<Person> data
            = FXCollections.observableArrayList();

    String ipV4;
    String ipV6;
    int cidr;
    String broadCast;
    String hostName;

    IPrange netRange = new IPrange();
    ArrayList<String> r = netRange.getRange();

    int IPTHREADS;
    int range;
    Thread tIp[];
    IPscanner ipScan[];
    int startIp = 0;
    int endIp;
    int totalHost = 0;

    ArrayList<Integer> allPorts = new ArrayList();
    //portThreads is the number of threds for port scanning
    int PTHREADS;
    int prange;
    Thread portThread[];
    PortScanner portTask[];
    int startPort;
    int endPort;

    @FXML
    private AnchorPane k;

    @FXML
    private TextField eip;

    @FXML
    private TextField rip;
    @FXML
    private TextArea txtout;
    @FXML
    private ListView<IPscanner> list;
    @FXML
    private TableView<Person> tabout;

    @FXML
    private TableColumn<Person, String> kolip;
    @FXML
    private TableColumn<Person, String> kolip1;
    @FXML
    private TableColumn<Person, String> kolstatus;

    @FXML
    public void scan(ActionEvent event) throws InterruptedException, Exception {
        try {
            StartIPscan();

        } catch (InterruptedException e) {

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
        warna();
        txtout.selectRange(100, 10000);
        text();

    }

    private void warna() {
        txtout.setStyle("-fx-text-fill: black;");
        txtout.setBackground(Background.EMPTY);
    }

    public void StartIPscan() throws InterruptedException {
        this.getInetConfig();

        String ipFrom = eip.getText();
        String ipTo = rip.getText();
        netRange.setFromip(ipFrom);
        netRange.setToip(ipTo);
        netRange.StrToIP();
        netRange.calcNetwork();
        IPTHREADS = r.size();
        range = r.size() / IPTHREADS;
        tIp = new Thread[IPTHREADS];
        ipScan = new IPscanner[IPTHREADS];
        String subnet = netRange.getSubnet();
        endIp = startIp + range;
        String on = "Online";
        //System.out.println(IPTHREADS + ", " + range + ", " + startIp + ", " + endIp);
        txtout.appendText(IPTHREADS + ", ");
        String name = range + ", " + startIp + ", " + endIp;

        System.out.println(Integer.toString(IPTHREADS));

        for (int i = 0; i < IPTHREADS - 1; i++) {
            //    txtout.appendText("starting threads");
            System.out.println("starting threads");

            System.out.println(i + " " + startIp + ", " + endIp);
            ipScan[i] = new IPscanner(startIp, endIp, subnet, txtout, kolip);
            tIp[i] = new Thread((Runnable) ipScan[i]);
            startIp = endIp;
            endIp = startIp + range;

        }

        txtout.appendText("Scanning network " + "\"" + subnet + startIp + "...\"" + "\n");

        for (int i = 0; i < IPTHREADS - 1; i++) {
            tIp[i].start();

        }
        Thread.sleep(10000);

        for (int i = 0; i < IPTHREADS - 1; i++) {
            tIp[i].join();
        }

        for (int i = 0; i < IPTHREADS - 1; i++) {

            totalHost += ipScan[i].getLiveHosts();

            data.addAll(
                    //   new Person(i + "", subnet + i + ipScan[i].getLiveHosts()+ "",  ""));
                    new Person(i + "", subnet + i + "", ipScan[i].getLiveHosts() + ""));
            text();
        }
        txtout.appendText("Number of hosts detected: " + totalHost);
        kolip1.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));
        kolip.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        kolstatus.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        // tabout.sort();
        tabout.setItems(data);

        // new IPscanner(startIp, endIp, subnet,txtout,kolip));
        // new IPscanner("Ethan", "Williams", "ethan.williams@example.com"),
        //  new IPscanner("Emma", "Jones", "emma.jones@example.com"),
        // new IPscanner("Michael", "Brown", "michael.brown@example.com"));
        // txtout.appendText("Number of hosts detected: " + ipScan);
    }

    public void getInetConfig() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
        }
        while (networkInterfaces.hasMoreElements()) {

            NetworkInterface networkInterface = networkInterfaces.nextElement();
            try {
                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    System.out.println(address.getAddress().getHostAddress());
                    if (isValidIPv4(address.getAddress().getHostAddress())) {
                        ipV4 = address.getAddress().getHostAddress();
                        cidr = address.getNetworkPrefixLength();
                        broadCast = address.getBroadcast().toString().replaceAll("^/", "");
                        continue;
                    }
                    if (address.getAddress().isLinkLocalAddress()) {
                        ipV6 = address.getAddress().getHostAddress();
                    }
                }
            } catch (NullPointerException e) {
            }
        }

//        System.out.println(ipV4 + " " + cidr + " " + broadCast);
//        System.out.println(ipV6);
        //   txtout.appendText(ipV4 + "\n" + cidr + "\n" + broadCast);
        //    txtout.appendText(ipV6);
    }

    public boolean isValidIPv4(String ip) {
        if (ip.substring(0, 3).equals("127")) {
            return false;
        }
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        System.out.println(ip + " " + ip.matches(PATTERN));
        return ip.matches(PATTERN);
    }

    public static class Person {

        private String firstname1;
        private String lastName;
        private String email;

        public Person(String fName, String lName, String email) {
            this.firstname1 = fName;
            this.lastName = lName;
            this.email = email;
        }

        public String getFirstName() {
            return firstname1;
        }

        public void setFirstName(String firstname1) {
            this.firstname1 = firstname1;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String fName) {
            lastName = fName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String fName) {
            email = fName;
        }
    }

    private boolean text() {
        if (kolstatus.equals(0)) {
            kolstatus.setCellValueFactory(new PropertyValueFactory<>("Offline"));
        } else {
            kolstatus.setCellValueFactory(new PropertyValueFactory<>("Online"));
        }
        return true;
    }

    @FXML
    private void reset(ActionEvent event) throws InterruptedException {
        txtout.setText("");
        tabout.setItems(null);
        Thread.interrupted();

    }

    @FXML
    public void sniffing(ActionEvent event) {
        try {
            snif();
        } catch (UnknownHostException e) {

        }
    }

    private void snif() throws UnknownHostException {
        InetAddress group = null;
        int port = 0;

        try {
            group = InetAddress.getByName(eip.getText());
            port = Integer.parseInt(rip.getText());
        } // end try
        catch (NumberFormatException ex) {

            System.err.println(
                    "Usage: java MulticastSniffer multicast_address port");
            txtout.appendText(ipV4);

            System.exit(1);
        }
        MulticastSocket ms = null;
        try {
            ms = new MulticastSocket(port);
            ms.joinGroup(group);
            byte[] buffer = new byte[8192];
            while (true) {
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                ms.receive(dp);
                String s = new String(dp.getData());
                System.out.println(s);
                txtout.appendText(s);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (ms != null) {
                try {
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    @FXML
    private void l(ActionEvent event) {

        try {

            Parent roort = FXMLLoader.load(getClass().getResource("/PING/Ping.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(roort);
           // scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setTitle("PORT SCANNING");

            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
            stage.show();

        } catch (IOException ex) {

        }

    }

}
