/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DashChild;

import CPU.CoresManager;
import CPU.CpuInfo;
import CPU.HostProcess;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledExecutorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class InfoController implements Initializable {

    private static final int LOOP_DELAY = 1000;
    private static final int DELAY_PROCESSES = 2000;
    private static final int MAX_DATA_POINTS = 100;
    private LineChart<Number, Number> cpuchart;
    private double prefWidth;
    private double prefHeight;
    public ObservableList<HostProcess> userProcessesList = FXCollections.observableArrayList();
    public ObservableList<HostProcess> allProcessesList = FXCollections.observableArrayList();
    private final HashMap<String, HostProcess> monitoredProcesses = new HashMap<>();
    private CoresManager coresManager;
    private NumberAxis xCPUAxis;
    private NumberAxis xMemoryAxis;
    private XYChart.Series memorySeries;

    private String filterString;

    private int currentGraphPosition = 0;

    private ScheduledExecutorService updateProcessesService;

    String ipV4;
    String ipV6;
    int cidr;
    String broadCast;
    String hostName;

    @FXML
    private Label txtip, lblwifi;
    AnchorPane D;
    @FXML
    private Label txthost;

    @FXML
    private Label txtmac, txttotald, txttotalu, txtproxy, txtproxytype, txtproxyport;
    @FXML
    private Label lblcpu;

    @FXML
    private ProgressIndicator cppro, prowifi;
    @FXML
    private AnchorPane k;

    @FXML
    private NumberAxis yAxis;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ipget();
        getInetConfig();
        proxy();
        //initTabResources();
    }

    private void ipget() {
        InetAddress ipadd;
        String hostname;
        try {
            ipadd = InetAddress.getLocalHost();
            hostname = ipadd.getHostName();
            txthost.setText(" = " + hostname);
            //  txtip.setText(" " + ipadd);
        } catch (UnknownHostException e) {
        }
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
                    //  System.out.println(address.getAddress().getHostAddress());
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

        //  System.out.println(ipV4 + " " + cidr + " " + broadCast);
        //   System.out.println(ipV6);
        txtip.setText(" = " + ipV4);
        txtmac.setText(" = " + broadCast);
    }

    public boolean isValidIPv4(String ip) {
        if (ip.substring(0, 3).equals("127")) {
            return false;
        }
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        //  System.out.println(ip + " " + ip.matches(PATTERN));
        return ip.matches(PATTERN);
    }

    private void proxy() {
        try {
            System.setProperty("java.net.useSystemProxies", "true");
            List<Proxy> l = ProxySelector.getDefault().select(
                    new URI("http://www.google.com/"));

            l.stream().map((proxy) -> {
                System.out.println("proxy hostname : " + proxy.type());
                txtproxytype.setText(" = " + proxy.type());
                return proxy;
            }).map((proxy) -> (InetSocketAddress) proxy.address()).forEachOrdered((addr) -> {
                if (addr == null) {
                    System.out.println("No Proxy");
                } else {
                    System.out.println("proxy hostname : " + addr.getHostName());
                    txtproxy.setText(" = " + addr.getHostName());
                    System.out.println("proxy port : " + addr.getPort());
                    txtproxyport.setText(" = " + addr.getPort());
                }
            });
        } catch (URISyntaxException e) {
        }
    }

    private void initTabResources() {

        int coresCount = Runtime.getRuntime().availableProcessors();//ManagementFactory.getThreadMXBean().getThreadCount();

        Label coreUsage;
        double coreWidth = prefWidth / coresCount;

        xCPUAxis = new NumberAxis(0, 100, 10);

        yAxis = new NumberAxis(0, 100, 25);

        cpuchart = new LineChart<>(xCPUAxis, yAxis);
        cpuchart.setAnimated(false);
        cpuchart.setTitle("CPU usage");

        XYChart.Series series;
        for (int i = 0; i < coresCount; i++) {
            coreUsage = new Label();
            series = new XYChart.Series();
            series.setName("cpu" + i);

            cpuchart.getData().add(series);

            coreUsage.setPrefWidth(coreWidth);
            coreUsage.setAlignment(Pos.CENTER);

            //     coresContainer.getChildren().add(coreUsage);
            coresManager.addCpuInfo(new CpuInfo("cpu" + i, coreUsage, series));
        }

    }

    private void backgroudFinishCallback() {
        HashMap<String, CpuInfo> cpus = coresManager.getCPUsInfo();

        CpuInfo cpuInfo;
        XYChart.Data data;

        for (Map.Entry<String, CpuInfo> entry : cpus.entrySet()) {
            Rectangle rect = new Rectangle(0, 0);
            rect.setVisible(false);

            cpuInfo = entry.getValue();
            data = new XYChart.Data(currentGraphPosition, cpuInfo.getUsage());
            data.setNode(rect);

            if (currentGraphPosition > MAX_DATA_POINTS) {
                cpuInfo.getSeries().getData().remove(0);
            }

            cpuInfo.getSeries().getData().add(data);
            cpuInfo.getLabel().setText(cpuInfo.getName() + ": " + Math.round(cpuInfo.getUsage()) + "%");
        }

        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double usedPercent = 100 * ((double) (1.0 - ((double) os.getFreePhysicalMemorySize() / (double) os.getTotalPhysicalMemorySize())));

        usedPercent = Math.round(usedPercent > 99.9 ? 100 : usedPercent);

        Rectangle memRect = new Rectangle(0, 0);
        memRect.setVisible(false);

        XYChart.Data memoryData = new XYChart.Data(currentGraphPosition, usedPercent);
        memoryData.setNode(memRect);

     //   memoryChart.setTitle("Memory usage (" + usedPercent + "%)");

        if (currentGraphPosition > MAX_DATA_POINTS) {
            memorySeries.getData().remove(0);
        }

        memorySeries.getData().add(memoryData);

        if (currentGraphPosition > MAX_DATA_POINTS) {
            xCPUAxis.setLowerBound(currentGraphPosition - MAX_DATA_POINTS);
            xCPUAxis.setUpperBound(currentGraphPosition);

            xMemoryAxis.setLowerBound(currentGraphPosition - MAX_DATA_POINTS);
            xMemoryAxis.setUpperBound(currentGraphPosition);
        }

        currentGraphPosition++;
    }
}
