package Baterei;



public interface BatteryState extends SystemCommandHandler
{
    String batteryState ();
    
    String timeToFull ();
    
    String timeToEmpty ();
    
    String percentage ();
    
    void updateData ();
}
