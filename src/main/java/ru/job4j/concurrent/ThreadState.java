package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " Работа начата");
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(
                () -> {
                }
        );
        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + second.getState());

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + " " + first.getState());
            System.out.println(second.getName() + " " + second.getState());
        }

        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + second.getState());

        System.out.println(Thread.currentThread().getName() + " Работа завершена");
    }
}
