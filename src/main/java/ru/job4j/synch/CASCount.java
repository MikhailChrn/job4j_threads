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
        do {
            previous = get();
            count.set(previous + 1);
        } while (!count.compareAndSet(previous + 1, get()));
    }

    public int get() {
        return count.get();
    }
}
