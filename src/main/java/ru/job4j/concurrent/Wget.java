package ru.job4j.concurrent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Package size is 1[Kb] = 1024[byte];
 * Download speed is measured in [Byte/s];
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

    @Deprecated
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
        return 0 < sp && sp < 1_000_000;
    }

    private static void isValidArguments(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException("There are not enough arguments");
        }
        if (!isAvailableUrl(args[0])) {
            throw new IllegalArgumentException("Invalid URL value");
        }
        if (!isValidSpeed(args[1])) {
            throw new IllegalArgumentException("Invalid speed value. Download speed should be in the range 0...999999 [Byte/s]");
        }
    }

    private void fileDownload(String url, int speed) throws IOException {
        try (InputStream input = new URL(url).openStream();
             OutputStream output = new FileOutputStream(new File(getNameFileFromUrl(url)))) {
            byte[] dataBuffer = new byte[PACKAGE_SIZE];
            int bytesRead;
            int delay;
            int counter = 0;
            long startAt = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                counter += bytesRead;
                output.write(dataBuffer, 0, bytesRead);
                if (counter >= this.speed && (System.currentTimeMillis() - startAt) < Math.pow(10, 3)) {
                    delay = (int) ((1 * Math.pow(10, 3) - (System.currentTimeMillis() - startAt)));
                    try {
                        System.out.println(delay + " ms");
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if (counter > speed) {
                    counter = 0;
                    startAt = System.currentTimeMillis();
                }
            }
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
