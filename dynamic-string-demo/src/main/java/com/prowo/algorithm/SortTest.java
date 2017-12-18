package com.prowo.algorithm;

/**
 * @author prowo
 * @date 2017/12/17
 */
public class SortTest {

    public static void main(String[] args) {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};

        print(arr);

//        bubbleSort(arr);
//        selectSort(arr);
//        insertSort(arr);
        quickSort(arr, 0, arr.length - 1);

        print(arr);
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int key = arr[left];
            int low = left;
            int high = right;

            while (low < high) {
                while (low < high && arr[high] >= key) {
                    high--;
                }
                arr[low] = arr[high];
                while (low < high && arr[low] <= key) {
                    low++;
                }
                arr[high] = arr[low];
            }
            arr[low] = key;
            quickSort(arr, left, low - 1);
            quickSort(arr, low + 1, right);
        }
    }

    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }

    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }
    }

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void print(int[] arr) {
        for (int i : arr) {
            System.err.print(i + " ");
        }
        System.err.println();
    }

}
