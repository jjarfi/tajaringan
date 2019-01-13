/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import army.Army;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HellCat
 */
public class DBProperti {

    Properties properti = new Properties();
    InputStream is;
    OutputStream output = null;

    public void buatDbProperti() {

        try {
            output = new FileOutputStream("dbmonitoring.properties");
            properti.setProperty("host", "localhost");
            properti.setProperty("port", "3306");
            properti.setProperty("db", "bony");
            properti.setProperty("user", "bony");
            properti.setProperty("password", "");
            properti.store(output, null);
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Army.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Army.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String loadFileProperti() {
        try {
            is = new FileInputStream("dbmonitoring.properties");
            properti.load(is);
            return properti.getProperty("db");
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
        return "";
    }
}
