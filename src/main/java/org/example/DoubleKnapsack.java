package org.example;

public class DoubleKnapsack {
    private static int solve(int[] values, int[] weights, int[] volumes, int[] limits) {
        int[][] dp = new int[limits[0] + 1][limits[1] + 1];

        int length = values.length;
        for (int k = 0; k < length; k++) {
            for (int i = limits[0]; i >= weights[k]; i--) {
                for (int j = limits[1]; j >= volumes[k]; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - weights[k]][j - volumes[k]] + values[k]);
                }
            }
        }
        return dp[limits[0]][limits[1]];
    }
    public static void main(String[] args) {
        int[] values = {10,15,20};
        int[] weights = {2,3,4};
        int[] volumes = {3,4,2};
        int[] limits = {6,7};
        System.out.println(solve(values, weights, volumes, limits));

        values = new  int[]{3,4,5,8};
        weights = new int[]{2,3,4,5};
        volumes = new int[]{1,2,3,4};
        limits = new int[]{7,6};
        System.out.println(solve(values, weights, volumes, limits));

    }
}

