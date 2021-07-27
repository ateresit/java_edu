package ru.geekbrains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        RandomValueGen randomValueGen = new RandomValueGen();
//        NotebookRepo notebookRepo = new NotebookRepo();

        /*System.out.println("price: " + randomValueGen.randomValueWithStep(100, 7, 500, 1000));
        System.out.println("ram: " + randomValueGen.randomValueWithStep(4, 4, 4, 12));*/

        ArrayList<String> notebookArray = new ArrayList<String>();
        notebookArray.add(1, new Notebook("Asus",
                randomValueGen.randomValueWithStep(4, 4, 4, 12),
                randomValueGen.randomValueWithStep(100, 7, 500, 1000)));

    }

}
