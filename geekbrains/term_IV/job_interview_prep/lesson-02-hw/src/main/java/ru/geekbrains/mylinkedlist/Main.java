package ru.geekbrains.mylinkedlist;

public class Main {

    public static void main(String[] args) {

        testMyLinkedList();
    }

    private static void testMyLinkedList() {
        LinkedList<Integer> linkedList = new LinkedListImpl<>();

        // проверка добавления
        linkedList.insertFirst(1);
        linkedList.insertFirst(2);
        linkedList.insertFirst(3);
        linkedList.insertFirst(4);
        linkedList.insertFirst(5);
        linkedList.insertFirst(6);
        linkedList.insertFirst(7);
        linkedList.insertFirst(8);
        linkedList.display();

        // проверка поиска
        System.out.println("Searching 3, result: " + linkedList.contains(3));
        System.out.println("Searching 7, result: " + linkedList.contains(7));
        System.out.println("Searching 10, result: " + linkedList.contains(10));

        // проверка удаления
        linkedList.removeFirst();
        linkedList.display();

        // проверка удаления произвольного элемента
        linkedList.remove(5);
        linkedList.display();
    }
}
