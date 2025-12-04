package org.example;
//基础01背包
public class BasicKnapsack {
    public static int solve(int[] v, int[] w, int c){
        int length = v.length;
        int[] dp=new int[c+1];
        for(int i=0;i<=c;i++) dp[i]=0;
        for(int i=0;i<length;i++){
            for(int j=c;j>=w[i];j--){
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
        return dp[c];
    }

    public static void main(String[] args) {
        System.out.println(solve(new int[]{3, 4, 5, 6},new int[]{2,3,4,5},10));//分别表示物品价值、重量，以及背包容量
    }
}