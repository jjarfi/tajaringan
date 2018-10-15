/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DashChild;

import Wifi.WirelessMonitor;
import static army.NetworkData.getRxChangeMap;
import static army.NetworkData.getRxCurrentMap;
import static army.NetworkData.getTxChangeMap;
import static army.NetworkData.getTxCurrentMap;
import com.jfoenix.controls.JFXProgressBar;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.FlatSkin;
import eu.hansolo.medusa.skins.ModernSkin;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import java.io.IOException;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * FXML Controller class
 *
 * @author pacevil
 */
public class InfoController implements Initializable {

    private static final Map<String, Long> rxCurrentMap = new HashMap<String, Long>();
    private static final Map<String, List<Long>> rxChangeMap = new HashMap<String, List<Long>>();
    private static final Map<String, Long> txCurrentMap = new HashMap<String, Long>();
    private static final Map<String, List<Long>> txChangeMap = new HashMap<String, List<Long>>();

    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final int UPDATE_FREQUENCY_IN_SECONDS = 1;
    private static Sigar sigar = new Sigar();

    private WirelessMonitor wirelessMonitor;

    private Gauge gauge = new Gauge();
    private Gauge gaugedua = new Gauge();
    private Gauge gaugetiga = new Gauge();
    private Tile tile = new Tile();

    private static HashMap<String, String> macLookupTable = new HashMap<>();
    private String C;
    private String ipV4;
    private String ipV6;
    private int cidr;
    private String broadCast;
    private String hostName;

    @FXML
    private Label txtip, lblwifi, lblpro, txtos, txtname;
    private AnchorPane D;
    @FXML
    private Label txthost;
    @FXML
    private Tile stockTile;

    @FXML
    private Label txtmac, txtgate, txttotald, txttotalu, txtproxy, txtproxytype, txtproxyport;
    @FXML
    private Label lblcpu, txtostipe, txtversion;

    @FXML
    private ProgressIndicator cppro, prowifi;
    @FXML
    private AnchorPane k;

