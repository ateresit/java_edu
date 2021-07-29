package ru.geekbrains;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int arrSize = 5000;
        RandomValueGen randomValueGen = new RandomValueGen();
        NotebookArray notebookArray = new NotebookArray(arrSize);

        createArrNotebooks("Lenuvo", randomValueGen, notebookArray);
        createArrNotebooks("Asos", randomValueGen, notebookArray);
        createArrNotebooks("MacNote", randomValueGen, notebookArray);
        createArrNotebooks("Eser", randomValueGen, notebookArray);
        createArrNotebooks("Xamiou", randomValueGen, notebookArray);

        /*notebookArray.insert("Lenuvo",
                randomValueGen.randomValueWithStep(100, 7, 500, 1000),
                randomValueGen.randomValueWithStep(4, 4, 4, 12));

        notebookArray.insert("Asos",
                randomValueGen.randomValueWithStep(100, 7, 500, 1000),
                randomValueGen.randomValueWithStep(4, 4, 4, 12));

        notebookArray.insert("Eser",
                randomValueGen.randomValueWithStep(100, 7, 500, 1000),
                randomValueGen.randomValueWithStep(4, 4, 4, 12));*/

        notebookArray.display();
        notebookArray.sortInsertObj();

        System.out.println("====================");
        notebookArray.display();


       /* List<Notebook> notebooks = new ArrayList<>(5000);

        addNotebookToArray(randomValueGen, notebooks, "Lenuvo");
        addNotebookToArray(randomValueGen, notebooks, "Asos");
        addNotebookToArray(randomValueGen, notebooks, "MacNote");
        addNotebookToArray(randomValueGen, notebooks, "Eser");
        addNotebookToArray(randomValueGen, notebooks, "Xamiou");

        System.out.println(notebooks.size());
        System.out.println(notebooks.toString());

        List<String> fieldBrand = notebooks.stream().map(Notebook::getBrandName).collect(Collectors.toList());
        List<Integer> fieldPrice = notebooks.stream().map(Notebook::getPrice).collect(Collectors.toList());
        List<Integer> fieldRam = notebooks.stream().map(Notebook::getRamSize).collect(Collectors.toList());

        System.out.println(fieldBrand.toString());
        System.out.println(fieldPrice.toString());
        System.out.println(fieldRam.toString());


    }

    private static void addNotebookToArray(RandomValueGen randomValueGen, List<Notebook> notebooks, String brandName) {
        for (int i = 0; i <1000; i++) {
            notebooks.add(new Notebook(brandName,
                    randomValueGen.randomValueWithStep(100, 7, 500, 1000),
                    randomValueGen.randomValueWithStep(4, 4, 4, 12)));
        }*/
    }

    private static void createArrNotebooks(String brandName, RandomValueGen randomValueGen, NotebookArray notebookArray) {
        for (int i = 0; i <1000; i++) {
            notebookArray.insert(brandName,
                    randomValueGen.randomValueWithStep(100, 7, 500, 1000),
                    randomValueGen.randomValueWithStep(4, 4, 4, 12));
        }
    }

}
