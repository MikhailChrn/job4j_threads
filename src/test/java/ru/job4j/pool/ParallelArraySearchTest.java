package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelArraySearchTest {

    @Test
    void simpleArraySearchLess10ElementsTest() {
        Integer[] input = new Integer[] {6, 7, 4, 5, 2, 3, 1};
        ParallelArraySearch<Integer> parallelArraySearch = new ParallelArraySearch<>();
        Integer result = parallelArraySearch.getIndex(input, 1);
        Integer expected = 6;
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void simpleArraySearchLess10ElementsTestValueNotFound() {
        Integer[] input = new Integer[] {6, 7, 4, 5, 2, 3, 1};
        ParallelArraySearch<Integer> parallelArraySearch = new ParallelArraySearch<>();
        Integer result = parallelArraySearch.getIndex(input, 99);
        Integer expected = -1;
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void simpleArraySearchMore10ElementsTest() {
        Integer[] input = new Integer[] {19, 18, 17, 16, 15, 14, 13, 12, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        ParallelArraySearch<Integer> parallelArraySearch = new ParallelArraySearch<>();
        Integer result = parallelArraySearch.getIndex(input, 8);
        Integer expected = 10;
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void simpleArraySearchMore10ElementsTestValueNotFound() {
        Integer[] input = new Integer[] {19, 18, 17, 16, 15, 14, 13, 12, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        ParallelArraySearch<Integer> parallelArraySearch = new ParallelArraySearch<>();
        Integer result = parallelArraySearch.getIndex(input, 99);
        Integer expected = -1;
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void simpleArraySearch1000ElementsTest() {
        Integer[] input = new Integer[1000];
        Arrays.setAll(input, i -> (int) (Math.random() * 100));

        ParallelArraySearch<Integer> parallelArraySearch = new ParallelArraySearch<>();

        Integer expected = (int) (Math.random() * 1000);
        input[expected] = -99;

        Integer result = parallelArraySearch.getIndex(input, -99);

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void simpleArraySearch1000ElementsTestNotFound() {
        Integer[] input = new Integer[1000];
        Arrays.setAll(input, i -> (int) (Math.random() * 100));

        ParallelArraySearch<Integer> parallelArraySearch = new ParallelArraySearch<>();

        Integer expected = -1;

        Integer result = parallelArraySearch.getIndex(input, -99);

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void differentDataTypeTest() {
        String[] input = new String[] {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и",
                "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч",
                "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"};
        ParallelArraySearch<String> parallelArraySearch = new ParallelArraySearch<>();
        Integer result = parallelArraySearch.getIndex(input, "ю");
        Integer expected = 31;
        assertThat(expected).isEqualTo(result);
    }
}