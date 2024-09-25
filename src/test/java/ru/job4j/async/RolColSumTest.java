package ru.job4j.async;

import static ru.job4j.async.RolColSum.*;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {
    private int[][] inputArray = {
            {1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3},
            {4, 4, 4, 4, 4},
            {5, 5, 5, 5, 5}
    };

    private Sums[] expResult = {
            new Sums(5, 15),
            new Sums(10, 15),
            new Sums(15, 15),
            new Sums(20, 15),
            new Sums(25, 15)
    };

    @Test
    void simple5x5SyncTest() {
        assertArrayEquals(expResult, sum(inputArray));
    }

    @Test
    void simple5x5AsyncTest() throws ExecutionException, InterruptedException {
        assertArrayEquals(expResult, asyncSum(inputArray));
    }
}