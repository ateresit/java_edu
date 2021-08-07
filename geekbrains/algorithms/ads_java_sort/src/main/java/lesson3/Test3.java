package lesson3;

import lesson3.queue.PriorityQueue;
import lesson3.queue.Queue;
import lesson3.queue.QueueImpl;
import lesson3.stack.Stack;
import lesson3.stack.StackImpl;

public class Test3 {

    public static void main(String[] args) {
//        testStack();
        testQueue();
    }

    private static void testQueue() {
//        Queue<Integer> queue = new QueueImpl<>(5);
        Queue<Integer> queue = new PriorityQueue<>(5);
        System.out.println("add element: " + queue.insert(34));
        System.out.println("add element: " + queue.insert(12));
        System.out.println("add element: " + queue.insert(20));
        System.out.println("add element: " + queue.insert(16));
        System.out.println("add element: " + queue.insert(14));
        System.out.println("add element: " + queue.insert(17));

        queue.display();
        System.out.println("remove: " + queue.remove());
        queue.display();

    }

    private static void testStack() {
        Stack<Integer> stack = new StackImpl<>(5);
        if (!stack.isEmpty()) {
            stack.pop();
        }
        addToStack(stack, 12);
        addToStack(stack, 16);
        addToStack(stack, 22);
        addToStack(stack, 5);
        addToStack(stack, 1);
        addToStack(stack, 32);
        System.out.println("last value: " + stack.pop() );
        System.out.println("last value: " + stack.peek() );
        stack.display();
    }

    private static boolean addToStack(Stack<Integer> stack, int value) {
        if (!stack.isFull()) {
            stack.push(value);
            return true;
        }
        return false;
    }

}
