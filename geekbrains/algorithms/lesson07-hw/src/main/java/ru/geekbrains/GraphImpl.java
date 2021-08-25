package ru.geekbrains;

import java.util.*;

public class GraphImpl implements Graph{

    private final List<Vertex> vertexList;
    private final boolean[][] adjMatrix;

    public GraphImpl(int maxVertexCount) {
        this.vertexList = new ArrayList<>(maxVertexCount);
        this.adjMatrix = new boolean[maxVertexCount][maxVertexCount];
    }

    @Override
    public void addVertex(String name) {
        vertexList.add(new Vertex(name));
    }

    @Override
    public boolean addEdge(String firstName, String lastName, String... others) {
        boolean result = addEdge(firstName, lastName);
        for (String other: others) {
            result &= addEdge(firstName, other);
        }

        return result;
    }

    public boolean addEdge(String firstName, String lastName) {
        int firstIndex = indexOf(firstName);
        int lastIndex = indexOf(lastName);

        if (firstIndex == -1 || lastIndex == -1) {
            return false;
        }

        adjMatrix[firstIndex][lastIndex] = true;
        adjMatrix[lastIndex][firstIndex] = true;

        return true;
    }

    private int indexOf(String firstName) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).getName().equals(firstName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSize() {
        return vertexList.size();
    }

    @Override
    public void display() {
        for (int i = 0; i < getSize(); i++) {
            System.out.print(vertexList.get(i));

            for (int j = 0; j < getSize(); j++) {
                if (adjMatrix[i][j]) {
                    System.out.print(" -> " + vertexList.get(j));
                }
            }
            System.out.println();
        }
    }

    @Override
    public void dfs(String firstName) {
        int firstIndex = indexOf(firstName);
        if (firstIndex == -1) {
            throw new IllegalArgumentException("Не найден индекс" + firstIndex);
        }

        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertexList.get(firstIndex);

        checkInVertex(stack, vertex);
        while (!stack.isEmpty()) {
            vertex = getNearUnvisitedVertex(stack.peek());
            if (vertex != null) {
                checkInVertex(stack, vertex);
            } else {
                stack.pop();
            }
        }

    }

    private Vertex getNearUnvisitedVertex(Vertex vertex) {
        int currentIndex = vertexList.indexOf(vertex);
        for (int j = 0; j < getSize(); j++) {
            if (adjMatrix[currentIndex][j] && !vertexList.get(j).isCheckIn()) {
                return vertexList.get(j);
            }
        }
        
        return null;
    }

    private void checkInVertex(Stack<Vertex> stack, Vertex vertex) {
        System.out.println(vertex.getName());
        stack.push(vertex);
        vertex.setCheckIn(true);
    }

    @Override
    public void bfs(String firstName) {
        int firstIndex = indexOf(firstName);
        if (firstIndex == -1) {
            throw new IllegalArgumentException("Не найден индекс" + firstIndex);
        }

        Queue<Vertex> queue = new LinkedList<>();
        Vertex vertex = vertexList.get(firstIndex);

        checkInVertex(queue, vertex);
        while (!queue.isEmpty()) {
            vertex = getNearUnvisitedVertex(queue.peek());
            if (vertex != null) {
                checkInVertex(queue, vertex);
            } else {
                queue.remove();
            }
        }
    }

    private void checkInVertex(Queue<Vertex> queue, Vertex vertex) {
        System.out.println(vertex.getName());
        queue.add(vertex);
        vertex.setCheckIn(true);
    }

}
