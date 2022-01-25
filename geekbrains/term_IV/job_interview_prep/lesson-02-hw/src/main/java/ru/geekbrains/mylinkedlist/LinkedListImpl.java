package ru.geekbrains.mylinkedlist;

public class LinkedListImpl<E> implements LinkedList<E> {

    protected Node<E> firstElem;
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void insertFirst(E value) {
        firstElem = new Node<>(value, firstElem);
        size++;
    }

    @Override
    public E getFirst() {
        if (firstElem != null) {
            return firstElem.item;
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> toRemoveNode = firstElem;
        firstElem = toRemoveNode.next;
        toRemoveNode.next = null;

        size--;

        return toRemoveNode.item;
    }

    @Override
    public boolean contains(E value) {
        Node<E> currElem = firstElem;
        while (currElem != null) {
            if (currElem.item.equals(value)) {
                return true;
            }
            currElem = currElem.next;
        }
        return false;
    }

    @Override
    public boolean remove(E value) {
        Node<E> currentElem = firstElem;
        Node<E> previousElem = null;

        while (currentElem != null) {
            if (currentElem.item.equals(value)) {
                break;
            }
            previousElem = currentElem;
            currentElem = currentElem.next;
        }

        if (currentElem == null) {
            return false;
        } else if (currentElem == firstElem) {
            removeFirst();
            return true;
        } else {
            previousElem.next = currentElem.next;
        }

        currentElem.next = null;
        size--;
        return true;
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");

        Node<E> currElem = firstElem;
        while (currElem != null) {
            stringBuilder.append(currElem.item);

            if (currElem.next != null) {
                stringBuilder.append(" -> ");
            }
            currElem = currElem.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
