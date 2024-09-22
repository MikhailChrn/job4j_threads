package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelMergeSortTest {
    @Test
    void oddNumberSimpleTest() {
        int[] input = new int[] {6, 7, 4, 5, 2, 3, 1};
        int[] result = ParallelMergeSort.sort(input);
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertArrayEquals(result, expected);
    }

    @Test
    void evenNumberSimpleTest() {
        int[] input = new int[] {6, 4, 5, 2, 3, 1};
        int[] result = ParallelMergeSort.sort(input);
        int[] expected = new int[] {1, 2, 3, 4, 5, 6};
        assertArrayEquals(result, expected);
    }

}