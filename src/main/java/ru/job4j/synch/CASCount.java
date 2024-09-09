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
        int exp = get() + 1;
        int value;
        do {
            value = count.incrementAndGet();
        } while (!count.compareAndSet(exp, value));
    }

    public int get() {
        return count.get();
    }
}
