package ru.geekbrains;

public interface Graph {

    void addVertex(String name);

    boolean addEdge(String firstName, String lastName, String... others );

    int getSize();

    void display();

    /**
     * англ. Depth-first search, DFS
     */
    void dfs(String firstName);

    /**
     * англ. breadth-first search, BFS
     */
    void bfs(String firstName);

}
