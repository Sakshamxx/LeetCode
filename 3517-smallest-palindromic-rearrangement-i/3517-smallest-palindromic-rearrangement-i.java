class Solution {
    public String smallestPalindrome(String s) {
        // Take the first half
        String firstHalf = s.substring(0, s.length() / 2);
        // Sort the first half
        char[] chars = firstHalf.toCharArray();
        Arrays.sort(chars);
        String left = new String(chars);
        // Build the answer
        StringBuilder ans = new StringBuilder(left);
        // Add middle character if length is odd
        if (s.length() % 2 != 0) {
            ans.append(s.charAt(s.length() / 2));
        }
        // Add reverse of left half
        ans.append(new StringBuilder(left).reverse());
        return ans.toString();
    }
}