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
    public static List<Integer> solve(int[] v, int[] w, int c, int maxV) {
        int allValue = 0;
        for (int k : v) allValue += k;
        if(allValue < c){
            int[] dp = new int[allValue+1];//此时dp[i]表示达到价值i所需最小重量
            dp[0]=0;
            for (int i = 1; i <= allValue; i++) dp[i]=-1;//-1表示穷大

            for (int i = 0; i < v.length; i++) {
                for (int j=allValue;j>=v[i];j--){
                    if(dp[j-v[i]]==-1) continue;//仍为无穷大
                    dp[j]=findMin(dp[j],dp[j-v[i]]+w[i]);
                }
            }

            if(maxV>allValue){
                int i;
                for(i = allValue;(dp[i]>c || dp[i]==-1) && i>0;i--);
                System.out.println("无法达到价值"+maxV);
                if(i!=0) System.out.println("最大价值应该为"+i);
                else System.out.println("无最大价值");
                return null;
            }
            if(dp[maxV]>c || dp[maxV]==-1) {
                int i;
                for(i = maxV;(dp[i]>c || dp[i]==-1) && i>0;i--);
                System.out.println("无法达到价值"+maxV);
                if(i!=0) System.out.println("最大价值应该为"+i);
                else System.out.println("无最大价值");
                return null;
            }
            if(dp[maxV]<c){
                int i;
                for(i = maxV;dp[i]<c && i<=allValue; i++);
                System.out.println("最大价值应该为"+(i-1));
            }
            if (maxV == c) {
                System.out.println("所给价值为最大价值");
            }

            List<Integer> ans = new ArrayList<>();
            int nowValue = maxV;
            for (int i = v.length-1; i >=0 && nowValue!=0; i--) {
                if(nowValue<v[i]) continue;
                if(dp[nowValue]==dp[nowValue-v[i]]+w[i]){
                    ans.add(i);
                    nowValue-=v[i];
                }
            }
            return ans;
        }else{
            int[][] dp = new int[v.length][c+1];//dp[i][j]表示前i个物品，背包容量为j时，最大价值
            for(int j = c; j >= w[0] ; j--) dp[0][j] = v[0];

            for(int i = 1; i < v.length; i++){
                for(int j = c;j >= w[i] ; j--){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-w[i]] + v[i]);
                }
                for(int j = 0; j < w[i]; j++){
                    dp[i][j] = dp[i-1][j];
                }
            }

            if(maxV>allValue){
                System.out.println("无法达到价值"+maxV);
                System.out.println("最大价值应该为"+dp[v.length-1][c]);
                return null;
            }
            if(dp[v.length-1][c]<maxV) {
                System.out.println("无法达到价值"+maxV);
                System.out.println("最大价值应该为"+dp[v.length-1][c]);
                return null;
            }else if(dp[v.length-1][c]>maxV){
                System.out.println("最大价值应该为"+dp[v.length-1][c]);
            }else{
                System.out.println("所给价值为最大价值");
            }

            List<Integer> ans = new ArrayList<>();
            int i,j;
            for(j = c;j>0; j--){
                if(dp[v.length-1][j]<maxV) {
                    j++;
                    break;
                }
            }
            for (i = v.length-1; i >0 && j>0; i--) {
                if(j<w[i]) continue;
                if(dp[i][j]==dp[i-1][j-w[i]]+v[i]){
                    ans.add(i);
                    j-=w[i];
                }
            }
            if(j>=w[0]) ans.add(0);//第一个物品无前件，特殊处理

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
