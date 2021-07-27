package ru.geekbrains;

import java.util.HashSet;

public class CreateNotebook {
    int brandQty;
    int arrayColumnQty;
    String brand;
    int[][] notebookList = new int[brandQty][arrayColumnQty];

    public CreateNotebook(int brandQty, int arrayColumnQty, String brand) {
        this.brandQty = brandQty;
        this.arrayColumnQty = arrayColumnQty;
        this.brand = brand;
    }

    public void createComp(String brand, int brandQty, int price, int ram) {

/*        for (int i = 0; i <= brandQty; i++) {
            for (int j = 0; j < ram.length; j++) {
                for (int k = 0; k < price.length; k++) {
                    notebookList[i][j] = 1;
                }
            }
        }*/
    }
}
