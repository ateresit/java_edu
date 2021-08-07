package lesson2.search;

public class InterpolationSearch {
    public static int search(Integer[] arr, int num) {
        int start = 0;
        int end = arr.length - 1;
        int base;
        int i = 0;
        while (end >= start && num >= arr[start] && num <= arr[end]) {
            if (arr[start] == arr[end]) {
                break;
            }
            i++;
            base = (int)(start +
                    1.0 * (end - start) / (arr[end] - arr[start])
                    * (num - arr[start]));

            if (arr[base] == num) {
                System.out.println("Кол-во итераций: " + i);
                return base;
            } else if (arr[base] > num) {
                end = base - 1;
            } else {
                start = base + 1;
            }

        }
        if (arr[start] == num) {
            return start;
        }

        return -1;
    }
}
