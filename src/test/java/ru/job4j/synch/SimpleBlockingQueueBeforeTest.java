package ru.job4j.synch;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Random;

class SimpleBlockingQueueBeforeTest {
    static void produce(String title, SimpleBlockingQueue<Integer> queue, int delay) throws InterruptedException {
        System.out.printf("[%s] started \n", title);
        while (true) {
            Integer i = new Random().nextInt(100);
            System.out.printf("[%s] produce : %d \n", title, i);
            queue.offer(i);
            Thread.sleep(delay);
        }
    }

    static void consume(String title, SimpleBlockingQueue<Integer> queue, int delay) throws InterruptedException {
        System.out.printf("[%s] started \n", title);
        while (true) {
            Integer i = queue.poll();
            System.out.printf("[%s] consume : %d \n", title, i);
            Thread.sleep(delay);
        }
    }

    @Test
    @Disabled
    void simpleFirstSimpleCase() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);

        Thread p1 = new Thread(
                () -> {
                    try {
                        produce(Thread.currentThread().getName(), queue, 150);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, "Producer #1"
        );

        Thread c1 = new Thread(
                () -> {
                    try {
                        consume(Thread.currentThread().getName(), queue, 500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, "Consumer #1"
        );

        Thread c2 = new Thread(
                () -> {
                    try {
                        consume(Thread.currentThread().getName(), queue, 500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, "Consumer #2"
        );

        Thread c3 = new Thread(
                () -> {
                    try {
                        consume(Thread.currentThread().getName(), queue, 500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, "Consumer #3"
        );

        p1.start();
        Thread.sleep(1000);
        c1.start();
        c2.start();
        c3.start();
        Thread.sleep(10000);
        Thread.interrupted();
    }
}