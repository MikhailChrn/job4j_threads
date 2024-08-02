package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamExample {
    /**
     * void zero()
     * Однопоточный пример использования Stream API.
     */

    static void zero() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> multiplication = list.stream()
                .reduce((left, right) -> left * right);
        System.out.println(multiplication.get());
    }

    /**
     * void one()
     * Параллельный пример использования Stream API.
     */

    static void one() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = list.parallelStream();
        System.out.println(stream.isParallel());
        Optional<Integer> multiplication = stream.reduce((left, right) -> left * right);
        System.out.println(multiplication.get());
    }

    /**
     * void two()
     * Параллельный пример использования Stream API.
     */

    static void two() {
        IntStream parallel = IntStream.range(1, 100).parallel();
        System.out.println(parallel.isParallel());
        IntStream sequential = parallel.sequential();
        System.out.println(sequential.isParallel());
    }

    /**
     * void three()
     * Демонстрирует неустранимый побочный эффект метода peek() в многопоточной среде.
     */

    static void three() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.stream().parallel().peek(System.out::println).toList();
    }

    /**
     * void four()
     * Демонстрирует неустранимый побочный эффект метода peek() в многопоточной среде.
     */

    static void four() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.stream().parallel().forEach(System.out::println);
        list.stream().parallel().forEachOrdered(System.out::println);
    }

    public static void main(String[] args) {
        zero();
        System.out.println("-----");
        one();
        System.out.println("-----");
        two();
        System.out.println("-----");
        three();
        System.out.println("-----");
        four();
    }
}
