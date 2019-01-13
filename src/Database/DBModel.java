/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Function.Function;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author HellCat
 */
public class DBModel {

    PreparedStatement pst;
    Properties properti = new Properties();
    InputStream iS;
    String db;

    public void loadFileProperti() {
        try {
            iS = new FileInputStream("dbmonitoring.properties");
            properti.load(iS);
            db = properti.getProperty("db");
        } catch (IOException e) {
            System.out.println("Gagal" + e);
        }
    }

    public void createDataBase() {
        loadFileProperti();
        KoneksiDB con = new KoneksiDB();
        try {
           // pst = con.buatDB().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
           // pst.execute();
            pst = con.buatDB().prepareStatement("create database if not exists " + db + " DEFAULT CHARACTER SET latin1 \n"
                    + " DEFAULT CHARSET=latin1");

            pst = con.buatDB().prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            pst.executeUpdate();
            pst = con.buatDB().prepareStatement("CREATE TABLE if not exists " + db + ".`login` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `username` VARCHAR(20) NOT NULL,\n"
                    + "  `password` VARCHAR(100) ,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC))\n"
                    + "  ENGINE=InnoDB DEFAULT CHARSET=latin1;");

            pst.execute();

            pst = con.buatDB().prepareStatement("CREATE TABLE if not exists " + db + ".`tbl_perangkat` (\n"
                    + "  `kd_prg` varchar(10) NOT NULL PRIMARY KEY,\n"
                    + "  `nm_prg` varchar(20) DEFAULT NULL,\n"
                    + "  `jns_prg` varchar(15) DEFAULT NULL,\n"
                    + "  `status` varchar(20) DEFAULT NULL,\n"
                    + "  `kondisi` varchar(9) DEFAULT NULL,\n"
                    + "  `foto` mediumblob)\n"
                    + "   ENGINE=InnoDB DEFAULT CHARSET=latin1;");

            pst.execute();

            pst = con.buatDB().prepareStatement("CREATE TABLE if not exists " + db + ".`tbl_lokasi` (\n"
                    + "  `kd_lokasi` varchar(7) NOT NULL PRIMARY KEY,\n"
                    + "  `nm_lokasi` varchar(40) DEFAULT NULL,\n"
                    + "  `ket` varchar(100))\n"
                    + "  ENGINE=InnoDB DEFAULT CHARSET=latin1;");

            pst.execute();

            pst = con.buatDB().prepareStatement("CREATE TABLE if not exists " + db + ".`tbl_logic_ip` (\n"
                    + "  `ip_add` varchar(20) NOT NULL PRIMARY KEY,\n"
                    + "  `subnet_mask` varchar(20) DEFAULT NULL,\n"
                    + "  `getway` varchar(20) ,\n"
                    + "  `mac_add` varchar(20))\n"
                    + "   ENGINE=InnoDB DEFAULT CHARSET=latin1;");
            pst.execute();

            pst = con.buatDB().prepareStatement("CREATE TABLE if not exists " + db + ".`tbl_detail_prg` (\n"
                    + "  `id_dtl_prg` varchar(8) NOT NULL PRIMARY KEY,\n"
                    + "  `kd_prg` varchar(10) NOT NULL,\n"
                    + "  `kd_lokasi` varchar(7) NOT NULL,\n"
                    + "  `ip_add` varchar(20) NOT NULL,\n"
                    + "  KEY `kd_prg` (`kd_prg`),\n"
                    + "  KEY `kd_lokasi` (`kd_lokasi`),\n"
                    + "  KEY `ip_add` (`ip_add`),\n"
                    + "  CONSTRAINT `tbl_detail_prg_ibfk_1` FOREIGN KEY (`kd_prg`) REFERENCES `tbl_perangkat` (`kd_prg`),\n"
                    + "  CONSTRAINT `tbl_detail_prg_ibfk_2` FOREIGN KEY (`kd_lokasi`) REFERENCES `tbl_lokasi` (`kd_lokasi`),\n"
                    + "  CONSTRAINT `tbl_detail_prg_ibfk_3`  FOREIGN KEY (`ip_add`) REFERENCES `tbl_logic_ip` (`ip_add`))\n"
                    + "  ENGINE=InnoDB DEFAULT CHARSET=latin1;");
            pst.execute();

            System.out.println("Databses berhasil dibuat");

        } catch (SQLException ex) {
            System.out.println("Error" + ex);
            try {
                Parent root = FXMLLoader.load(getClass().getResource(Function.SERVER));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle(Function.NAMAAPP);
                stage.showAndWait();
            } catch (IOException ex1) {
                Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

}
