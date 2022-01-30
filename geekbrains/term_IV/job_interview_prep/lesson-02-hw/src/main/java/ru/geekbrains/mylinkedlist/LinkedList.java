package ru.geekbrains.mylinkedlist;

public interface LinkedList<E> {

    int size();
    boolean isEmpty();

    void insertFirst(E value);
    E getFirst();
    E removeFirst();

    boolean contains(E value);
    boolean remove(E value);

    void display();

    class Node<E> {
        E item;
        Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

}
