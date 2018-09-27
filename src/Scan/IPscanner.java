/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scan;

import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pacevil
 */
public class IPscanner implements Runnable {

    final String THREAD_TAG;
    JFXTextArea textArea;
    int startIp;
    int stopIp;
    int range;
    String subnet;
    static final int TIMEOUT = 2000;
    String ipadd = "";
    boolean ping;
    int numOfHost = 0;

    public IPscanner(int startIp, int stopIp, String subnet, JFXTextArea output) {
        this.startIp = startIp;
        this.stopIp = stopIp;
        this.subnet = subnet;
        textArea = output;
        THREAD_TAG = startIp + "";
    }

    public int getLiveHosts() {
        return numOfHost;
    }

    @Override
    public void run() {

        //scanning network
        for (int i = startIp; i < stopIp; i++) {
            try {
                InetAddress address = InetAddress.getByName(subnet + i);
                System.out.println("Pinging: " + subnet + i);
                //scanProgressBar.setValue(i);//updating progress bar
                ping = address.isReachable(TIMEOUT);
                if (ping) {
                    System.out.println(subnet + i + " ----> is alive!" + "\n");
                    ipadd += subnet + i + " ----> is alive!\n";
                    numOfHost++;
                }
//                                else
//                {
//                    ipadd += subnet + i +"\n";
//                }
//updating text area
textArea.appendText(ipadd);
ipadd = "";
            } catch (IOException ex) {
                Logger.getLogger(IPscanner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
