/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pacelinux
 */
public class Sniffer{
    private String ipaddr; 
    private int index;
    public void SetIndex(int index){
        this.index=index;
    }
    public int GetIndex(){
        return this.index;
    }
    public void setip(String ip){  
        this.ipaddr=ip;
    }
  

    public static boolean possiedeip(NetworkInterface netint){
        boolean validip = false;

        String ip2=null;
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for(InetAddress inetAddress : Collections.list(inetAddresses)){   
                ip2= inetAddress.getHostAddress();
             
            if(!ip2.isEmpty() && ip2.length()<=15 && !ip2.equals("127.0.0.1") && !netint.getDisplayName().contains("Loopback")){
                validip=true;  
                break;
            }
           
        }
        
        return validip;
    }
    

   public static String getip(String name){
       String ip=null;
       NetworkInterface interfacciascelta=null;
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
          while(nets.hasMoreElements()){
               NetworkInterface netint= nets.nextElement();
               if(name.equals(netint.getDisplayName())){
                   interfacciascelta=netint;
                    break;
                }
          }
               
            
       //   System.out.println( interfacciascelta.getHardwareAddress().toString());
           
             Enumeration<InetAddress> inetAddress = interfacciascelta.getInetAddresses(); 

             while(inetAddress.hasMoreElements()){
                InetAddress inetA= inetAddress.nextElement();
                
                if(inetA.getHostAddress().length()<=15 && !inetA.getHostAddress().equals("127.0.0.1")){
                ip=inetA.getHostAddress();
                break;
                }
            }
               
               
        } catch (SocketException ex) {
            Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return ip;
   } 
 

    public static String getmacAddr(NetworkInterface net){
        byte[] val;
        String val2 = "";
        boolean windows = false;
        StringBuilder build = new StringBuilder();
        try {
            val = net.getHardwareAddress(); 
           
            if(val!=null){
                for(byte b: val){
                      if(System.getProperty("os.name").startsWith("Windows")){ 
                             build.append(String.format("%02x",b)+"-"); 
                             windows=true;
                      }else{ 
                              build.append(String.format("%02x",b)+":");  
                              
                               
                      }
                       
                        
                     
                 
                }
      
            }
            if(windows){  //se windows leva l'ultimo trattino
                  build.deleteCharAt(build.lastIndexOf("-"));
            }else{ //se linux leve i : 
                  build.deleteCharAt(build.lastIndexOf(":"));
            }
            val2 = build.toString();
        } catch (SocketException ex) {
            Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        return val2;  //restituito il mac sottoforma di stringa
    }
  

            }
  
