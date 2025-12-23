package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//混合背包
public class MixedKnapsack {
    static class Item {//定义物品对象，包含物品的重量、价值和数量，其中数量为-1表示可以选无穷个
        int weight;
        int value;
        int count;

        public Item(int weight, int value, int count) {
            this.weight = weight;
            this.value = value;
            this.count = count;
        }
    }


    //在已经计算了hadCalculateCapacity的容量下的dp数组，计算容量为capacity的背包最大价值
    private static void dpHelper(List<Item> items, int capacity, int[] dp) {
        int n = items.size();
        if(items.getFirst().count==-1){
            for (int i = 0; i < n; i++) {
                for (int j = items.get(i).weight; j <= capacity; j++) {
                    dp[j] = Math.max(dp[j], dp[j - items.get(i).weight] + items.get(i).value);
                }
            }
        }else{
            for (int i = 0; i < n; i++) {
                for (int j = capacity; j >= items.get(i).weight; j--) {
                    dp[j] = Math.max(dp[j], dp[j - items.get(i).weight] + items.get(i).value);
                }
            }
        }
    }
public static int solve(int capacity,List<Item> items){
    List<Item> baKnapsack = new ArrayList<>();
    List<Item> UnKnapsack = new ArrayList<>();
    for (Item item : items) {
        int count = item.count;
        int weight = item.weight;
        int value = item.value;

        if(count==1){
            baKnapsack.add(new Item(weight, value, count));
        }else if(count>1){
            while (count > 0) {
                int k = Math.min(count, 1 << (int) (Math.log(count) / Math.log(2))); // 二进制拆分
                baKnapsack.add(new Item(weight * k, value * k, k));
                count -= k;
            }
        }else{
            UnKnapsack.add(new Item(weight, value, count));
        }

    }

    int[] dp = new int[capacity + 1];
    dpHelper(baKnapsack, capacity, dp);
    dpHelper(UnKnapsack, capacity, dp);

    return dp[capacity];


}
    public static void main(String[] args) {
        //物品数量为-1表示可以选无穷个
        List<Item> items = new ArrayList<>(Arrays.asList(
                new Item(2, 3, 1),new Item(3, 5, -1),
                new Item(5, 8, 2)
        ));
        System.out.println(solve(10,items));

        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(4, 7, -1),new Item(6, 10, 3),
                new Item(5, 8, -1)
        ));
        System.out.println(solve(15,items));

        items.clear();
        items = new ArrayList<>(Arrays.asList(
                new Item(7, 15, 1),new Item(6, 13, -1),
                new Item(8, 18, 2),new Item(5, 10, -1)
        ));
        System.out.println(solve(25,items));
    }
}
