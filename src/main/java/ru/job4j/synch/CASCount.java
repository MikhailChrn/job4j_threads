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
        int value;
        do {
            value = get() + 1;
            count.set(value);
        } while (!count.compareAndSet(value, get()));
    }

    public int get() {
        return count.get();
    }
}
