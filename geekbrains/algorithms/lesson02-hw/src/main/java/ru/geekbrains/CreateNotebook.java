package ru.geekbrains;

public class CreateNotebook {

    public void createComp(String[] brand, int[] ram, int[] price, int quantity) {

        for (int i = 0; i < brand.length; i++) {
            for (int j = 0; j < ram.length; j++) {
                for (int k = 0; k < price.length; k++) {
                    Notebook notebook = new Notebook(brand[i], ram[j], price[k]);
                }
            }
        }
    }
}