    @FXML
    private NumberAxis yAxis;
    @FXML
    private Pane pangau, paneone;
    @FXML
    private FlowGridPane flow, flowdua, flowtiga;
    @FXML
    private JFXProgressBar dwn;
    private int sigarWrapper = 0;
    private final long longSigarWrapper = 0L;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            aBarCPU();
            aBarMemori();
            aInfonetwok();
            updateDataPeriodically();
            diskupdateperoide();
            netDownload();
            info();
            getInetConfig();
            proxy();
            updatesinyal();

        } catch (SigarException | InterruptedException ex) {
            Logger.getLogger(InfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void aBarCPU() {
        Timeline cpuTimeLine = new Timeline();
        cpuTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(UPDATE_FREQUENCY_IN_SECONDS),
                this::cpuudate));
        cpuTimeLine.setCycleCount(Animation.INDEFINITE);
        cpuTimeLine.play();
    }

    private void aInfonetwok() {
        Timeline cpuTimeLine = new Timeline();
        cpuTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(UPDATE_FREQUENCY_IN_SECONDS),
                this::netwok));
        cpuTimeLine.setCycleCount(Animation.INDEFINITE);
        cpuTimeLine.play();
    }

    private void netwok(ActionEvent e) {

        try {

            txtip.setText(" = " + getSigar().getNetInterfaceConfig().getAddress().trim());
            txtgate.setText(" = " + getSigar().getNetInfo().getDefaultGateway());
            txtmac.setText(" = " + getSigar().getNetInterfaceConfig().getHwaddr());

        } catch (SigarException ex) {
            logger.warning(ex.getMessage());
        }
    }

    private void aBarMemori() {
        Timeline memoryTimeLine = new Timeline();
        memoryTimeLine.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(UPDATE_FREQUENCY_IN_SECONDS),
                        this::memoriupdate));
        memoryTimeLine.setCycleCount(Animation.INDEFINITE);
        memoryTimeLine.play();
    }

    private void cpuudate(ActionEvent e) {

        try {
            double data = getSigar().getCpuPerc().getCombined();

            getGauge().setValue((int) (data * 100));
            // gauge.setValue(data );

        } catch (SigarException ex) {
            logger.warning(ex.getMessage());
        }
    }

    private void memoriupdate(ActionEvent e) {
        try {

            Long[] m = getMetrik();
            long totalrx = m[0];
            long totaltx = m[1];
            try {
                gaugetiga.setValue(Float.parseFloat(Sigar.formatSize(totalrx)));
                gaugedua.setValue(Float.parseFloat(Sigar.formatSize(totaltx)));
            } catch (NumberFormatException es) {

            }

        } catch (SigarException ex) {
            System.err.println(ex);
        }
    }

    private void netDownload() {
        Mem mem = new Mem();
        mem.toMap();
        // diskUsage.gather(sigar, name);
        // Sigar sigars = getSigar();
        mem.getFree();
        System.out.println("Total" + mem.getTotal());
        //            System.out.println("Total" + swap.getTotal());
//
//            System.out.println("Used" + swap.getUsed());
//
//            System.out.println("Free" + swap.getFree());
//System.out.println((Sigar.formatSize(swap.getTotal() - swap.getUsed())));
        try {
            //  dwn.setProgress(Float.parseFloat(Sigar.formatSize(swap.getTotal() - swap.getUsed())));
            //System.out.println(Float.parseFloat(Sigar.formatSize(swap.getTotal() - swap.getUsed())));
        } catch (NumberFormatException x) {

        }
//
        // System.out.println("PageIn=" + diskUsage.getWrites());
        // System.out.println("PageOut=" + diskUsage.getReadBytes());

    }

    private void diskupdate(ActionEvent e) {

    }

    private void diskupdateperoide() {
        Timeline tm = new Timeline();
        tm.getKeyFrames().add(new KeyFrame(Duration.seconds(UPDATE_FREQUENCY_IN_SECONDS),
                this::diskupdate));
        tm.setCycleCount(Animation.INDEFINITE);
        tm.play();
    }

    private void updateDataPeriodically() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(UPDATE_FREQUENCY_IN_SECONDS),
                        this::updateHandler));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateHandler(ActionEvent e) {

        NetInterfaceStat netInterfaceStat = new NetInterfaceStat();
        try {
            double data = getSigar().getNetInfo().getSecondaryDns().hashCode();

        } catch (SigarException ex) {

        }

    }

    private void updatesinyal() {
        try {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updatesinyalauto));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        } catch (Exception e) {

        }

    }

    private void updatesinyalauto(ActionEvent e) {
        try {
            String konek = sigar.getNetInterfaceConfig().getName();
            // String info = sigar.get;
            txtname.setText("= " + konek);

        } catch (SigarException ex) {
            Logger.getLogger(InfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void info() throws SigarException, InterruptedException {

        InetAddress ipadd;
        String hostname;

        try {
            getGauge().setAnimated(true);
            getGaugedua().setAnimated(true);
            getGaugetiga().setAnimated(true);
            ipadd = InetAddress.getLocalHost();
            hostname = ipadd.getHostName();
            txthost.setText(" = " + hostname);
            String nameOS = System.getProperty("os.name");
            txtos.setText(" = " + nameOS);
            String osType = System.getProperty("os.arch");
            txtostipe.setText(" = " + osType);
            String osVersion = System.getProperty("os.version");
            txtversion.setText(" = " + osVersion);
        } catch (UnknownHostException e) {
        }
    }

    public void getInetConfig() throws SigarException {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            try {
                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    if (isValidIPv4(address.getAddress().getHostAddress())) {
                        setIpV4(address.getAddress().getHostAddress());
                        setCidr(address.getNetworkPrefixLength());
                        setBroadCast(address.getBroadcast().toString().replaceAll("^/", ""));
                        continue;
                    }
                    if (address.getAddress().isLinkLocalAddress()) {
                        setIpV6(address.getAddress().getHostAddress());
                    }
                }
            } catch (NullPointerException e) {
            }
        }

    }

    public boolean isValidIPv4(String ip) {
        if (ip.substring(0, 3).equals("127")) {
            return false;
        }
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }

    private void proxy() throws SigarException, InterruptedException {
        try {
            flow.getChildren().addAll(getGauge());
            flowdua.getChildren().addAll(getGaugedua());
            flowtiga.getChildren().addAll(getGaugetiga());
            System.setProperty("java.net.useSystemProxies", "true");
            List<Proxy> l = ProxySelector.getDefault().select(
                    new URI("http://www.google.com/"));

            l.stream().map((proxy) -> {

                txtproxytype.setText(" = " + proxy.type());
                return proxy;
            }).map((proxy) -> (InetSocketAddress) proxy.address()).forEachOrdered((addr) -> {
                if (addr == null) {

                } else {

                    txtproxy.setText(" = " + addr.getHostName());

                    txtproxyport.setText(" = " + addr.getPort());
                }
            });

            getGauge().setSkin(new ModernSkin(getGauge()));
            getGauge().setUnit("CPU");

            getGauge().setTitle("Core : " + Runtime.getRuntime().availableProcessors());
            getGauge().setUnitColor(Color.WHITE);
            getGauge().setDecimals(0);

            getGauge().setAnimated(true);

            getGauge().setValueColor(Color.WHITE);
            getGauge().setTitleColor(Color.WHITE);
            getGauge().setBarColor(Color.rgb(0, 214, 215));
            getGauge().setNeedleColor(Color.RED);
            getGauge().setThresholdColor(Color.RED);
            getGauge().setThreshold(100);
            getGauge().setThresholdVisible(true);
            getGauge().setTickLabelColor(Color.rgb(151, 151, 151));
            getGauge().setTickMarkColor(Color.WHITE);
            getGauge().setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);

            getGaugedua().setSkin(new ModernSkin(getGaugedua()));
            getGaugedua().setUnit("Bytes/s");
            getGaugedua().setTitle("Upload");
            getGaugedua().setUnitColor(Color.WHITE);
            getGaugedua().setDecimals(0);
            // gauge.setValue(sigar.getCpuPerc().getCombined() * 100);
            getGaugedua().setAnimated(true);
            getGaugedua().setStartFromZero(true);
            getGaugedua().setValueColor(Color.WHITE);
            getGaugedua().setTitleColor(Color.WHITE);
            getGaugedua().setBarColor(Color.rgb(0, 214, 215));
            getGaugedua().setNeedleColor(Color.RED);
            getGaugedua().setThresholdColor(Color.RED);
            getGaugedua().setThreshold(100);
            getGaugedua().setThresholdVisible(true);
            getGaugedua().setTickLabelColor(Color.rgb(151, 151, 151));
            getGaugedua().setTickMarkColor(Color.WHITE);
            getGaugedua().setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);

            getGaugetiga().setSkin(new ModernSkin(getGaugetiga()));
            getGaugetiga().setUnit("Bytes/s");
            getGaugetiga().setTitle("Download");
            getGaugetiga().setUnitColor(Color.WHITE);
            getGaugetiga().setDecimals(0);
            // gauge.setValue(sigar.getCpuPerc().getCombined() * 100);
            getGaugetiga().setAnimated(true);

            getGaugetiga().setValueColor(Color.WHITE);
            getGaugetiga().setTitleColor(Color.WHITE);
            getGaugetiga().setBarColor(Color.rgb(0, 214, 215));
            getGaugetiga().setNeedleColor(Color.RED);
            getGaugetiga().setThresholdColor(Color.RED);
            getGaugetiga().setThreshold(100);
            getGaugetiga().setThresholdVisible(true);
            getGaugetiga().setTickLabelColor(Color.rgb(151, 151, 151));
            getGaugetiga().setTickMarkColor(Color.WHITE);
            getGaugetiga().setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
        } catch (URISyntaxException e) {
        }
        String sleepTime = System.getProperty("sigar.testThreadCpu.sleep");
        if (sleepTime != null) {
            Thread.sleep(Integer.parseInt(sleepTime) * 1000);
        }

    }

    static String lookupMAC(String mac) {
        String shortMac = mac.substring(0, 8);
        if (getMacLookupTable().containsKey(shortMac)) {

            return getMacLookupTable().get(shortMac);

        } else {
            String ven = getVendor(mac);
            getMacLookupTable().put(shortMac, ven);
            System.out.println(mac);
            return ven;

        }
    }

    private static String getVendor(String mac) {

        try {
            return new Scanner(new URL("http://api.macvendors.com/" + mac).openStream()).useDelimiter("\\A").next();
        } catch (IOException ex) {
        }
        return "N/A";
    }

    /**
     * @return the gaugetiga
     */
    public Gauge getGaugetiga() {
        return gaugetiga;
    }

    /**
     * @param gaugetiga the gaugetiga to set
     */
    public void setGaugetiga(Gauge gaugetiga) {
        this.gaugetiga = gaugetiga;
    }

    /**
     * @return the tile
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * @param tile the tile to set
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * @return the sigar
     */
    public static Sigar getSigar() {
        return sigar;
    }

    /**
     * @param aSigar the sigar to set
     */
    public static void setSigar(Sigar aSigar) {
        sigar = aSigar;
    }

    /**
     * @return the gaugedua
     */
    public Gauge getGaugedua() {
        return gaugedua;
    }

    /**
     * @param gaugedua the gaugedua to set
     */
    public void setGaugedua(Gauge gaugedua) {
        this.gaugedua = gaugedua;
    }

    /**
     * @return the C
     */
    public String getC() {
        return C;
    }

    /**
     * @param C the C to set
     */
    public void setC(String C) {
        this.C = C;
    }

    /**
     * @return the ipV4
     */
    public String getIpV4() {
        return ipV4;
    }

    /**
     * @param ipV4 the ipV4 to set
     */
    public void setIpV4(String ipV4) {
        this.ipV4 = ipV4;
    }

    /**
     * @return the D
     */
    public AnchorPane getD() {
        return D;
    }

    /**
     * @param D the D to set
     */
    public void setD(AnchorPane D) {
        this.D = D;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the cidr
     */
    public int getCidr() {
        return cidr;
    }

    /**
     * @param cidr the cidr to set
     */
    public void setCidr(int cidr) {
        this.cidr = cidr;
    }

    /**
     * @return the broadCast
     */
    public String getBroadCast() {
        return broadCast;
    }

    /**
     * @param broadCast the broadCast to set
     */
    public void setBroadCast(String broadCast) {
        this.broadCast = broadCast;
    }

    /**
     * @return the ipV6
     */
    public String getIpV6() {
        return ipV6;
    }

    /**
     * @param ipV6 the ipV6 to set
     */
    public void setIpV6(String ipV6) {
        this.ipV6 = ipV6;
    }

    /**
     * @return the macLookupTable
     */
    public static HashMap<String, String> getMacLookupTable() {
        return macLookupTable;
    }

    public static void setMacLookupTable(HashMap<String, String> aMacLookupTable) {
        macLookupTable = aMacLookupTable;
    }

    /**
     * @return the gauge
     */
    public Gauge getGauge() {
        return gauge;
    }

    /**
     * @param gauge the gauge to set
     */
    public void setGauge(Gauge gauge) {
        this.gauge = gauge;
    }

    /**
     * @return the sigarWrapper
     */
    public int getSigarWrapper() {
        return sigarWrapper;
    }

    /**
     * @param sigarWrapper the sigarWrapper to set
     */
    public void setSigarWrapper(int sigarWrapper) {
        this.sigarWrapper = sigarWrapper;
    }

    /**
     * @return the longSigarWrapper
     */
    public long getLongSigarWrapper() {
        return longSigarWrapper;
    }

    public static Long[] getMetrik() throws SigarException {
        for (String ni : sigar.getNetInterfaceList()) {
            // System.out.println(ni);
            NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
            NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ni);
            String hwaddr = null;
            if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())) {
                hwaddr = ifConfig.getHwaddr();
            }
            if (hwaddr != null) {
                long rxCurrenttmp = netStat.getRxBytes();
                saveChange(getRxCurrentMap(), getRxChangeMap(), hwaddr, rxCurrenttmp, ni);
                long txCurrenttmp = netStat.getTxBytes();
                saveChange(getTxCurrentMap(), getTxChangeMap(), hwaddr, txCurrenttmp, ni);
            }
        }
        long totalrx = getMetricData(getRxChangeMap());
        long totaltx = getMetricData(getTxChangeMap());
        getRxChangeMap().values().forEach((l) -> {
            l.clear();
        });
        getTxChangeMap().values().forEach((l) -> {
            l.clear();
        });
        return new Long[]{totalrx, totaltx};
    }

    private static long getMetricData(Map<String, List<Long>> rxChangeMap) {
        long total = 0;
        for (Map.Entry<String, List<Long>> entry : rxChangeMap.entrySet()) {
            int average = 0;
            for (Long l : entry.getValue()) {
                average += l;
            }
            total += average / entry.getValue().size();
        }
        return total;
    }

    private static void saveChange(Map<String, Long> currentMap,
            Map<String, List<Long>> changeMap, String hwaddr, long current,
            String ni) {
        Long oldCurrent = currentMap.get(ni);
        if (oldCurrent != null) {
            List<Long> list = changeMap.get(hwaddr);
            if (list == null) {
                list = new LinkedList<>();
                changeMap.put(hwaddr, list);
            }
            list.add((current - oldCurrent));
        }
        currentMap.put(ni, current);
    }

}
