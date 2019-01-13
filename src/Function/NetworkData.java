package Function;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pacevil
 */
public class NetworkData {

    private static Map<String, Long> rxCurrentMap = new HashMap<String, Long>();
    private static Map<String, List<Long>> rxChangeMap = new HashMap<String, List<Long>>();
    private static Map<String, Long> txCurrentMap = new HashMap<String, Long>();
    private static Map<String, List<Long>> txChangeMap = new HashMap<String, List<Long>>();
    private static Sigar sigar;

    private static void newMetrickThread() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param s
     * @throws InterruptedException
     * @throws SigarException
     * 
     */
    public NetworkData(Sigar s) throws SigarException, InterruptedException {
        sigar = s;
        getMetric();

        Thread.sleep(1000);     
    }



    public static String networkInfo() throws SigarException {
        String info = sigar.getNetInfo().toString();
        info += "\n"+ sigar.getNetInterfaceConfig().toString();
        return info;
    }

    public static String getDefaultGateway() throws SigarException {
        return sigar.getNetInfo().getDefaultGateway();
    }

    public static void newMetricThread() throws SigarException, InterruptedException {
        while (true) {
            Long[] m = getMetric();
            long totalrx = m[0];
            long totaltx = m[1];
            System.out.print("totalrx(download): ");
            System.out.println("\t" + Sigar.formatSize(totalrx));
            System.out.print("totaltx(upload): ");
            System.out.println("\t" + Sigar.formatSize(totaltx));
            System.out.println("-----------------------------------");
            Thread.sleep(1000);
        }

    }

    public static Long[] getMetric() throws SigarException {
        for (String ni : sigar.getNetInterfaceList()) {
            // System.out.println(ni);
            NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
            NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ni);
            String hwaddr = null;
            if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())) {
                hwaddr = ifConfig.getHwaddr();
            }
            if (hwaddr != null) {
                long rxCurrenttmp = netStat.getRxBytes();
                saveChange(getRxCurrentMap(), getRxChangeMap(), hwaddr, rxCurrenttmp, ni);
                long txCurrenttmp = netStat.getTxBytes();
                saveChange(getTxCurrentMap(), getTxChangeMap(), hwaddr, txCurrenttmp, ni);
            }
        }
        long totalrx = getMetricData(getRxChangeMap());
        long totaltx = getMetricData(getTxChangeMap());
        getRxChangeMap().values().forEach((l) -> {
            l.clear();
        });
        getTxChangeMap().values().forEach((l) -> {
            l.clear();
        });
        return new Long[] { totalrx, totaltx };
    }

    private static long getMetricData(Map<String, List<Long>> rxChangeMap) {
        long total = 0;
        for (Entry<String, List<Long>> entry : rxChangeMap.entrySet()) {
            int average = 0;
            for (Long l : entry.getValue()) {
                average += l;
            }
            total += average / entry.getValue().size();
        }
        return total;
    }

    private static void saveChange(Map<String, Long> currentMap,
            Map<String, List<Long>> changeMap, String hwaddr, long current,
            String ni) {
        Long oldCurrent = currentMap.get(ni);
        if (oldCurrent != null) {
            List<Long> list = changeMap.get(hwaddr);
            if (list == null) {
                list = new LinkedList<>();
                changeMap.put(hwaddr, list);
            }
            list.add((current - oldCurrent));
        }
        currentMap.put(ni, current);
    }

    /**
     * @return the rxCurrentMap
     */
    public static Map<String, Long> getRxCurrentMap() {
        return rxCurrentMap;
    }

    /**
     * @param aRxCurrentMap the rxCurrentMap to set
     */
    public static void setRxCurrentMap(Map<String, Long> aRxCurrentMap) {
        rxCurrentMap = aRxCurrentMap;
    }

    /**
     * @return the rxChangeMap
     */
    public static Map<String, List<Long>> getRxChangeMap() {
        return rxChangeMap;
    }

    /**
     * @param aRxChangeMap the rxChangeMap to set
     */
    public static void setRxChangeMap(Map<String, List<Long>> aRxChangeMap) {
        rxChangeMap = aRxChangeMap;
    }

    /**
     * @return the txCurrentMap
     */
    public static Map<String, Long> getTxCurrentMap() {
        return txCurrentMap;
    }

    /**
     * @param aTxCurrentMap the txCurrentMap to set
     */
    public static void setTxCurrentMap(Map<String, Long> aTxCurrentMap) {
        txCurrentMap = aTxCurrentMap;
    }

    /**
     * @return the txChangeMap
     */
    public static Map<String, List<Long>> getTxChangeMap() {
        return txChangeMap;
    }

    /**
     * @param aTxChangeMap the txChangeMap to set
     */
    public static void setTxChangeMap(Map<String, List<Long>> aTxChangeMap) {
        txChangeMap = aTxChangeMap;
    }

}