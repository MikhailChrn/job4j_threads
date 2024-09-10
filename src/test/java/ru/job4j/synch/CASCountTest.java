package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    static void atomicIncrement(CASCount counter) {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }

    @Test
    public void when3IncrementAnd3GetInSeries() {
        CASCount counter = new CASCount();
        counter.increment();
        assertThat(counter.get()).isEqualTo(1);
        counter.increment();
        assertThat(counter.get()).isEqualTo(2);
        counter.increment();
        assertThat(counter.get()).isEqualTo(3);
    }

    @Test
    public void when100ThredsIncrement1000timesInParallel() throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();

        final CASCount counter = new CASCount();

        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(
                            () -> atomicIncrement(counter),
                            format("#%d", i)
                    )
            );
        }

        threadList.forEach(thread -> {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        counter.increment();

        assertThat(counter.get()).isEqualTo(100001);
    }
}
