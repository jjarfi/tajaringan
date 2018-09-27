/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scan;

import java.util.ArrayList;

/**
 *
 * @author pacevil
 */
public class IPrange {
    private javax.swing.JFrame frame;
    private String from = "";
    private String to = "";
    private int[] ip1 = {0,0,0,0};//holds the numeric ip
    private int[] ip2 = {0,0,0,0};
    ArrayList<String> range;
    String subnet;
    String netType; 
    
    public IPrange()
    {
        range = new ArrayList<>();
    }
    //setters
    public void setFromip(String from)
    {
        this.from = from;
    }
    public void setToip(String to)
    {
        this.to = to;
    }
    //convert from String ip to int[] ip
    public void StrToIP()
    {
        if(from == "" || from == null)
        {
            this.showEnterIpMessage();
            return;
        }

        String[] temp1 = from.split("\\.");
        String[] temp2 = to.split("\\.");
        for(int i = 0; i < temp1.length; i++)
        {
            ip1[i] = Integer.parseInt(temp1[i]);
            ip2[i] = Integer.parseInt(temp2[i]);
        }
    }
    /**
     * identifying type of network class A, B, C
     */
    public void calcNetwork()
    {
        String addr = "";
        if((ip1[1] != ip2[1]) && ( ip1[1] < ip2[1]))
        {
            subnet = ip1[0] + ".0.0.";
            for(int i = ip1[1]; i <= ip2[1]; i++)
            {
                for(int j = 0; j <= 255; j++)
                {
                    for(int k = 0; k <= 255; k++)
                    {
                        addr = ip1[0]+"."+i+"."+j+"."+k;
                        range.add(addr);
                    }
                }
            }
        }
        //adding class B network to Array "range"
        else if((ip1[2] != ip2[2]) && (ip1[2] < ip2[2]))
        {
            subnet = ip1[0] + "." + ip1[1] + ".0.";
            for(int i = ip1[2]; i <= ip2[2]; i++)
            {
                for(int j = 0; j <= 255; j++)
                {
                    addr = ip1[0]+"."+ip1[1]+"."+i+"."+j;
                    range.add(addr);
                }
            }
        }
        //adding class C network to Array "range"
        else if((ip1[3] != ip2[3]) && (ip1[3] < ip2[3]))
        {
            subnet = ip1[0] + "." + ip1[1] + "." + ip1[2] + ".";
            for(int i = ip1[3]; i <= ip2[3]; i++)
            {
                addr = ip1[0]+"."+ip1[1]+"."+ip1[2]+"."+i;
                range.add(addr);
            }
        }
        else System.out.println("Enter correct address range!");
   }
    public ArrayList<String> getRange()
    {
        return range;
    }
    
    public String getSubnet()
    {
        return subnet;
    }

    private void showEnterIpMessage() {
        
    }
    
    
}
