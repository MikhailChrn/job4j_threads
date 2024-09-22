package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort extends RecursiveTask<int[]> {

    private final int[] array;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }

    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {array[from]};
        }
        int middle = (from + to) / 2;

        ParallelMergeSort leftSort = new ParallelMergeSort(array, from, middle);
        ParallelMergeSort rightSort = new ParallelMergeSort(array, middle + 1, to);

        leftSort.fork();
        rightSort.fork();

        int[] left = leftSort.join();
        int[] right = rightSort.join();

        return merge(left, right);
    }

    public static int[] merge(int[] left, int[] right) {
        int leftI = 0;
        int rightI = 0;
        int resultI = 0;
        int[] result = new int[left.length + right.length];
        while (resultI != result.length) {
            if (leftI == left.length) {
                result[resultI++] = right[rightI++];
            } else if (rightI == right.length) {
                result[resultI++] = left[leftI++];
            } else if (left[leftI] <= right[rightI]) {
                result[resultI++] = left[leftI++];
            } else {
                result[resultI++] = right[rightI++];
            }
        }
        return result;
    }
}
