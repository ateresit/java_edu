package ru.geekbrains.dequeue;

public class DeQueueImpl<E> implements DeQueue<E>{

    @Override
    public boolean insertLeft(E value) {
        return false;
    }

    @Override
    public boolean insertRight(E value) {
        return false;
    }

    @Override
    public E removeLeft() {
        return null;
    }

    @Override
    public E removeRight() {
        return null;
    }

    @Override
    public boolean insert(Object value) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object peekFront() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void display() {

    }
}
