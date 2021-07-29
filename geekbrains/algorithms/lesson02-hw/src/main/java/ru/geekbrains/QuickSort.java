package ru.geekbrains;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QuickSort {
    public static List<Integer> quickSort(List<Integer> arr) {

        if (arr.isEmpty()) {
            return arr;
        }

        Integer base = arr.get(0);

        List<Integer> left = new LinkedList<>();
        List<Integer> middle = new LinkedList<>();
        List<Integer> right = new LinkedList<>();


        for (Integer n : arr) {
            if (n.equals(base)) {
                middle.add(n);
            } else if (n.compareTo(base) > 0) {
                right.add(n);
            } else {
                left.add(n);
            }
        }

        left = quickSort(left);
        right = quickSort(right);

        left.addAll(middle);
        left.addAll(right);

        return left;
    }

    public static void sort(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);

        quickSort(list).toArray(arr);
    }

}
