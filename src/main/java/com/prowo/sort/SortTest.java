package com.prowo.sort;

/**
 * Created by linan on 2017/8/12.
 */
public class SortTest {

    public static void fastSort(int[] arr, int start, int end) {
        if (null == arr || arr.length <= 1 || start >= end) {
            return;
        }
        int key = arr[start];
        int low = start;
        int high = end;
        while (low < high) {
            while (low < high && arr[high] > key) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] < key) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = key;
        fastSort(arr, start, low - 1);
        fastSort(arr, low + 1, end);
    }


    public static void main(String[] args) {
//        int[] arr = {1, 2, 3, 4, 5};
        int[] arr = {5, 4, 3, 2, 1};
        fastSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
