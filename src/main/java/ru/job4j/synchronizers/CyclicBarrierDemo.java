package ru.job4j.synchronizers;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new FerryBoat());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }

    public static class FerryBoat implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println("Паром переправил автомобили!");
            } catch (InterruptedException e) {
            }
        }
    }

    public static class Car implements Runnable {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Автомобиль №%d подъехал к паромной переправе.\n", carNumber);
                BARRIER.await();
                System.out.printf("Автомобиль №%d продолжил движение.\n", carNumber);
            } catch (Exception e) {
            }
        }
    }
}
