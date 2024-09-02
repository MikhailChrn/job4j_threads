package ru.job4j.synch;

/**
 * Одна нить ищет файлы с подходящим именем.
 * Вторая нить берет эти файлы и читает.
 * <p>
 * Эта схема хорошо описывается шаблоном Producer-Consumer.
 * Однако есть один момент.
 * <p>
 * Когда первая нить заканчивает свою работу, потребители переходят в режим wait.
 * <p>
 * Изменить код, так, что бы потребитель завершал свою работу.
 * (Код нужно изменить в методе main.)
 */

public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();
    }
}
