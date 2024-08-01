package ru.job4j.concurrent;

public class ThreadLocalDemo {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static class FirstThread extends Thread {
        @Override
        public void run() {
            ThreadLocalDemo.threadLocal.set("Это поток 1.");
            System.out.println(ThreadLocalDemo.threadLocal.get());
        }
    }

    public static class SecondThread extends Thread {
        @Override
        public void run() {
            ThreadLocalDemo.threadLocal.set("Это поток 2.");
            System.out.println(ThreadLocalDemo.threadLocal.get());
        }
    }

    public static void main(String[] args) {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        threadLocal.set("Это поток main.");
        System.out.println(threadLocal.get());
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
