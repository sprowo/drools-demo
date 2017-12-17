package com.prowo.algorithm;

/**
 * @author prowo
 * @date 2017/12/17
 */
public class MaxSubSumTest {

    public static void main(String[] args) {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};
        System.err.println(maxSubSum1(arr));
        System.err.println(maxSubSum2(arr));
        System.err.println(maxSubSum3(arr));
    }

    public static int maxSubSum3(int[] arr) {
        int max = 0;
        int temp = 0;

        for (int i = 0; i < arr.length; i++) {
            temp += arr[i];
            if (temp > max) {
                max = temp;
            }
            if (temp < 0) {
                temp = 0;
            }
        }

        return max;
    }

    public static int maxSubSum2(int[] arr) {
        int max = 0;

        for (int i = 0; i < arr.length; i++) {
            int temp = 0;
            for (int j = 0; j < arr.length; j++) {
                temp += arr[j];
                if (temp > max) {
                    max = temp;
                }
            }
        }

        return max;
    }

    public static int maxSubSum1(int[] arr) {
        int max = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int temp = 0;
                for (int k = i; k < j; k++) {
                    temp += arr[k];
                }
                if (temp > max) {
                    max = temp;
                }
            }
        }

        return max;
    }

}
