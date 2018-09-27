package Wifi;

import Baterei.SystemCommandHandler;



public interface WirelessMonitor extends SystemCommandHandler
{
    boolean isConnected ();
    
    String wirelessName ();
    
    int linkQuality ();
    
    double signalLevel ();
    
    void updateData ();
}
