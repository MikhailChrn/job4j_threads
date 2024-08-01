package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class WgetTest {
    @Test
    void whenMethodGetNameFileFromUrlTesting() {
        String input
                = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        String expected = "pom.xml";
        String result = Wget.getNameFileFromUrl(input);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenMethodGetDelayMSTesting() {
        long inputNano = (long) (0.5 * Math.pow(10, 9));
        int expected = (int) ((1 * Math.pow(10, 9) - inputNano) / Math.pow(10, 6));
        int result = Wget.getDelayMS(inputNano);
        assertThat(result).isEqualTo(expected);
    }
}