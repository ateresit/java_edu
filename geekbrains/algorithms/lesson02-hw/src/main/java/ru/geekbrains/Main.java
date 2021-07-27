package ru.geekbrains;

import java.util.*;

public class Main {
    private static String brandName;

    public static void main(String[] args) {
        RandomValueGen randomValueGen = new RandomValueGen();

        List<Notebook> notebooks = new ArrayList<>();

        addNotebookToArray(randomValueGen, notebooks, "Lenuvo");
        addNotebookToArray(randomValueGen, notebooks, "Asos");
        addNotebookToArray(randomValueGen, notebooks, "MacNote");
        addNotebookToArray(randomValueGen, notebooks, "Eser");
        addNotebookToArray(randomValueGen, notebooks, "Xamiou");

        System.out.println(notebooks.size());
        System.out.println(notebooks.toString());
    }

    private static void addNotebookToArray(RandomValueGen randomValueGen, List<Notebook> notebooks, String brandName) {
        for (int i = 0; i <1000; i++) {
            notebooks.add(new Notebook(brandName,
                    randomValueGen.randomValueWithStep(100, 7, 500, 1000),
                    randomValueGen.randomValueWithStep(4, 4, 4, 12)));
        }
    }

}
