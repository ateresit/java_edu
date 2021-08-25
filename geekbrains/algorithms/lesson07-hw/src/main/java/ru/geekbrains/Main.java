package ru.geekbrains;

public class Main {
    public static void main(String[] args) {

        cityGraph();
    }

    private static void cityGraph() {
        Graph graph = new GraphImpl(10);

        graph.addVertex("Москва");
        graph.addVertex("Тула");
        graph.addVertex("Рязань");
        graph.addVertex("Калуга");
        graph.addVertex("Липецк");
        graph.addVertex("Тамбов");
        graph.addVertex("Орёл");
        graph.addVertex("Саратов");
        graph.addVertex("Курск");
        graph.addVertex("Воронеж");

        graph.addEdge("Москва", "Тула", "Рязань", "Калуга");
        graph.addEdge("Тула", "Москва", "Липецк");
        graph.addEdge("Рязань", "Москва", "Тамбов");
        graph.addEdge("Калуга", "Москва", "Орёл");
        graph.addEdge("Липецк", "Тула", "Воронеж");
        graph.addEdge("Тамбов", "Рязань", "Саратов");
        graph.addEdge("Орёл", "Калуга", "Курск");
        graph.addEdge("Саратов", "Тамбов", "Воронеж");
        graph.addEdge("Курск", "Орёл", "Воронеж");
        graph.addEdge("Воронеж", "Липецк", "Саратов", "Курск");

//        graph.display();
        graph.dfs("Воронеж");
//        graph.bfs("Воронеж");

    }
}
