/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rifat
 */
public class KoneksiDB {

    Properties properti = new Properties();
    InputStream is;

    public Connection con;

    String url;
    String user;
    String pass;
    String unicode = "?useUnicode=yes&characterEncoding=UTF-8";

    public void loadFileProperti() {
        try {
            is = new FileInputStream("dbmonitoring.properties");
            properti.load(is);
            url = "jdbc:mysql://" + properti.getProperty("host") + ":" + properti.getProperty("port") + "/";
            user = properti.getProperty("user");
            pass = properti.getProperty("password");
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    public Connection buatDB() throws SQLException {
        loadFileProperti();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KoneksiDB.class.getName()).log(Level.SEVERE, null, ex);

        }
        return con;
    }

    public Connection getKonek() {
        loadFileProperti();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url + unicode, user, pass);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(KoneksiDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
