/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sistem;

/**
 *
 * @author pacevil
 */
public class FileSystemItem
{
    private String name;
    private String size;
    private String used;
    private String available;
    private double usedPercentage;
    
    private FileSystemItem ()
    {
    }
    
    private FileSystemItem (String name, String size, String used, String available,
            double usedPercentage)
    {
        this.name = name;
        this.size = size;
        this.used = used;
        this.available = available;
        this.usedPercentage = usedPercentage;
    }
    
    /**
     * New instance file system item.
     *
     * @return the file system item
     */
    public static FileSystemItem newInstance ()
    {
        return new FileSystemItem();
    }
    

    public static FileSystemItem newInstance (String name, String size, String used,
            String available, double usedPercentage)
    {
        return new FileSystemItem(name, size, used, available, usedPercentage);
    }
    
    public String getName ()
    {
        return name;
    }
    
    public void setName (String name)
    {
        this.name = name;
    }
    
    public String getSize ()
    {
        return size;
    }
    
    public void setSize (String size)
    {
        this.size = size;
    }
    
    public String getUsed ()
    {
        return used;
    }
    
    public void setUsed (String used)
    {
        this.used = used;
    }
    
    public String getAvailable ()
    {
        return available;
    }
    
    public void setAvailable (String available)
    {
        this.available = available;
    }
    
    public double getUsedPercentage ()
    {
        return usedPercentage;
    }
    
    public void setUsedPercentage (double usedPercentage)
    {
        this.usedPercentage = usedPercentage;
    }
    
    @Override
    public String toString ()
    {
        return "FileSystemItem{" + "name='" + name + '\'' + ", size='" + size + '\'' + ", used='" +
                used + '\'' + ", available='" + available + '\'' + ", usedPercentage=" +
                usedPercentage + '}';
    }
}