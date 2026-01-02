package org.example;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class StressTest {
    public static void main(String[] args) {
        runBasicKnapsackTest();
        runUnboundedKnapsackTest();
        runMultipleKnapsackTest();
        runMixedKnapsackTest();
        runMultiDimensionalKnapsackTest();
        runBacktrackingKnapsackTest();
    }
    
    private static void runBasicKnapsackTest() {
        System.out.println("\n基本01背包压力测试");
        int itemCount = 1500;
        int capacity = 15000;
        int[] values = new int[itemCount];
        int[] weights = new int[itemCount];
        Random rand = new Random(42); // 使用固定种子以确保结果可重现
        for (int i = 0; i < itemCount; i++) {
            values[i] = rand.nextInt(100) + 1; // 价值范围 [1, 100]
            weights[i] = rand.nextInt(50) + 1; // 重量范围 [1, 50]
        }
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        long startTime = System.currentTimeMillis();
        int result = BasicKnapsack.solve(values, weights, capacity);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runUnboundedKnapsackTest() {
        System.out.println("\n完全背包压力测试");
        int itemCount = 1200;
        int capacity = 12000;
        List<UnboundedKnapsack.Item> items = new ArrayList<>();
        Random rand = new Random(42);
        for (int i = 0; i < itemCount; i++) {
            int weight = rand.nextInt(40) + 1; // 重量范围 [1, 40]
            int value = rand.nextInt(80) + 1;  // 价值范围 [1, 80]
            items.add(new UnboundedKnapsack.Item(weight, value));
        }
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        long startTime = System.currentTimeMillis();
        int result = UnboundedKnapsack.solve(items, capacity);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runMultipleKnapsackTest() {
        System.out.println("\n多重背包压力测试");
        int itemCount = 1100;
        int capacity = 11000;
        List<MultipleKnapsack.Item> items = new ArrayList<>();
        Random rand = new Random(42);
        for (int i = 0; i < itemCount; i++) {
            int weight = rand.nextInt(30) + 1;  // 重量范围 [1, 30]
            int value = rand.nextInt(60) + 1;   // 价值范围 [1, 60]
            int count = rand.nextInt(10) + 1;   // 数量范围 [1, 10]
            items.add(new MultipleKnapsack.Item(weight, value, count));
        }
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        System.out.println("估算拆分后物品数量: 取决于数量限制");
        long startTime = System.currentTimeMillis();
        int result = MultipleKnapsack.knapsack(capacity, items);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runMixedKnapsackTest() {
        System.out.println("\n混合背包压力测试");
        int itemCount = 1000;
        int capacity = 10000;
        List<MixedKnapsack.Item> items = new ArrayList<>();
        Random rand = new Random(42);
        for (int i = 0; i < itemCount; i++) {
            int weight = rand.nextInt(25) + 1;  // 重量范围 [1, 25]
            int value = rand.nextInt(50) + 1;   // 价值范围 [1, 50]
            int type = rand.nextInt(12); // 0-9表示多重背包数量，10表示01背包，11表示完全背包
            int count;
            if (type == 11) {
                count = -1; // 完全背包
            } else if (type == 10) {
                count = 1; // 01背包
            } else {
                count = type + 1; // 多重背包，数量为type+1
            }
            items.add(new MixedKnapsack.Item(weight, value, count));
        }
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        long startTime = System.currentTimeMillis();
        int result = MixedKnapsack.solve(capacity, items);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runMultiDimensionalKnapsackTest() {
        System.out.println("\n多维背包压力测试");
        int itemCount = 300;
        int dimensions = 3;
        int[] limits = {100, 100, 100};
        int[][] items = new int[itemCount][dimensions + 1]; // 第0维为价值，后面为各维度重量
        Random rand = new Random(42);
        for (int i = 0; i < itemCount; i++) {
            items[i][0] = rand.nextInt(100) + 1; // 价值范围 [1, 100]
            for (int j = 1; j <= dimensions; j++) {
                items[i][j] = rand.nextInt(20) + 1; // 各维度重量范围 [1, 20]
            }
        }
        System.out.println("物品数量: " + itemCount);
        System.out.println("维度数: " + dimensions);
        System.out.println("背包状态数"+limits[0]*limits[1]*limits[2]);
        long startTime = System.currentTimeMillis();
        int result = MultiDimensionalKnapsack.solve(items, limits);
        long endTime = System.currentTimeMillis();
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runBacktrackingKnapsackTest() {
        System.out.println("\n背包逆求组合压力测试");
        int itemCount = 1200;
        int capacity = 12000;
        int[] values = new int[itemCount];
        int[] weights = new int[itemCount];
        Random rand = new Random(42); // 使用固定种子以确保结果可重现
        for (int i = 0; i < itemCount; i++) {
            values[i] = rand.nextInt(50) + 1; // 价值范围 [1, 50]
            weights[i] = rand.nextInt(30) + 1; // 重量范围 [1, 30]
        }
        int maxValue = BasicKnapsack.solve(values, weights, capacity);
        int targetValue = maxValue / 2; // 目标价值设为最大价值的一半
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        long startTime = System.currentTimeMillis();
        List<Integer> result = BacktrackingKnapsack.solve(values, weights, capacity, targetValue);
        long endTime = System.currentTimeMillis();
        if (result != null) {
            System.out.println("找到组合，包含物品数量: " + result.size());
        } else {
            System.out.println("无法达到目标价值");
        }
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
}