package com.supermacro;

import java.io.File;
import java.io.FileOutputStream;

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
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(log.getBytes());
            fos.write("\n".getBytes());
            fos.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
    }
}
