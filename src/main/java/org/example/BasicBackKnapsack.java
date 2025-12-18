package org.example;

import java.util.ArrayList;
import java.util.List;

//基于二维的动态规划
public class BasicBackKnapsack {
    public static int solve(int[] value, int[] weight, int capacity){
        int length = value.length;
        int[] dp=new int[capacity+1];
        for(int i=0;i<=capacity;i++) dp[i]=0;
        for(int i=0;i<length;i++){
            for(int j=capacity;j>=weight[i];j--){
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0, j = capacity; i <value.length && j>0; i++) {
            if(j<weight[i]) continue;
            if(dp[j]==dp[j-weight[i]]+value[i]){
                ans.add(i);
                j-=weight[i];
            }
        }

        System.out.println("达成该价值可能的组合为"+ans);
        return dp[capacity];
    }

    public static void main(String[] args) {
        System.out.println("最大价值为"+solve(new int[]{3, 4, 5, 6},new int[]{2,3,4,5},10));//分别表示物品价值、重量，以及背包容量
    }
}