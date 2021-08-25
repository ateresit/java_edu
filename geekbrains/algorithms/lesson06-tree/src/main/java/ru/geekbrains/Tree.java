package ru.geekbrains;

public interface Tree<E extends Comparable<? super E>> {
    boolean add(E value);
    boolean contains(E value);
    boolean remove(E value);
    boolean isEmpty();
    int size();
    void display();
}
