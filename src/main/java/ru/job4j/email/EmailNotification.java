package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService executorPool;

    public EmailNotification() {
        this.executorPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    public void emailTo(User user) {
        this.executorPool.submit(new Runnable() {
            @Override
            public void run() {
                send(String.format("Notification %s to email %s", user.username(), user.email()),
                        String.format("Add a new event to %s", user.username()),
                        user.email()
                );
            }
        });
    }

    public void send(String subject, String body, String email) {
        /**
         * EMPTY
         */
    }

    public void close() {
        executorPool.shutdown();
        while (!executorPool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
