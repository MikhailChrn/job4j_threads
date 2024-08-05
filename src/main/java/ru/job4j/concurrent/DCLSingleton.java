package ru.job4j.concurrent;

public class DCLSingleton {
    private static volatile DCLSingleton instance;

    private DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        DCLSingleton dclSingleton = DCLSingleton.getInstance();
    }
}
