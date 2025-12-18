package org.example;

public class MultiDimensionalKnapsack {
    private static int solve(int[][] items, int[] limits) {
            // 预计算总状态数和偏移量
            int k = limits.length;
            int totalStates = 1;
            int[] offsets = new int[k];
            offsets[0] = 1;
            totalStates *= (limits[0] + 1);
            for (int i = 1; i < k; i++) {
                offsets[i] = offsets[i - 1] * (limits[i - 1] + 1);//上一维度的值加上（当前维度的值加上额外的0）
                totalStates *= (limits[i] + 1);
            }
            int[] dp = new int[totalStates];//总共有totalStates种状态

            // 处理每个物品
            for (int[] item : items) {
                for (int state = totalStates - 1; state >= 0; state--) {
                    boolean feasible = true;
                    int prevState = state;//记录选了当前物品后，减少物品占用的容量后的状态
                    // 检查并计算前一个状态
                    for (int d = 0; d < k; d++) {
                        int dimValue = (state / offsets[d]) % (limits[d] + 1);
                        if (dimValue < item[d+1]) {
                            feasible = false;//当前状态无法容纳该物品
                            break;
                        }
                        prevState -= item[d+1] * offsets[d];
                    }
                    if (feasible && prevState >= 0) {
                        dp[state] = Math.max(dp[state], dp[prevState] + item[0]);
                    }
                }
            }
            return dp[totalStates - 1];
    }
    public static void main(String[] args) {
        //第一个维度为价值
        int[][] items = {{10,2,3},{15,3,4},{20,4,2}};
        int[] limits = {6,7};
        System.out.println(solve(items, limits));

        items = new int[][]{{3,2,1},{4,3,2},{5,4,3},{8,5,4}};
        limits = new int[]{7,6};
        System.out.println(solve(items, limits));

        items = new int[][]{{60 ,30, 20, 100},{70,40, 10, 150 },{ 50,20, 30, 200},{90,50, 25, 180 },{40,25, 15, 120 }};
        limits = new int[]{100,50,300};
        System.out.println(solve(items, limits));

        items = new int[][]{{300, 200, 80, 100},{250, 120, 300, 350},{ 50, 250, 200,100},{150, 100, 150,250},{400,300, 70, 200}};
        limits = new int[]{500,200,400};
        System.out.println(solve(items, limits));

        items = new int[][]{
                {200,30, 4, 100, 300},
                {250,40, 8, 150, 200},
                {300,50, 6, 200, 400},
                {100,20, 2, 50, 100},
                {350,60, 10, 250, 350}
        };
        limits = new int[]{80, 16, 500, 1000};
        System.out.println(solve(items, limits));
    }
}
