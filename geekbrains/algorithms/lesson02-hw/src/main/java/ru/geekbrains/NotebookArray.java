package ru.geekbrains;

public class NotebookArray {
    private Notebook[] arr;
    private int size;

    public NotebookArray(int size) {
        this.arr = new Notebook[size];
        this.size = 0;
    }

    public boolean find(String search) {
        int i;
        for (i=0; i <this.size; i++) {
            if (this.arr[i].getBrandName().equals(search)) break;
        }
        if (i == this.size)
            return false;
        else
            return true;
    }

    public void display(){
        for (int i=0; i < this.size; i++) {
            System.out.println(this.arr[i].getBrandName() + " " +
                    this.arr[i].getRamSize() + " " +
                    this.arr[i].getPrice());
        }
    }

    public void delete(String search) {
        int i=0;
        for (i=0; i < this.size; i++) {
            if (this.arr[i].getBrandName().equals(search)) break;
        }

        for (int j=i ; j < this.size - 1; j++) {
            this.arr[j] = this.arr[j+1];
        }
        this.size--;
    }

    public void insert(String brandName, int ramSize, int price){
        this.arr[this.size] = new Notebook(brandName, ramSize, price);
        this.size++;
    }

    public void sortInsertObj(){
        int in, out;
        for(out = 1; out < this.size; out++){
            Notebook temp = this.arr[out];
            in = out;
            while (in > 0 && this.arr[in-1].getBrandName().compareTo(temp.getBrandName()) > 0){
                this.arr[in] = this.arr[in-1];
                --in;
            }
            this.arr[in] = temp;
        }
    }

}