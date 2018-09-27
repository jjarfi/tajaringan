/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author pacevil
 */
public class Net {

    Scanner sc = new Scanner(System.in);
    InetAddress[] addr;

    private void netw() {
        try {
            InetAddress inetHost = InetAddress.getByName("www.manthandesai.co.cc");
            String hostName = inetHost.getHostName();
            for (;;) {
                int[] addr1 = new int[4];
                byte[] addr2 = new byte[4];
                System.out.println("Enter ip address");
                for (int i = 0; i < 4; i++) {
                    addr1[i] = sc.nextInt();
                    addr2[i] = ((byte) addr1[i]);
                }
                InetAddress it = InetAddress.getByAddress(addr2);
                System.out.println("ip \t" + Arrays.toString(it.getAddress()));
                System.out.println("name \t" + it.getHostName());
                System.out.println("Host  addr \t" + it.getHostAddress());
                System.out.println("cannonical host name \t" + it.getCanonicalHostName());
                System.out.println("is multicast \t" + it.isMulticastAddress());
                System.out.println("SiteLocal" + it.isSiteLocalAddress());
                addr = InetAddress.getAllByName(it.getHostName());
                for (int i = 0; i < addr.length; i++) {
                    System.out.println(addr[i]);
                }
                System.out.println("enter 1 to exit");
                if (sc.nextInt() == 1) {
                    break;
                }
            }

        } catch (UnknownHostException e) {

        }
    }

}
