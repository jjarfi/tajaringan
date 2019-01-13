/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scan;

import Controller.S;
import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 *
 * @author pacevil
 */
public class IPscanner implements Runnable {

    private final ObservableList<S.Person> data
            = FXCollections.observableArrayList();

    TableView<S.Person> tabout;

    TableColumn<S.Person, String> kolip;
    JFXProgressBar pb;

    final String THREAD_TAG;
    TextArea textArea;
    int startIp;
    int stopIp;
    int range;
    String subnet;
    String online = " ---> Online!";
    static final int TIMEOUT = 10;
    String ipadd = "";
    boolean ping;
    int numOfHost = 0;
    String Online = Integer.toString(numOfHost);

    public IPscanner(int startIp, int stopIp, String subnet, TextArea output, TableColumn kol) {
        this.startIp = startIp;
        this.stopIp = stopIp;
        this.subnet = subnet;
        textArea = output;
        kolip = kol;
        THREAD_TAG = startIp + "";
    }

    public int getLiveHosts() {
        return numOfHost;
    }

    @Override
    public void run() {
        for (int i = startIp; i < stopIp; i++) {
            try {
                InetAddress address = InetAddress.getByName(subnet + i);
                System.out.println("Pinging: " + subnet + i);
                //  textArea.appendText("Pinging: " + subnet + i + "\n");
                //   pb.setProgress(i);//updating progress bar
                ping = address.isReachable(TIMEOUT);
                if (ping) {
                    System.out.println(subnet + i + online + "\n");
                    textArea.appendText(subnet + i + online + "\n");

                    ipadd += subnet + i + Online;
                    numOfHost++;

                } else {
                    ipadd += subnet + i + "\n";
                }

                ipadd = "";
            } catch (IOException ex) {
                Logger.getLogger(IPscanner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
