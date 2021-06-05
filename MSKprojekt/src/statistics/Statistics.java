package statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Statistics {
    public static int average(HashMap<Integer, StatisticsEntry> serviceHistory) {
        if (serviceHistory.size() == 0) {
            return 0;
        }
        int sumOfEntryies = 0;
        Iterator hmIterator = serviceHistory.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            Integer key = (Integer) mapElement.getKey();
            StatisticsEntry stat = (StatisticsEntry) mapElement.getValue();
            sumOfEntryies += stat.repairTime - stat.callTime;
        }
        return sumOfEntryies / serviceHistory.size();
    }
    public static int standardDeviation(HashMap<Integer, StatisticsEntry> serviceHistory) {
        if (serviceHistory.size() == 0) {
            return 0;
        }
        double sumOfEntryies = 0;
        int avg = Statistics.average(serviceHistory);
        Iterator hmIterator = serviceHistory.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            Integer key = (Integer) mapElement.getKey();
            StatisticsEntry stat = (StatisticsEntry) mapElement.getValue();
            double dif = (stat.repairTime - stat.callTime) - avg;
            sumOfEntryies += Math.pow( dif, 2.0 );
        }
        return (int) Math.sqrt( sumOfEntryies / serviceHistory.size() );
    }
}
