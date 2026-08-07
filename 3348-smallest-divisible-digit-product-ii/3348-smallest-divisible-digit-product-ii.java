class Solution {
    static final int INF = Integer.MAX_VALUE / 2;
    int A, B, C, D;
    int dimB, dimC, dimD;
    int[][] contrib = new int[10][4];
    int[][] table; // table[v] = min #digits from {v..9} to cover a need, v = 2..9

    private int index(int a, int b, int c, int d) {
        return ((a * dimB + b) * dimC + c) * dimD + d;
    }

    public String smallestNumber(String num, long t) {
        long tt = t;
        int a = 0, b = 0, c = 0, d = 0;
        while (tt % 2 == 0) { tt /= 2; a++; }
        while (tt % 3 == 0) { tt /= 3; b++; }
        while (tt % 5 == 0) { tt /= 5; c++; }
        while (tt % 7 == 0) { tt /= 7; d++; }
        if (tt != 1) return "-1";
        A = a; B = b; C = c; D = d;
        dimB = B + 1; dimC = C + 1; dimD = D + 1;

        contrib[0] = new int[]{0,0,0,0};
        contrib[1] = new int[]{0,0,0,0};
        contrib[2] = new int[]{1,0,0,0};
        contrib[3] = new int[]{0,1,0,0};
        contrib[4] = new int[]{2,0,0,0};
        contrib[5] = new int[]{0,0,1,0};
        contrib[6] = new int[]{1,1,0,0};
        contrib[7] = new int[]{0,0,0,1};
        contrib[8] = new int[]{3,0,0,0};
        contrib[9] = new int[]{0,2,0,0};

        int totalStates = (A + 1) * (B + 1) * (C + 1) * (D + 1);
        table = new int[10][];
        int[] prev = new int[totalStates];
        Arrays.fill(prev, INF);
        prev[index(0,0,0,0)] = 0;

        for (int v = 9; v >= 2; v--) {
    int[] cur = new int[totalStates];
    Arrays.fill(cur, INF);        // <-- add this
    int[] cv = contrib[v];
    for (int aa = 0; aa <= A; aa++) {
        for (int bb = 0; bb <= B; bb++) {
            for (int cc = 0; cc <= C; cc++) {
                for (int dd = 0; dd <= D; dd++) {
                    int idx = index(aa, bb, cc, dd);
                    if (aa == 0 && bb == 0 && cc == 0 && dd == 0) { cur[idx] = 0; continue; }
                    int val = prev[idx];
                    int na = Math.max(0, aa - cv[0]);
                    int nb = Math.max(0, bb - cv[1]);
                    int nc = Math.max(0, cc - cv[2]);
                    int nd = Math.max(0, dd - cv[3]);
                    int cand = cur[index(na, nb, nc, nd)];
                    if (cand < INF && cand + 1 < val) val = cand + 1;
                    cur[idx] = val;
                }
            }
        }
    }
    table[v] = cur;
    prev = cur;
}

        int L0 = num.length();
        int[] cumA = new int[L0+1], cumB = new int[L0+1], cumC = new int[L0+1], cumD = new int[L0+1];
        int firstZero = L0;
        for (int i = 0; i < L0; i++) {
            int digit = num.charAt(i) - '0';
            if (digit == 0 && firstZero == L0) firstZero = i;
            int[] cv = contrib[digit];
            cumA[i+1] = cumA[i] + cv[0];
            cumB[i+1] = cumB[i] + cv[1];
            cumC[i+1] = cumC[i] + cv[2];
            cumD[i+1] = cumD[i] + cv[3];
        }

        if (firstZero == L0 && cumA[L0] >= A && cumB[L0] >= B && cumC[L0] >= C && cumD[L0] >= D) {
            return num;
        }

        int maxPivot = Math.min(L0 - 1, firstZero);
        for (int i = maxPivot; i >= 0; i--) {
            int startDigit = (i == firstZero) ? 1 : (num.charAt(i) - '0' + 1);
            int pa = cumA[i], pb = cumB[i], pc = cumC[i], pd = cumD[i];
            int suffixLen = L0 - 1 - i;
            for (int v = startDigit; v <= 9; v++) {
                int[] cv = contrib[v];
                int na = Math.max(0, A - pa - cv[0]);
                int nb = Math.max(0, B - pb - cv[1]);
                int nc = Math.max(0, C - pc - cv[2]);
                int nd = Math.max(0, D - pd - cv[3]);
                int m = table[2][index(na, nb, nc, nd)];
                if (m <= suffixLen) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append((char) ('0' + v));
                    sb.append(bestSuffix(suffixLen, na, nb, nc, nd));
                    return sb.toString();
                }
            }
        }

        int m0 = table[2][index(A, B, C, D)];
        int L = Math.max(L0 + 1, m0);
        return bestSuffix(L, A, B, C, D);
    }

    private String bestSuffix(int L, int a, int b, int c, int d) {
        int m = table[2][index(a, b, c, d)];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < L - m; i++) sb.append('1');

        int ra = a, rb = b, rc = c, rd = d;
        int lb = 2, slots = m;
        int[] specials = new int[m];
        for (int pos = 0; pos < m; pos++) {
            for (int v = lb; v <= 9; v++) {
                int[] cv = contrib[v];
                int na = Math.max(0, ra - cv[0]);
                int nb = Math.max(0, rb - cv[1]);
                int nc = Math.max(0, rc - cv[2]);
                int nd = Math.max(0, rd - cv[3]);
                int remain = slots - 1;
                int cost = table[v][index(na, nb, nc, nd)];
                if (cost <= remain) {
                    specials[pos] = v;
                    ra = na; rb = nb; rc = nc; rd = nd;
                    lb = v; slots = remain;
                    break;
                }
            }
        }
        for (int i = 0; i < m; i++) sb.append((char) ('0' + specials[i]));
        return sb.toString();
    }
}