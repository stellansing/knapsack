package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//完全背包
public class UnboundedKnapsack {
    static class Item {//定义物品对象
        int weight;
        int value;
        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }
    public static int solve(List<Item> items, int capacity) {
        int n = items.size();//物品数量
        //求解单位价值最大和次打值
        int indexA=0;//alpha为单位价值最大的索引
        double pa=-1.0,pb=-1.0;//pa和pb分别为最大和次打值单位价值
        for(int i=0;i<n;i++){
            double p=(double)items.get(i).value/items.get(i).weight;
            if (p>pa){
                pb=pa;
                pa=p;
                indexA=i;
            }else if(p>pb){
                pb=p;
            }
        }

        int[] dp = new int[capacity+1];
        if(pb<0 || pa-pb<1e-9){//最大与次大单位价值接近，退化为传统动态规划算法
            dpHelper(items, capacity,0,dp);
            return dp[capacity];
        }

        //计算最简单情况及回溯次数
        int weightA = items.get(indexA).weight;
        int valueA = items.get(indexA).value;
        int m = capacity%weightA;
        int q = capacity/weightA;

        items.set(indexA, new Item(weightA, 0));//排除单位价值最大的物品
        dpHelper(items, m,0, dp);
        int km = Math.min((int)((pb*m-dp[m])/((pa-pb)*weightA))+1,q);//依据公式求解最大回溯次数km

        //回溯求最终解
        int newMaxCap = Math.min(m+km*weightA,capacity);//与原背包大小对比，保证处理范围在问题之内
        dpHelper(items,newMaxCap,m,dp);

        int maxValue=0,currentCap=m;
        for(int i=0;i<=km;i++){
            if(currentCap>newMaxCap) break;
            maxValue = Math.max(maxValue,q*valueA+dp[currentCap]);
            currentCap += weightA;
            q--;
        }
        return maxValue;
    }

    //动态规划求解，
    private static void dpHelper(List<Item> items, int capacity,int hadCalculateCapacity,int[] dp){
        int n = items.size();
        for (int i = 0; i < n; i++){
            for (int j = Math.max(items.get(i).weight,hadCalculateCapacity+1);j<=capacity;j++){
                dp[j] = Math.max(dp[j],dp[j - items.get(i).weight]+items.get(i).value);
            }
        }
    }

    private static void print(List<Item> items, int capacity){
        System.out.print("对于背包大小"+capacity+"，有"+items.size()+"种物品，其价值和重量分别为：");
        for(int i=0;i<items.size();i++){
            System.out.print("("+items.get(i).value+","+items.get(i).weight+")");
        }
        System.out.println();
        System.out.println("可以达到的最大价值为"+solve(items,capacity));
        System.out.println();
    }

    public static void main(String[] args) {

        List<Item> items = new ArrayList<>(Arrays.asList(
                new Item(1, 1), new Item(2, 5), new Item(3, 8),
                new Item(4, 9), new Item(5, 10), new Item(6, 17),
                new Item(7, 17), new Item(8, 20), new Item(9, 24),
                new Item(10, 3)
        ));
        print(items,27);


        //（物品 A：价值 10，重量 3；物品 B：价值 11，重量 4；物品 C：价值 12，重量 5）
        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(3, 10), new Item(4, 11), new Item(5, 12)
        ));
        print(items,20);

        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(3, 5)
        ));
        print(items,15);

        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(1, 1), new Item(3, 4), new Item(4, 5), new Item(5, 7)
        ));
        print(items,20);

        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(2, 4), new Item(3, 6), new Item(4, 8)
        ));
        print(items,12);

        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(5, 10), new Item(4, 7), new Item(3, 5)
        ));
        print(items,6);
    }
}