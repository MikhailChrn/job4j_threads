package ru.job4j.concurrent;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class FileDownload {
    public static void main(String[] args) throws Exception {
        long startAt = System.currentTimeMillis();
        File file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (InputStream input = new URL(url).openStream();
             OutputStream output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                System.out.println("Read 512 bytes : " + (System.nanoTime() - downloadAt) + " nano.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Files.size(file.toPath()) + " bytes");
    }
}
