package lesson2;

import lesson2.search.BinarySearch;
import lesson2.search.InterpolationSearch;
import lesson2.sort.BubbleSort;
import lesson2.sort.QuickSort;
import lesson2.sort.SelectionSort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MyArray.isRepeat = false;
        MyArray.isShuffle = true;
        final int SIZE = 30000;
        Integer[] arr = MyArray.getArray(SIZE);

        QuickSort.sort(arr);
//        System.out.println(Arrays.toString(arr));
        System.out.println(InterpolationSearch.search(arr, 30000));
        System.out.println(BinarySearch.search(arr, 30000));
    }
}
