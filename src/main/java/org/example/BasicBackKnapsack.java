package org.example;

import java.util.ArrayList;
import java.util.List;

//基于二维的动态规划
public class BasicBackKnapsack {
    public static int solve(int[] value, int[] weight, int capacity){
        int[][] dp = new int[value.length][capacity+1];//dp[i][j]表示前i个物品，背包容量为j时，最大价值
        for(int j = capacity; j >= weight[0] ; j--) dp[0][j] = value[0];

        for(int i = 1; i < value.length; i++){
            for(int j = capacity;j >= weight[i] ; j--){
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weight[i]] + value[i]);
            }
            for(int j = 0; j < weight[i]; j++){
                dp[i][j] = dp[i-1][j];
            }
        }

        List<Integer> ans = new ArrayList<>();
        int i,j;
        for (i = value.length-1,j = capacity; i >0 && j>0; i--) {
            if(j<weight[i]) continue;
            if(dp[i][j]==dp[i-1][j-weight[i]]+value[i]){
                ans.add(i);
                j-=weight[i];
            }
        }
        if(j>=weight[0]) ans.add(0);//第一个物品无前件，特殊处理
        System.out.print("组合为:"+ ans);
        return dp[value.length-1][capacity];
    }

    public static void main(String[] args) {
        System.out.println("最大价值为"+solve(new int[]{3, 4, 5, 6},new int[]{2,3,4,5},10));//分别表示物品价值、重量，以及背包容量
    }
}