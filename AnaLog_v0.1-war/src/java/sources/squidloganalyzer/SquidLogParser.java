package sources.squidloganalyzer;

import entity.ProxyLog;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Suffering
 */
public class SquidLogParser{
    private static Pattern LOG_ENTRY_PATTERN = Pattern.compile(""
            + "([\\d]+)\\.([\\d]+)\\p{Space}+"      //timestamp                 (1,2)
            + "(\\d+)\\p{Space}+"                   //dutation                  (3)
            + "([\\d\\.]+)\\p{Space}+"              //clientAddress             (4)
            + "([^\\/]+)\\/(\\d+)\\p{Space}+"       //cacheEvent + ResultCode   (5,6)
            + "(\\d+)\\p{Space}+"                   //traffic                   (7)
            + "(\\S+)\\p{Space}+"                   //RequestMetod              (8)
            + "(\\S+)\\p{Space}+"                   //URL                       (9)
            + "(\\S+)\\p{Space}+"                   //user                      (10)
            + "(\\S+)\\p{Space}+"                   //hierarchyCode             (11)
            + "(\\S+)");                            //contentType               (12) 


    public static ProxyLog preParsing(String line, int index, Date lastTimeStamp) {
        Matcher matcher = LOG_ENTRY_PATTERN.matcher(line.trim());        
        if(matcher.matches()){
            Date currentTimeStamp = new Date(Long.valueOf(matcher.group(1)) * 1000 + Long.valueOf(matcher.group(2)));
            if (currentTimeStamp.getTime() <= lastTimeStamp.getTime()) {
                System.out.println("RemoteUserId= " + index);
                return parsing(matcher, index, currentTimeStamp, "RemoteUser");
            } 
            else {
                System.out.println("DefaultUserId= " + index);
                return parsing(matcher, index, currentTimeStamp, "DefaultUser");
            }  
        }
        else {
            return null;
        }
    }
    
    private static String pruningURL (String url) {
        if (url.length() >= 30) {
            return url.substring(0, 30);
        }
        else {
            return url;
        }
    }
    
    private static ProxyLog parsing (Matcher matcher, int index, Date currentTimeStamp, String clientName) {
        ProxyLog entry = new ProxyLog();
        entry.setId(index);
        entry.setSessiontimestamp(currentTimeStamp);
        entry.setClientaddress(matcher.group(4));
        entry.setClientname(clientName);
        entry.setUrlAddress(pruningURL(matcher.group(9)));
        entry.setDuration(Integer.valueOf(matcher.group(3)));
        entry.setTraffic(Integer.valueOf(matcher.group(7)));
        entry.setContenttype(matcher.group(12));
        entry.setSelected("false");
        return entry;
    }
}
