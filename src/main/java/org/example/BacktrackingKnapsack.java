package org.example;

import java.util.ArrayList;
import java.util.List;

//背包逆问题，依据结果推组合
public class BacktrackingKnapsack {
    public static int findMin(int a,int b){
        if(a==-1){
            return b;
        }else if(b==-1){
            return a;
        }else{
            return Math.min(a, b);
        }
    }
    public static List<Integer> solve(int[] value, int[] weight, int capacity, int maxV) {
        if(maxV < capacity){
            int[][] dp = new int[value.length][maxV+1];//此时dp[i]表示达到价值i所需最小重量
            dp[0][0]=0;
            for (int i = 1; i <= maxV; i++) {
                if(i<=value[0]){
                    dp[0][i]=weight[0];
                    continue;
                }
                dp[0][i] = -1;//-1表示穷大
            }

            for (int i = 1; i < value.length; i++) {
                for (int j=maxV;j>=value[i];j--){
                    if(dp[i-1][j-value[i]]==-1) {
                        dp[i][j]=-1;
                        continue;
                    }
                    //仍为无穷大(-1表示无穷大)
                    dp[i][j]=findMin(dp[i-1][j],dp[i-1][j-value[i]]+weight[i]);
                }
                for (int j=value[i]-1;j>0;j--){
                    dp[i][j]=findMin(dp[i-1][j],weight[i]);
                }
            }

            if(dp[value.length-1][maxV]>capacity || dp[value.length-1][maxV]==-1){
                int i;
                for(i = maxV;(dp[value.length-1][i]>capacity || dp[value.length-1][i]==-1) && i>0;i--);
                System.out.println("无法达到价值"+maxV);
                return null;
            }

            List<Integer> ans = new ArrayList<>();
            int nowValue = maxV;
            for (int i = value.length-1; i >0  && nowValue!=0; i--) {
                if(nowValue<value[i]) continue;
                if(dp[i][nowValue]==dp[i-1][nowValue-value[i]]+weight[i]){
                    ans.add(i);
                    nowValue-=value[i];
                }
            }
            if(nowValue>=value[0]) ans.add(0);
            return ans;
        }else{
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

            if(dp[value.length-1][capacity]<maxV) {
                System.out.println("无法达到价值"+maxV);
                return null;
            }

            List<Integer> ans = new ArrayList<>();
            int i,j;
            for(j = capacity;j>0; j--){
                if(dp[value.length-1][j]<maxV) {
                    j++;
                    break;
                }
            }
            for (i = value.length-1; i >0 && j>0; i--) {
                if(j<weight[i]) continue;
                if(dp[i][j]==dp[i-1][j-weight[i]]+value[i]){
                    ans.add(i);
                    j-=weight[i];
                }
            }
            if(j>=weight[0]) ans.add(0);//第一个物品无前件，特殊处理
            return ans;
        }

    }
    private static void print(int[] value, int[] weight, int capacity, int maxV){
        System.out.print("对于背包大小"+capacity+"，有"+value.length+"个物品，其价值和重量分别为：");        for(int i=0;i<value.length;i++){
            System.out.print("("+value[i]+","+weight[i]+")");
        }
        System.out.println();

        List<Integer> ans;
        if((ans=solve(value,weight,capacity,maxV))!=null) {
            System.out.print("达成价值"+maxV+"可能的组合为:");
            for(Integer x:ans){
                System.out.print("物品"+(x+1));
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void main(String[] args) {

        print(new int[]{1,2,3},new int[]{2,4,5},7,10);
        print(new int[]{5,6,7},new int[]{3,4,5},10,11);
        print(new int[]{1,4,5,7},new int[]{1,3,4,5},8,12);
        print(new int[]{2,3,4,5},new int[]{3,4,5,6},15,10);
        print(new int[]{10,20,30},new int[]{5,10,15},20,30);
        print(new int[]{1,1,1,1},new int[]{2,2,2,2},5,2);
        print(new int[]{5},new int[]{3},3,5);
    }
}
