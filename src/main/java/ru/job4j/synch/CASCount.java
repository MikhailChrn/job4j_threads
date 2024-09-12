package ru.job4j.synch;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public CASCount() {
        count.set(0);
    }

    public void increment() {
        int previous;
        int value;
        do {
            previous = get();
            value = previous + 1;
        } while (!count.compareAndSet(previous, value));
    }

    public int get() {
        return count.get();
    }
}
