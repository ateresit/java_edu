package lesson2.sort;

public class BubbleSort {
    public static void sort(Integer[] arr) {
        Boolean isChange;
        do {
            isChange = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    Integer temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    isChange = true;
                }
            }
        } while (isChange);
    }

}
