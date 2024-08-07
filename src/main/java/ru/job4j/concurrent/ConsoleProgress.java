package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private final char[] process = new char[]{'-', '\\', '|', '/'};

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            i = i > process.length - 1 ? 0 : i;
            System.out.printf("\rLoading %c", process[i++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
