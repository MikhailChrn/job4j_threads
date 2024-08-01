package ru.job4j.concurrent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Package size is measured in one Kbyte;
 * Download speed is measured in [Kbyte/s];
 */

public class Wget implements Runnable {
    static final int PACKAGE_SIZE = 1_024;

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            this.fileDownload(this.url, this.speed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getNameFileFromUrl(String url) {
        String[] str = url.split("/");
        return str[str.length - 1];
    }

    static int getDelayMS(long downLoadNano) {
        return (int) ((1 * Math.pow(10, 9) - downLoadNano) / Math.pow(10, 6));
    }

    private static boolean isAvailableUrl(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("HEAD");
        return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    private static boolean isValidSpeed(String speed) {
        int sp = Integer.parseInt(speed);
        return 0 < sp && sp < 1000;
    }

    private static void isValidArguments(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException("There are not enough arguments");
        }
        if (!isAvailableUrl(args[0])) {
            throw new IllegalArgumentException("Invalid URL value");
        }
        if (!isValidSpeed(args[1])) {
            throw new IllegalArgumentException("Invalid speed value. Speed should be in the range 0...999 [Kb/s]");
        }
    }

    private void fileDownload(String url, int speed) throws IOException {
        try (InputStream input = new URL(url).openStream();
             OutputStream output = new FileOutputStream(new File(getNameFileFromUrl(url)))) {
            long startDownloadAt = System.nanoTime();
            byte[] dataBuffer = new byte[PACKAGE_SIZE];
            int bytesRead;
            int counter = 0;
            long downloadAt;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                counter++;
                if (counter == this.speed && getDelayMS(System.nanoTime() - downloadAt) > 0) {
                    try {
                        Thread.sleep(getDelayMS(System.nanoTime() - downloadAt));
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if (counter > speed) {
                    counter = 0;
                }
            }
            System.out.printf("Скорость загрузки составила : %d [Kb/s] \n", this.speed);
            System.out.printf("Время загрузки составило : %d [ms]",
                    (int) ((System.nanoTime() - startDownloadAt) / Math.pow(10, 6)));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        isValidArguments(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
