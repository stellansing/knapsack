package org.example;
//基于二维的动态规划
public class BasicBackKnapsack {
    public static int solve(int[] v, int[] w, int c){
        int length = v.length;
        int[][] dp = new int[length][c+1];
        // 初始化
        for(int j = 0; j <= c; j++){
            if(j >= w[0]) dp[0][j] = v[0];
        }
        // 遍历顺序：先物品，后容量
        for(int i = 1; i < length; i++){
            for(int j = 0; j <= c; j++){
                if(j >= w[i]){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-w[i]] + v[i]);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[length-1][c];
    }

    public static void main(String[] args) {
        System.out.println(solve(new int[]{3, 4, 5, 6},new int[]{2,3,4,5},10));//分别表示物品价值、重量，以及背包容量
    }
}