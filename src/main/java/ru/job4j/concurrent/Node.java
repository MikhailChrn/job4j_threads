package ru.job4j.concurrent;

public class Node<T> {
    private final Node<T> next;
    private T value;

    public Node(final Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
