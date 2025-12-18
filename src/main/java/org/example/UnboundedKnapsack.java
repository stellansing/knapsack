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
        int alpha = 0;//alpha为单位价值最大的索引
        double pa = -1.0, pb = -1.0;//pa和pb分别为最大和次打值单位价值
        for (int i = 0; i < n; i++) {
            double p = (double) items.get(i).value / items.get(i).weight;
            if (p > pa) {
                pb = pa;
                pa = p;
                alpha = i;
            } else if (p > pb && i != alpha) {
                pb = p;
            }
        }
        if (pb < 0) {
            pb = pa;
        }

        if (Math.abs(pa - pb) < 1e-9) {//最大与次大单位价值接近，退化为传统动态规划算法
            int[] fullDp = new int[capacity + 1];
            dpHelper(items, capacity,0,fullDp);
            return fullDp[capacity];
        }


        //计算最简单情况及回溯次数
        int wAlpha = items.get(alpha).weight;
        int vAlpha = items.get(alpha).value;
        int m = capacity % wAlpha;
        int q = capacity / wAlpha;

        items.set(alpha, new Item(wAlpha, 0));//排除单位价值最大的物品
        int[] dp = new int[capacity + 1];
        dpHelper(items, m,0, dp);
        int rM = dp[m];//剩余容量（总容量 mod Wa）最大价值
        double numerator = pb * m - rM;
        double denominator = (pa - pb) * wAlpha;
        int km = (int) (numerator / denominator) + 1;//依据公式求解最大回溯次数km
        km = Math.min(km, q);

        //回溯求最终解
        int rnewMaxCap = m + km * wAlpha;//需要回溯的背包容量rnewMaxCap
        rnewMaxCap = Math.min(rnewMaxCap, capacity);//与原背包大小对比，保证处理范围在问题之内
        dpHelper(items, rnewMaxCap,m,dp);
        int maxValue = 0;
        for (int i = 0; i <= km ; i++) {
            int currentCap = m + i * wAlpha;
            if (currentCap > rnewMaxCap) break;
            int currentValue = (q-i) * vAlpha + dp[currentCap];//(q-i)为选alpha的次数
            maxValue = Math.max(maxValue, currentValue);
        }

        return maxValue;
    }

    //动态规划求解，
    private static void dpHelper(List<Item> items, int capacity,int hadCalculateCapacity,int[] dp) {
        int n = items.size();
        for (int i = 0; i < n; i++) {
            for (int j = Math.max(items.get(i).weight, hadCalculateCapacity+1); j <= capacity; j++) {
                dp[j] = Math.max(dp[j], dp[j - items.get(i).weight] + items.get(i).value);
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

        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(2, 3), new Item(3, 4), new Item(4, 5),
                new Item(5, 6)
        ));
        print(items,10);

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