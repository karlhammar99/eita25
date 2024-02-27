package projekt2;

import java.io.*;
import java.text.SimpleDateFormat;

public class Logger {
    private static final String folderPath = "logs/";

    public static void log(String s) {
        File folder = new File(folderPath.replace("/", ""));
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(folderPath + "log.txt", true);
            String timeStamp =
                    new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            writer.write("\n" + timeStamp + " " + s);
            System.out.println("should log!");
            System.out.println(timeStamp);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
