class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return nums[b] - nums[a];   // Max Heap
            }
        });
        
        int[] ans = new int[nums.length - k + 1];
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            while (!pq.isEmpty() && pq.peek() < (i - k + 1)) {
                pq.poll();
            }
            pq.offer(i);
            if (i >= k - 1) {
                ans[idx] = nums[pq.peek()];
                idx++;
            }
        }
        return ans;
    }
}