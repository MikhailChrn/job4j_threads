package ru.job4j.async;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        int rowSum = 0;
        int colSum = 0;

        for (int i = 0; i < result.length; i++) {
            rowSum = Arrays.stream(matrix[i]).sum();
            int finalCol = i;
            colSum = IntStream.range(0, result.length)
                    .map((row) -> matrix[row][finalCol])
                    .sum();

            result[i] = new Sums(rowSum, colSum);
        }

        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        CompletableFuture<Integer> rowSum;
        CompletableFuture<Integer> columnSum;

        for (int i = 0; i < result.length; i++) {
            rowSum = rowSumFutureTask(matrix, i);
            columnSum = columnSumFutureTask(matrix, i);

            result[i] = new Sums(rowSum.get(), columnSum.get());
        }

        return result;
    }

    public static CompletableFuture<Integer> rowSumFutureTask(int[][] matrix, int row) {
        return CompletableFuture.supplyAsync(() -> {
            return Integer.valueOf(Arrays.stream(matrix[row]).sum());
        });
    }

    public static CompletableFuture<Integer> columnSumFutureTask(int[][] matrix, int column) {
        return CompletableFuture.supplyAsync(() -> {
            return Integer.valueOf(
                    IntStream.range(0, matrix.length)
                            .map((row) -> matrix[row][column])
                            .sum());
        });
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }
}
