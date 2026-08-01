class Solution {
    Integer[][] dp;

    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        dp = new Integer[n][n];
        return solve(nums, 0, n - 1) >= 0;
    }

    private int solve(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        if (dp[left][right] != null) {
            return dp[left][right];
        }
        int takeLeft = nums[left] - solve(nums, left + 1, right);
        int takeRight = nums[right] - solve(nums, left, right - 1);
        return dp[left][right] = Math.max(takeLeft, takeRight);
    }
}