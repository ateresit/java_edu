package lesson2;

import lesson2.search.InterpolationSearch;
import lesson2.sort.*;

import java.util.Arrays;

public class SpeedTest {

    private static Long time;

    public static void main(String[] args) {
        MyArray.isRepeat = true;
        MyArray.isShuffle = true;
        final int SIZE = 50000;
        Integer[] arr = MyArray.getArray(SIZE);

        MyArray.shuffleArray(arr);
        printSortName("Быстрая сортировка");
        startTime();
        QuickSort.sort(arr);
        endTime();

        MyArray.shuffleArray(arr);
        printSortName("Вставками сортировка");
        startTime();
        InsertionSort.sort(arr);
        endTime();

        MyArray.shuffleArray(arr);
        printSortName("Выбором сортировка");
        startTime();
        SelectionSort.sort(arr);
        endTime();


        MyArray.shuffleArray(arr);
        printSortName("Сортировка пузырьком");
        startTime();
        BubbleSort.sort(arr);
        endTime();

        MyArray.shuffleArray(arr);
        printSortName("Шейкерная сортировка");
        startTime();
        CocktailSort.sort(arr);
        endTime();

        MyArray.shuffleArray(arr);
        printSortName("Сортировка расческой");
        startTime();
        CombSort.sort(arr);
        endTime();


        MyArray.shuffleArray(arr);
        printSortName("Сортировка кучей");
        startTime();
        HeapSort.sort(arr);
        endTime();


        MyArray.shuffleArray(arr);
        printSortName("Сортировка слиянием");
        startTime();
        MergeSort.sort(arr);
        endTime();

        MyArray.shuffleArray(arr);
        printSortName("Сортировка голубиная");
        startTime();
        PigeonholeSort.sort(arr);
        endTime();

        MyArray.shuffleArray(arr);
        printSortName("Сортировка встроенная");
        startTime();
        Arrays.sort(arr);
        endTime();

    }

    private static void startTime() {
        time = System.currentTimeMillis();
    }

    private static void endTime() {
        time = System.currentTimeMillis() - time;
        System.out.println(time);

    }

    private static void printSortName(String sortName) {
        System.out.printf("%s: ", sortName);
    }

    private static void printArray(Integer[] arr) {
        System.out.println(Arrays.toString(arr));
    }



}
