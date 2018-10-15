package Sistem;

import Wifi.WirelessMonitor;
import java.io.IOException;
import java.util.List;

public class WindowsWirelessMonitor implements WirelessMonitor
{
    private WindowsWirelessMonitor ()
    {
    }
    
    public static WindowsWirelessMonitor newInstance ()
    {
        return new WindowsWirelessMonitor();
    }
    
    @Override
    public boolean isConnected ()
    {
        return false;
    }
    
    @Override
    public String wirelessName ()
    {
        return "";
    }
    
    @Override
    public int linkQuality ()
    {
        return 0;
    }
    
    @Override
    public double signalLevel ()
    {
        return 0;
    }
    
    @Override
    public void updateData ()
    {
    }

    @Override
    public String getCommandOutput(List<String> command) throws IOException {
        return WirelessMonitor.super.getCommandOutput(command); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] stream() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
