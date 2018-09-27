package Baterei;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class LinuxBatteryState implements BatteryState
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final Pattern REMOVE_SPACE = Pattern.compile(" ");
    private static final String[] BATTERY_COMMAND = { "upower", "-d" };
    private String outputData;
    
    private LinuxBatteryState () throws IOException
    {
        updateBatteryCommandOutput();
    }
    
    private void updateBatteryCommandOutput () throws IOException
    {
        outputData = removeExtraData(getCommandOutput(List(BATTERY_COMMAND)));
    }
    
    public static LinuxBatteryState newInstance () throws IOException
    {
        return new LinuxBatteryState();
    }
    
    private String getProperty (String propertyName)
    {
        try
        {
            String data = new String(outputData);
            data = data.substring(data.indexOf(propertyName) + propertyName.length());
            data = data.substring(0, data.indexOf(System.lineSeparator()));
            return data;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public String batteryState ()
    {
        return getProperty("state:");
    }
    
    @Override
    public String timeToFull ()
    {
        return getProperty("timetofull:");
    }
    
    @Override
    public String timeToEmpty ()
    {
        return getProperty("timetoempty:");
    }
    
    @Override
    public String percentage ()
    {
        return getProperty("percentage:");
    }
    
    @Override
    public void updateData ()
    {
        try {
            updateBatteryCommandOutput();
        } catch (IOException ex) {
            Logger.getLogger(LinuxBatteryState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String removeExtraData (String output)
    {
        output = output.substring(output.indexOf("battery"));
        return REMOVE_SPACE.matcher(output).replaceAll("");
    }
    
    @Override
    public String toString ()
    {
        return "LinuxBatteryState{" + "outputData='" + outputData + '\'' + '}';
    }

    @Override
    public String getCommandOutput(List<String> command) throws IOException {
        return BatteryState.super.getCommandOutput(command); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] stream() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<String> List(String[] BATTERY_COMMAND) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
