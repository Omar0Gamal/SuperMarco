package com.supermacro;

import java.io.File;
import java.io.FileOutputStream;

public class LogManager {
    public static final String LOG_FILE = "log.txt";
    public static final String TIME_FORMAT = "[yyyy-MM-dd HH:mm:ss]";
    public static final String LOG_FORMAT = "%time%:%user% %action% %target%.";

    public static void log(String user, String action, String target){
        String timestamp = new java.text.SimpleDateFormat(TIME_FORMAT)
                .format(new java.util.Date());
        String log = LOG_FORMAT.replace("%time%", timestamp)
                .replace("%user%", user)
                .replace("%action%", action)
                .replace("%target%", target);
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            // write log to file
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(log.getBytes());
            fos.write("\n".getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
