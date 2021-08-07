package lesson2.sort;

public class SelectionSort {
    public static void sort(Integer[] arr) {

        int min = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
//            if (i == min) {
//                continue;
//            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }

    }
}
