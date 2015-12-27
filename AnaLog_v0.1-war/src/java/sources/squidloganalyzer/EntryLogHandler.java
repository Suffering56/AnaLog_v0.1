package sources.squidloganalyzer;

import components.ProxyLogFacade;
import entity.ProxyLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EntryLogHandler implements Serializable {
    
    private static String squidLogFolder = "F:\\00_Development\\Java\\Projects\\EE\\AnaLog\\Analog_v0.1\\Analog_v0.1-war\\resources\\logs\\proxy.log";
    private int index;
    private Date timestamp;
    private BufferedReader reader;
    private ProxyLogFacade proxyLogFacade;
    
    public EntryLogHandler(ProxyLogFacade proxyLogFacade, List<ProxyLog> table) {
        this.proxyLogFacade = proxyLogFacade;
        if (table.isEmpty()) {
            index = 1;
            timestamp = new Date(0);
        }
        else {
            index = table.get(table.size()-1).getId() + 1;
            timestamp = table.get(table.size()-1).getSessiontimestamp();
        }
    }
    
    public void logExtraction() throws Exception {
        reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(squidLogFolder)), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            logRecording(line);
        }
    }
    
    private void logRecording(String line) {  
        ProxyLog entry;
        if ((entry = SquidLogParser.preParsing(line, index, timestamp)) != null) {
            if (proxyLogFacade.recordExistCheck(
                    entry.getSessiontimestamp(),
                    entry.getDuration(),
                    entry.getUrlAddress(),
                    entry.getTraffic())) {
                proxyLogFacade.persistEntry(entry);
                index++;
            }               
        }
    }
}
