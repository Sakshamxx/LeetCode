class Solution {
    public int maxProduct(int n) {
        int fmax = 0;
        int smax = 0;
        while(n>0){
            int rem = n%10;
            n=n/10;
            if (rem > fmax){
                smax = fmax;
                fmax = rem;
            }
            else if(rem > smax && rem <= fmax){
                smax = rem;
            }
        }
        return smax*fmax;
    }
}