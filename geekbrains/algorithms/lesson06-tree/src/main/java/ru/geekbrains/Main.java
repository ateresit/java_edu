package ru.geekbrains;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new TreeImpl<>();
        tree.add(60);
        tree.add(50);
        tree.add(66);
        tree.add(40);
        tree.add(55);
        tree.add(70);
        tree.add(31);
        tree.add(45);
        tree.add(42);
        tree.add(43);
        tree.add(67);
        tree.add(81);

        System.out.println("Find 70: " + tree.contains(70));
        System.out.println("Find 700: " + tree.contains(700));

        tree.remove(43);
        tree.remove(66);
        tree.remove(40);
        tree.remove(50);

        tree.display();

//        tree.traverse(Tree.TraversMode.PRE_ORDER);
    }
}
