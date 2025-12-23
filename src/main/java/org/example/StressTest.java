package org.example;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class StressTest {
    public static void main(String[] args) {
        System.out.println("开始压力测试...");
        
        // 测试基本背包问题，物品数量>1000，容量>10000
        runBasicKnapsackTest();
        
        // 测试完全背包问题
        runUnboundedKnapsackTest();
        
        // 测试多重背包问题
        runMultipleKnapsackTest();
        
        // 测试混合背包问题
        runMixedKnapsackTest();
        
        // 测试多维背包问题
        runMultiDimensionalKnapsackTest();
        
        // 测试背包逆求组合
        runBacktrackingKnapsackTest();
    }
    
    private static void runBasicKnapsackTest() {
        System.out.println("\n=== 基本01背包压力测试 ===");
        
        // 生成测试数据：物品数量1500，背包容量15000
        int itemCount = 1500;
        int capacity = 15000;
        
        int[] values = new int[itemCount];
        int[] weights = new int[itemCount];
        
        Random rand = new Random(42); // 使用固定种子以确保结果可重现
        
        // 随机生成物品的价值和重量
        for (int i = 0; i < itemCount; i++) {
            values[i] = rand.nextInt(100) + 1; // 价值范围 [1, 100]
            weights[i] = rand.nextInt(50) + 1; // 重量范围 [1, 50]
        }
        
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 运行基本背包算法
        int result = BasicKnapsack.solve(values, weights, capacity);
        
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        
        System.out.println("最大价值: " + result);
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
        
        // 验证结果合理性
        validateResult(result, values, capacity);
    }
    
    private static void runUnboundedKnapsackTest() {
        System.out.println("\n=== 完全背包压力测试 ===");
        
        // 生成测试数据：物品数量1200，背包容量12000
        int itemCount = 1200;
        int capacity = 12000;
        
        List<UnboundedKnapsack.Item> items = new ArrayList<>();
        Random rand = new Random(42);
        
        // 随机生成物品
        for (int i = 0; i < itemCount; i++) {
            int weight = rand.nextInt(40) + 1; // 重量范围 [1, 40]
            int value = rand.nextInt(80) + 1;  // 价值范围 [1, 80]
            items.add(new UnboundedKnapsack.Item(weight, value));
        }
        
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 运行完全背包算法
        int result = UnboundedKnapsack.solve(items, capacity);
        
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        
        System.out.println("最大价值: " + result);
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runMultipleKnapsackTest() {
        System.out.println("\n=== 多重背包压力测试 ===");
        
        // 生成测试数据：物品数量1100，背包容量11000
        int itemCount = 1100;
        int capacity = 11000;
        
        List<MultipleKnapsack.Item> items = new ArrayList<>();
        Random rand = new Random(42);
        
        // 随机生成物品，限制数量以避免二进制拆分后物品过多
        for (int i = 0; i < itemCount; i++) {
            int weight = rand.nextInt(30) + 1;  // 重量范围 [1, 30]
            int value = rand.nextInt(60) + 1;   // 价值范围 [1, 60]
            int count = rand.nextInt(10) + 1;   // 数量范围 [1, 10]
            items.add(new MultipleKnapsack.Item(weight, value, count));
        }
        
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        System.out.println("估算拆分后物品数量: 取决于数量限制");
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 运行多重背包算法
        int result = MultipleKnapsack.knapsack(capacity, items);
        
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        
        System.out.println("最大价值: " + result);
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runMixedKnapsackTest() {
        System.out.println("\n=== 混合背包压力测试 ===");
        
        // 生成测试数据：物品数量1000，背包容量10000
        int itemCount = 1000;
        int capacity = 10000;
        
        List<MixedKnapsack.Item> items = new ArrayList<>();
        Random rand = new Random(42);
        
        // 随机生成物品，包括01背包、完全背包和多重背包物品
        for (int i = 0; i < itemCount; i++) {
            int weight = rand.nextInt(25) + 1;  // 重量范围 [1, 25]
            int value = rand.nextInt(50) + 1;   // 价值范围 [1, 50]
            // 随机决定物品类型：-1表示完全背包（无限），1表示01背包，其他正数表示多重背包
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
        System.out.println("物品类型: 01背包、完全背包、多重背包混合");
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 运行混合背包算法
        int result = MixedKnapsack.solve(capacity, items);
        
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        
        System.out.println("最大价值: " + result);
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runMultiDimensionalKnapsackTest() {
        System.out.println("\n=== 多维背包压力测试 ===");
        
        // 生成测试数据：物品数量300，3维约束，每维容量100
        int itemCount = 300;
        int dimensions = 3;
        int[] limits = {100, 100, 100};
        
        int[][] items = new int[itemCount][dimensions + 1]; // 第0维为价值，后面为各维度重量
        Random rand = new Random(42);
        
        // 随机生成物品
        for (int i = 0; i < itemCount; i++) {
            items[i][0] = rand.nextInt(100) + 1; // 价值范围 [1, 100]
            for (int j = 1; j <= dimensions; j++) {
                items[i][j] = rand.nextInt(20) + 1; // 各维度重量范围 [1, 20]
            }
        }
        
        System.out.println("物品数量: " + itemCount);
        System.out.println("维度数: " + dimensions);
        System.out.println("各维容量限制: " + java.util.Arrays.toString(limits));
        System.out.println("注意: 3维背包的状态空间为 " + (limits[0]+1) + "*" + (limits[1]+1) + "*" + (limits[2]+1) + "=" + (limits[0]+1)*(limits[1]+1)*(limits[2]+1));
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 运行多维背包算法
        int result = MultiDimensionalKnapsack.solve(items, limits);
        
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        
        System.out.println("最大价值: " + result);
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void runBacktrackingKnapsackTest() {
        System.out.println("\n=== 背包逆求组合压力测试 ===");
        
        // 生成测试数据：物品数量1200，背包容量12000
        int itemCount = 1200;
        int capacity = 12000;
        
        int[] values = new int[itemCount];
        int[] weights = new int[itemCount];
        
        Random rand = new Random(42); // 使用固定种子以确保结果可重现
        
        // 随机生成物品的价值和重量
        for (int i = 0; i < itemCount; i++) {
            values[i] = rand.nextInt(50) + 1; // 价值范围 [1, 50]
            weights[i] = rand.nextInt(30) + 1; // 重量范围 [1, 30]
        }
        
        // 先运行基本背包算法获取最大价值
        int maxValue = BasicKnapsack.solve(values, weights, capacity);
        int targetValue = maxValue / 2; // 目标价值设为最大价值的一半
        
        System.out.println("物品数量: " + itemCount);
        System.out.println("背包容量: " + capacity);
        System.out.println("目标价值: " + targetValue);
        System.out.println("理论最大价值: " + maxValue);
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 运行背包逆求组合算法
        List<Integer> result = BacktrackingKnapsack.solve(values, weights, capacity, targetValue);
        
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        
        if (result != null) {
            System.out.println("找到组合，包含物品数量: " + result.size());
            System.out.println("物品索引示例: " + (result.size() > 10 ? result.subList(0, Math.min(10, result.size())) : result));
        } else {
            System.out.println("无法达到目标价值");
        }
        System.out.println("执行时间: " + (endTime - startTime) + "ms");
    }
    
    private static void validateResult(int result, int[] values, int capacity) {
        // 计算理论最大可能价值（如果容量足够装下所有物品）
        int maxValuePossible = sum(values);
        System.out.println("理论最大可能价值（容量足够）: " + maxValuePossible);
        
        // 检查结果是否在合理范围内
        if (result <= maxValuePossible && result >= 0) {
            System.out.println("✓ 结果在合理范围内");
        } else {
            System.out.println("✗ 结果不合理");
        }
    }
    
    private static int sum(int[] arr) {
        int total = 0;
        for (int value : arr) {
            total += value;
        }
        return total;
    }
}