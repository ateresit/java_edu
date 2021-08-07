package ru.geekbrains;

import ru.geekbrains.queue.PriorityQueue;
import ru.geekbrains.queue.Queue;
import ru.geekbrains.queue.QueueImpl;
import ru.geekbrains.stack.Stack;
import ru.geekbrains.stack.StackImpl;

public class Main {
    public static void main(String[] args) {
        //testSteck();
        testQueue();

    }

    private static void testSteck() {
        Stack<Integer> stack = new StackImpl<>(5);
        addToStack(stack, 10);
        addToStack(stack, 12);
        addToStack(stack, 9);
        addToStack(stack, 13);
        addToStack(stack, 11);

        stack.display();
    }

    private static boolean addToStack(Stack<Integer> stack, int value) {
        if (!stack.isFull()) {
            stack.push(value);
            return true;
        }
        return false;
    }

    private static void testQueue() {
//        Queue<Integer> queue = new QueueImpl<>(5);
        Queue<Integer> queue = new PriorityQueue<>(5);
        System.out.println("add element: " + queue.insert(812));
        System.out.println("add element: " + queue.insert(14));
        System.out.println("add element: " + queue.insert(16));
        System.out.println("add element: " + queue.insert(47));
        System.out.println("add element: " + queue.insert(200));
        System.out.println("add element: " + queue.insert(34));

        queue.display();

        System.out.println("removed: " + queue.remove());

        queue.display();

        System.out.println("removed: " + queue.remove());

        queue.display();
    }
}
