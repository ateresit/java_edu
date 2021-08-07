package sorting;

public class Main {
    public static void main(String[] args) {
        int size = 100;
        MyArr arr = new MyArr(size);
        arr.insert("Vasya", 10);
        arr.insert("Igor", 15);
        arr.insert("Viktor", 15);
        arr.display();

        arr.sortInsertObj();

        arr.display();
    }
}
