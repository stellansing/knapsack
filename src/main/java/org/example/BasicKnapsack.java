package org.example;
//基础01背包
public class BasicKnapsack {
    public static int solve(int[] value, int[] weight, int capacity){
        int length = value.length;
        int[] dp=new int[capacity+1];
        for(int i=0;i<=capacity;i++) dp[i]=0;
        for(int i=0;i<length;i++){
            for(int j=capacity;j>=weight[i];j--){
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[capacity];
    }

    public static void main(String[] args) {
        System.out.println(solve(new int[]{3, 4, 5, 6},new int[]{2,3,4,5},10));//分别表示物品价值、重量，以及背包容量
    }
}