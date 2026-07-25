class Solution {
    public int myAtoi(String s) {
        // Whitespace
        s = s.trim();
        // Base case
        if(s.isEmpty()){
            return 0;
        }
        // Signedness
        int i=0;
        int sign=1;
        if (s.charAt(i)== '-' || s.charAt(i)== '+'){
            sign =s.charAt(i) == '-' ? -1:1;
            i++;
        }
        // Conversion
        long num = 0;
        while(i<s.length() && Character.isDigit(s.charAt(i))){
            num = (num*10) + (s.charAt(i)-'0');
            if (num*sign>Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }else if (num*sign<Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }
            i++;
        }
        return (int)(sign*num);
    }
}