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
                    if(dp[j-value[i]]==-1) continue;//仍为无穷大
                    dp[j]=findMin(dp[j],dp[j-value[i]]+weight[i]);
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
            for (int i = value.length-1; i >=0 && nowValue!=0; i--) {
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
            for (int i = length-1; i >=0 && j>0; i--) {
                if(j<weight[i]) continue;
                if(dp[j]==dp[j-weight[i]]+value[i]){
                    ans.add(i);
                    j-=weight[i];
                }
            }




            return ans;
        }

    }
    public static void main(String[] args) {
        List<Integer> ans;
        if((ans=solve(new int[]{1,2,3},new int[]{2,4,5},7,10))!=null) System.out.println("达成该价值可能的组合为"+ans);
        System.out.println();
        if((ans=solve(new int[]{5,6,7},new int[]{3,4,5},10,11))!=null) System.out.println("达成该价值可能的组合为"+ans);
        System.out.println();
        if((ans=solve(new int[]{3,5,6},new int[]{2,3,5},8,8))!=null) System.out.println("达成该价值可能的组合为"+ans);
    }
}
