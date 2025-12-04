package org.example;

import java.util.ArrayList;
import java.util.List;
// 多重背包问题
public class MultipleKnapsack {

    static class Item {
        int weight;
        int value;
        int count;

        public Item(int weight, int value, int count) {
            this.weight = weight;
            this.value = value;
            this.count = count;
        }
    }

    public static int knapsack(int capacity, List<Item> items) {
        // 优化部分：二进制拆分降低复杂度
        List<Item> newItems = new ArrayList<>();
        for (Item item : items) {
            int count = item.count;
            int weight = item.weight;
            int value = item.value;

            while (count > 0) {
                int k = Math.min(count, 1 << (int) (Math.log(count) / Math.log(2))); // 二进制拆分
                newItems.add(new Item(weight * k, value * k, k));
                count -= k;
            }
        }

        // 初始化dp数组
        int[] dp = new int[capacity + 1];

        // 0-1背包处理
        for (Item item : newItems) {
            for (int j = capacity; j >= item.weight; j--) {
                    dp[j] = Math.max(dp[j], dp[j - item.weight] + item.value);
            }
        }

        return dp[capacity];
    }

    public static void main(String[] args) {
        // 示例测试
        int capacity = 10;
        List<Item> items = new ArrayList<>();
        items.add(new Item(2, 6, 3));  // 重量2，价值6，数量3
        items.add(new Item(3, 10, 2)); // 重量3，价值10，数量2
        items.add(new Item(1, 2, 5));  // 重量1，价值2，数量5

        int maxValue = knapsack(capacity, items);
        System.out.println("最大价值：" + maxValue); // 输出：最大价值：32
    }
}