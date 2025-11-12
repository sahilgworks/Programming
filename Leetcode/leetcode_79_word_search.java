public class leetcode_79_word_search {

    public static void main(String[] args) {
        char[][] board = new char[][]
                {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        System.out.println(new Solution().exist(board, "ABCCED"));
        System.out.println(new Solution().exist(board, "ABCC"));
        System.out.println(new Solution().exist(board, "A"));
        System.out.println(new Solution().exist(board, "X"));
        System.out.println(new Solution().exist(board, "Y"));
        System.out.println(new Solution().exist(board, "AB"));
        System.out.println(new Solution().exist(board, "AS"));

        board = new char[][]
                {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        System.out.println(new Solution().exist(board, "SEE"));
    }

    static class Solution {

        public boolean exist(char[][] board, String word) {
            return exist(board, word.toCharArray());
        }

        public boolean exist(char[][] board, char[] word) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == word[0]) {
                        if(exist(board, board.length, board[0].length, i, j, word, word.length, 0)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        char invalid = 0;

        public boolean exist(char[][] board, int m, int n, int i, int j, char[] word, int len, int k) {
            if (k == len) {
                return true;
            }
            if (i < 0 || i >= m || j < 0 || j >= n) {
                return false;
            }
            if (board[i][j] == invalid) {
                return false;
            }
            if (board[i][j] == word[k]) {
                board[i][j] = invalid;
                if (
                        exist(board, m, n, i + 1, j, word, len, k + 1) ||
                                exist(board, m, n, i, j + 1, word, len, k + 1) ||
                                exist(board, m, n, i - 1, j, word, len, k + 1) ||
                                exist(board, m, n, i, j - 1, word, len, k + 1)) {
                    board[i][j] = word[k];
                    return true;
                } else {
                    board[i][j] = word[k];
                    return false;
                }
            }
            return false;
        }
    }
}
