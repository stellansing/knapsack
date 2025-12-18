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
            int[] dp = new int[maxV+1];//此时dp[i]表示达到价值i所需最小重量
            dp[0]=0;
            for (int i = 1; i <= maxV; i++) dp[i]=-1;//-1表示穷大

            for (int i = 0; i < value.length; i++) {
                for (int j=maxV;j>=value[i];j--){
                    if(dp[j-value[i]]==-1) continue;//仍为无穷大(-1表示无穷大)
                    dp[j]=findMin(dp[j],dp[j-value[i]]+weight[i]);
                }
                for (int j=value[i]-1;j>0;j--){
                    dp[j]=findMin(dp[j],weight[i]);
                }
            }

            if(dp[maxV]>capacity || dp[maxV]==-1) {
                int i;
                for(i = maxV;(dp[i]>capacity || dp[i]==-1) && i>0;i--);
                System.out.println("无法达到价值"+maxV);
                return null;
            }

            List<Integer> ans = new ArrayList<>();
            int nowValue = maxV;
            for (int i = 0; i < value.length && nowValue!=0; i++) {
                if(nowValue<value[i]) continue;
                if(dp[nowValue]==dp[nowValue-value[i]]+weight[i]){
                    ans.add(i);
                    nowValue-=value[i];
                }
            }
            return ans;
        }else{
            int length = value.length;
            int[] dp=new int[capacity+1];
            for(int i=0;i<=capacity;i++) dp[i]=0;
            for(int i=0;i<length;i++){
                for(int j=capacity;j>=weight[i];j--){
                    dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
                }
            }

            if(dp[capacity]<maxV) {
                System.out.println("无法达到价值"+maxV);
                return null;
            }

            List<Integer> ans = new ArrayList<>();
            int j;
            for(j = capacity;j>0; j--){
                if(dp[j]<maxV) {
                    if(j==capacity) break;
                    j++;
                    break;
                }
            }//找到最小背包大小
            for (int i = 0; i <value.length && j>0; i++) {
                if(j<weight[i]) continue;
                if(dp[j]==dp[j-weight[i]]+value[i]){
                    ans.add(i);
                    j-=weight[i];
                }
            }
            return ans;
        }

    }
    private static void print(int[] value, int[] weight, int capacity, int maxV){
        System.out.print("对于背包大小"+capacity+"，有"+value.length+"个物品，其价值和重量分别为：");
        for(int i=0;i<value.length;i++){
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

