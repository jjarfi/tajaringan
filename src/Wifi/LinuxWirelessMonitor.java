package Wifi;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class LinuxWirelessMonitor implements WirelessMonitor
{
    private static final int STRONGEST_SIGNAL = 30;
    private static final double SIGNAL_PERCENT_RATE = 1.666;

    private static List<String> List(String iwconfig) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private final Logger logger = Logger.getLogger(getClass().getName());
    public static final List<String> WIRELESS_COMMAND = List("iwconfig");
    private String data;
    
    private LinuxWirelessMonitor ()
    {
        updateData();
    }
    
    public static LinuxWirelessMonitor newInstance ()
    {
        return new LinuxWirelessMonitor();
    }
    
    @Override
    public boolean isConnected ()
    {
        try
        {
            String dataCopy = new String(data);
            String delimiter = "ESSID:";
            dataCopy = dataCopy.substring(dataCopy.indexOf(delimiter) + delimiter.length());
            dataCopy = dataCopy.substring(0, '\n').trim();
            return !"off/any".equals(dataCopy);
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return false;
        }
    }
    
    @Override
    public String wirelessName ()
    {
        if (isConnected())
        {
            String dataCopy = new String(data);
            String delimiter = "ESSID:\"";
            dataCopy = dataCopy.substring(dataCopy.indexOf(delimiter) + delimiter.length());
            dataCopy = dataCopy.substring(0, dataCopy.indexOf('\"')).trim();
            return dataCopy;
        }
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
        if (isConnected())
        {
            String dataCopy = new String(data);
            return computeSignalStrengthPercent(Double.parseDouble(getDbmLevel(dataCopy)));
        }
        return 0;
    }
    
    private double computeSignalStrengthPercent (double signalDb)
    {
        signalDb -= STRONGEST_SIGNAL;
        signalDb *= SIGNAL_PERCENT_RATE;
        signalDb = 100 - signalDb;
        signalDb /= 100;
        return signalDb;
    }
    
    private String getDbmLevel (String dataCopy)
    {
        String delimiter = "Signal level=-";
        dataCopy = dataCopy.substring(dataCopy.indexOf(delimiter) + delimiter.length());
        dataCopy = dataCopy.substring(0, dataCopy.indexOf("dBm")).trim();
        return dataCopy;
    }
    
    @Override
    public void updateData ()
    {
        try
        {
            data = getCommandOutput(WIRELESS_COMMAND);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            data = "";
        }
    }
    
    @Override
    public String toString ()
    {
        return "LinuxWirelessMonitor{" + "data='" + data + '\'' + '}';
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
