package com.supermacro;

import java.io.File;
import java.io.FileWriter;

public class LogManager {
    public static final String LOG_FILE = "log.txt";
    public static final String LOG_FORMAT = "%user% %action%.";

    public static void log(String user, String action){
        String log = LOG_FORMAT.replace("%user%", user)
                    .replace("%action%", action);
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            // write log to file
            FileWriter fw = new FileWriter(file, true);
            fw.write(log);
            fw.write("\n");
            fw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
