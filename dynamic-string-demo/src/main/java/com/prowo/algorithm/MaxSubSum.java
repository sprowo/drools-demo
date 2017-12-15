package com.prowo.algorithm;

/**
 * @author prowo
 * @date 2017/12/14
 */
public class MaxSubSum {

    public static void main(String[] args) {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};

//        System.err.println(getMaxSubSum1(arr));
        System.err.println(getMaxSubSum2(arr));
//        System.err.println(getMaxSubSumRec(arr));
    }

    public static int getMaxSubSum2(int[] arr) {
        int max = 0;

        for (int i = 0; i < arr.length; i++) {
            int temp = 0;
            for (int j = i; j < arr.length; j++) {
                temp += arr[j];
                if (temp > max) {
                    max = temp;
                }
            }
        }

        return max;
    }

    public static int getMaxSubSum1(int[] arr) {
        int max = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int temp = 0;
                for (int k = i; k < j; k++) {
                    temp += arr[k];
                    if (temp > max) {
                        max = temp;
                    }
                }
            }
        }

        return max;
    }

    /**
     * 动态规划法
     *
     * @param arr
     * @return
     */
    public static int getMaxSubSumRec(int[] arr) {
        int max = 0;
        int temp = 0;
        for (int i = 0; i < arr.length; i++) {
            temp += arr[i];
            if (temp > max) {
                max = temp;
            } else if (temp < 0) {
                temp = 0;
            }
        }
        return max;
    }


}
