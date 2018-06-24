package itsu.java.tinycontroller.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static void makeConfig() {
        try {
            File root = new File("./TinyController/");
            root.mkdirs();
            if (!new File("./TinyController/Config.properties").exists()) writeConfig(new File("./TinyController/Config.properties"), Utils.class.getClassLoader().getResourceAsStream("Config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> readConfig() {
        try {
            File file = new File("./TinyController/Config.properties");
            if (!file.exists()) {
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            temp = br.readLine();
            while (temp != null) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(temp);
                temp = br.readLine();
            }
            br.close();

            String data = stringBuilder.toString();
            Map<String, Integer> result = new HashMap<>();

            String[] split = data.split("\n");
            for (String str : split) {
                String[] keyAndValue = str.split("=");
                result.put(keyAndValue[0], Integer.parseInt(keyAndValue[1]));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeConfig(int horizontal, int vertical, int distance) throws IOException {
        String data = "HorizontalSensitivity=" + horizontal + "\nVerticalSensitivity=" + vertical + "\nDistance=" + distance;
        InputStream content = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        FileOutputStream stream = new FileOutputStream(new File("./TinyController/Config.properties"));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = content.read(buffer)) != -1) {
            stream.write(buffer, 0, length);
        }
        stream.close();
        content.close();
    }

    public static void writeConfig(File file, InputStream content) throws IOException {
        FileOutputStream stream = new FileOutputStream(new File("./TinyController/Config.properties"));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = content.read(buffer)) != -1) {
            stream.write(buffer, 0, length);
        }
        stream.close();
        content.close();
    }

}
