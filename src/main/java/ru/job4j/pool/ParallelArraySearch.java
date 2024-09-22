package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelArraySearch<V> {

    public int getIndex(V[] array, V value) {
        return new ForkJoinPool().invoke(new ArraySearchTask(array, value, 0, array.length));
    }

    private class ArraySearchTask extends RecursiveTask<Integer> {

        private V[] array;

        private V value;

        private final int from;

        private final int to;

        public ArraySearchTask(V[] array, V value, int from, int to) {
            this.array = array;
            this.value = value;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Integer compute() {
            Integer result = -1;

            if (this.to - this.from <= 10) {
                return getIndex(array, value, this.from, this.to);
            }

            int middle = (this.from + this.to) / 2;

            ArraySearchTask leftSearchTask =
                    new ArraySearchTask(this.array, this.value, this.from, middle);
            ArraySearchTask rightSearchTask =
                    new ArraySearchTask(this.array, this.value, middle + 1, this.to);

            leftSearchTask.fork();
            rightSearchTask.fork();

            Integer left = leftSearchTask.join();
            Integer right = rightSearchTask.join();

            return left != -1 ? left : right;
        }

        private int getIndex(V[] array, V value, int from, int to) {
            int result = -1;
            for (int i = from; i < to; i++) {
                if (value.equals(array[i])) {
                    result = i;
                    break;
                }
            }
            return result;
        }
    }
}
