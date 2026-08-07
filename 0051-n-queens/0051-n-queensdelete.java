class Solution {

    List<List<String>> ans = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }

        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1]; // row - col + n - 1
        boolean[] diag2 = new boolean[2 * n - 1]; // row + col
        backtrack(0, board, col, diag1, diag2, n);
        return ans;
    }

    private void backtrack(int row, char[][] board,
                           boolean[] col,
                           boolean[] diag1,
                           boolean[] diag2,
                           int n) {

        if (row == n) {
            List<String> temp = new ArrayList<>();
            for (char[] r : board) {
                temp.add(new String(r));
            }
            ans.add(temp);
            return;
        }

        for (int c = 0; c < n; c++) {

            int d1 = row - c + n - 1;
            int d2 = row + c;

            if (col[c] || diag1[d1] || diag2[d2])
                continue;

            board[row][c] = 'Q';
            col[c] = true;
            diag1[d1] = true;
            diag2[d2] = true;

            backtrack(row + 1, board, col, diag1, diag2, n);

            board[row][c] = '.';
            col[c] = false;
            diag1[d1] = false;
            diag2[d2] = false;
        }
    }
}