package ru.job4j.concurrent;

import java.io.*;
import java.net.URL;

/**
 * Package size is measured in bytes;
 * Download speed is measured in [byte/ms];
 */

public class Wget implements Runnable {
    static final String FILE_DESTINATION = "test.txt";
    static final int PACKAGE_SIZE = 1_024;

    private final String url;
    private final int maxBytePerMSSpeed;

    public Wget(String url, int speed) {
        this.url = url;
        this.maxBytePerMSSpeed = speed;
    }

    @Override
    public void run() {
        try {
            this.fileDownload(this.url, this.maxBytePerMSSpeed);
        } catch (IOException ex) {
            try {
                throw new IOException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getNameFileFromUrl(String url) {
        return FILE_DESTINATION;
    }

    private static boolean isValidUrl(String url) {
        return true;
    }

    private static boolean isValidSpeed(String speed) {
        return true;
    }

    private static void isValidArguments(String[] args) {
        if (args.length > 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        if (!isValidUrl(args[0])) {
            throw new IllegalArgumentException("Invalid URL value");
        }
        if (!isValidSpeed(args[1])) {
            throw new IllegalArgumentException("Invalid speed value");
        }
    }

    private void fileDownload(String url, int speed) throws IOException {
        long startAt = System.currentTimeMillis();
        File file = new File(getNameFileFromUrl(url));
        try (InputStream input = new URL(url).openStream();
             OutputStream output = new FileOutputStream(file)) {
            System.out.println("Open connection: "
                    + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[PACKAGE_SIZE];
            int bytesRead;
            double factBytePerMSDownloadSpeed;
            double packagePauseTimeMS;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                factBytePerMSDownloadSpeed = PACKAGE_SIZE * 1_000_000 / (System.nanoTime() - downloadAt);
                packagePauseTimeMS = PACKAGE_SIZE * (1 - this.maxBytePerMSSpeed / factBytePerMSDownloadSpeed);
                if (packagePauseTimeMS > 0) {
                    try {
                        Thread.sleep((int) packagePauseTimeMS);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Фактическое скорость при скачивании пакета : "
                        + factBytePerMSDownloadSpeed + " [byte/ms]");
                System.out.println("Фактическая пауза для пакета : "
                        + packagePauseTimeMS + " [ms]");
            }
        } catch (IOException ex) {
            throw new IOException();
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            isValidArguments(args);
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        } catch (InterruptedException ex) {
            throw new InterruptedException(ex.getMessage());
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
