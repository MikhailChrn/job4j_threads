package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {
    public static final int NUMBER_OF_TASKS = 1000;

    public static final int NUMBER_OF_THREADS
            = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks
            = new SimpleBlockingQueue<>(NUMBER_OF_TASKS / 10);

    public ThreadPool() {
        IntStream.range(0, NUMBER_OF_THREADS).forEach((i) -> {
            threads.add(new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                Runnable runnable = tasks.poll();
                                if (runnable != null) {
                                    runnable.run();
                                }
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            ));
            threads.get(i).start();
        });
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(thread -> thread.interrupt());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();

        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            threadPool.work(
                    () -> System.out.println(
                            Thread.currentThread().getName() + " working on task"
                    )
            );
        }

        threadPool.shutdown();
    }
}
