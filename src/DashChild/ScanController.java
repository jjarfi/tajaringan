/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DashChild;

import Scan.IPrange;
import Scan.IPscanner;
import Scan.PortScanner;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class ScanController implements Initializable {

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
    private JFXTextArea output;
     @FXML
    private JFXButton reset;
    
    @FXML 
    public void scan(ActionEvent event) throws InterruptedException{
        try{
            StartIPscan();
            }catch(InterruptedException e){
            
        }
        
    }
    
   @FXML 
    public void resetall(ActionEvent event) throws InterruptedException{
        try{ 
        Treset(); 
          }catch(Exception e){
            
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
        output.selectRange(10, 100);
    }

    private void warna() {
        output.setStyle("-fx-text-fill: white;");
    }

        private void Treset() {
        output.setText("");
       eip.setText("");
        rip.setText("");
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
        System.out.println(IPTHREADS + ", " + range + ", " + startIp + ", " + endIp);
        //output.setText(IPTHREADS + ", " );
        String name = range + ", " + startIp + ", " + endIp;
        System.out.println(Integer.toString(IPTHREADS));
        for (int i = 0; i < IPTHREADS - 1; i++) {
          //  output.appendText("starting threads");
            System.out.println("starting threads");
            

            System.out.println(i + " " + startIp + ", " + endIp);
            //output.appendText(i + " " + startIp + ", "  + endIp);
            ipScan[i] = new IPscanner(startIp, endIp, subnet, output);
            tIp[i] = new Thread((Runnable) ipScan[i]);
            startIp = endIp;
            endIp = startIp + range;
        }

        output.appendText("Scanning network " + "\"" + subnet + startIp + "...\"" + "\n");
        for (int i = 0; i < IPTHREADS - 1; i++) {
            tIp[i].start();
        }
        Thread.sleep(1000);

        for (int i = 0; i < IPTHREADS - 1; i++) {
            tIp[i].join();
        }
   
        for (int i = 0; i < IPTHREADS - 1; i++) {
            totalHost += ipScan[i].getLiveHosts();
        }
        output.appendText("\n======  Results  ======\n");
        output.appendText("Number of hosts detected: " + totalHost);
      
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
                    if(isValidIPv4(address.getAddress().getHostAddress())) {
                        ipV4 = address.getAddress().getHostAddress();
                        cidr = address.getNetworkPrefixLength();
                        broadCast = address.getBroadcast().toString().replaceAll("^/", "");
                        continue;
                    } 
                    if(address.getAddress().isLinkLocalAddress()) {
                        ipV6 = address.getAddress().getHostAddress();                    
                    }
                }
            } catch (NullPointerException e) {
            }
        }
        
//        System.out.println(ipV4 + " " + cidr + " " + broadCast);
//        System.out.println(ipV6);
    }
          public boolean isValidIPv4(String ip) {        
        if(ip.substring(0, 3).equals("127")) {
            return false;
            
        }
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        System.out.println(ip + " " + ip.matches(PATTERN));
        return ip.matches(PATTERN);
        
    }

          
          
          
}
