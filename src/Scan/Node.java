/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scan;

/**
 *
 * @author pacevil
 */
public class Node {
       String ip;
    String mac;
    String CanonicalHostName;
    String HostName;
    String remark;
    boolean isReachable;
    int progressBar;

    public Node() { }

    Node(String ip, String mac, int progressBar){
        this.ip = ip;
        this.isReachable = true;
        this.progressBar = progressBar;        
    }

    public void setProgressBar(int progressBar) {
        this.progressBar = progressBar;
    }

    public String getIp() {
        return ip;
    }

    public String getHostName() {
        return HostName;
    }

    public String getMac() {
        return mac;
    }

    public String getRemark() {
        return remark;
    }

    public boolean isReachable() {
        return isReachable;
    }

    @Override
    public String toString() {
        return "IP: " + ip + "\n" +
                "MAC: " + mac + "\n" +
                "CanonicalHostName:\t" + CanonicalHostName + "\n" +
                "HostName:\t" + HostName + "\n" +
                "isReachable: " + isReachable +
                "\n" + remark;
    }
    
}
