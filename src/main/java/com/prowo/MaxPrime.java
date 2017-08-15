package com.prowo;

/**
 * Created by linan on 2017/8/13.
 */
public class MaxPrime {
    public static boolean isPrime(int number) {
        if (number <= 3) {
            return number > 1;
        }
        int k = (int) Math.sqrt(number);
        for (int i = 2; i <= k; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int maxPrime(int number) {
        int max = 0;
        if (number <= 1) {
            return max;
        }
        for (int i = number; i > 1; i--) {
            if (isPrime(i)) {
                max = i;
                break;
            }
        }
        return max;
    }


    public static void main(String[] args) {
        System.out.println(maxPrime(4));
    }
}
