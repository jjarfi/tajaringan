/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raffi
 */
public class Function {

    public static final String LOGIN = "/Form/A.fxml";
    public static final String HOME = "/Form/home.fxml";
    public static final String FORMUTAMA = "/Form/B.fxml";
    public static final String PERANGKAT = "/Form/J.fxml";
    public static final String DASHBOARD = "/Form/E.fxml";
    public static final String STATUS = "/Form/K.fxml";
    public static final String DETAIL = "/Form/L.fxml";
    public static final String JARINGAN = "/Form/N.fxml";
    public static final String SERVER = "/Form/Server.fxml";
    public static final String DETAILPERANGKAT = "/Form/O.fxml";
    public static final String MONITORING = "/Form/M.fxml";
    public static final String PACKETSSNIFFING = "/user/Bu.fxml";
    public static final String REPORT = "/Form/report.fxml";
    public static final String GAUGE = "/Form/I.fxml";
    public static final String PINGTRACERT = "/Form/S.fxml";
    public static final String NAMAAPP = "Monitoring App ~";
    public static final String NAMADASHBOARD = " DASHBOARD";
    public static final String NAMADATAPERANGKAT = " DATA PERANGKAT";
    public static final String NAMADATASTATUSPERANGKAT = " DATA LOKASI";
    public static final String NAMADATAJARINGANKOMPUTER = " DATA DETAIL IP";
    public static final String NAMAMONITORINGJARINGAN = " PING & TRACERT ROUTE";
    public static final String NAMADETAILPERANGKAT = "PENGOLAHAN DATA DETAIL PERANGKAT";
    public static final String NAMASCANIP = " SCANNING HOST";
    public static final String PAKETSNIFFING = " PAKET SNIFFING";
    public static final String LAPORAN = " LAPORAN ";
    //  public static final String CMONITORING="/Form/B.fxml/S.fxml/M.fxml";

    public final String ICON = "/Icon/icon.png";
    
        public Connection konekDB() {
        try {
            String host = "192.168.43.217";
            String username = "pace";
            String password = "123";
            String database = "dbmonitoring";
            String port = "3306";

            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
           // System.out.println("RexNET APP");

            return connection;
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Function.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
