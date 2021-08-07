package ru.geekbrains.dequeue;

import ru.geekbrains.queue.Queue;

public interface DeQueue <E> extends Queue {

    boolean insertLeft(E value);
    boolean insertRight(E value);

    E removeLeft();
    E removeRight();

}
