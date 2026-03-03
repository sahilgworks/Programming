import static org.junit.Assert.assertEquals;

import java.util.*;
import java.lang.*;

public class leetcode_984_rotten_oranges {

    public static void main(String[] args) {
        SolutionV1 solution = new SolutionV1();
        assertEquals(4, solution.orangesRotting(new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}));
        assertEquals(-1, solution.orangesRotting(new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}));
        assertEquals(0, solution.orangesRotting(new int[][]{{0, 2}}));
    }

    static class SolutionV2 {
        int rotten = 2;
        int fresh = 1;
        int none = 0;

        public int orangesRotting(int[][] grid) {
            //we will apply a bfs, and iteration is a next minute
            //since we can have multipe rotten  oranges at given time, we will process them together
            //lets first collect all rotten oranges
            HashSet<String> queue = new HashSet<>();

            int m = grid.length;
            int n = grid[0].length;
            int max = Integer.MAX_VALUE;
            int[][] dist = new int[m][n];
            int result = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == rotten) {
                        dist[i][j] = 0;
                    } else if (grid[i][j] == fresh) {
                        dist[i][j] = max;
                    } else {
                        dist[i][j] = -1;
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == rotten) {
                        dfs(grid, dist, m, n, i, j);
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    result = Math.max(dist[i][j], result);
                }
            }
            if (result == max) {
                return -1;
            } else {
                return result;
            }
        }

        void dfs(int[][] grid, int[][] dist, int m, int n, int i, int j) {
            int cd = dist[i][j];
            int cdn = cd + 1;
            if ((i + 1) < m && dist[i + 1][j] > cdn) {
                dist[i + 1][j] = cdn;
                grid[i + 1][j] = rotten;
            }
            if ((i - 1) >= 0 && dist[i - 1][j] > cdn) {
                dist[i - 1][j] = cdn;
                grid[i - 1][j] = rotten;
            }
            if ((j + 1) < n && dist[i][j + 1] > cdn) {
                dist[i][j + 1] = cdn;
                grid[i][j + 1] = rotten;
            }
            if ((j - 1) >= 0 && dist[i][j - 1] > cdn) {
                dist[i][j - 1] = cdn;
                grid[i][j - 1] = rotten;
            }
        }
    }

    static class SolutionV1 {
        public int orangesRotting(int[][] grid) {
            //we will apply a bfs, and iteration is a next minute
            //since we can have multipe rotten  oranges at given time, we will process them together
            //lets first collect all rotten oranges
            HashSet<String> queue = new HashSet<>();
            int iFresh = 0;
            int iRotten = 0;
            int fFresh = 0;
            int fRotten = 0;
            int rotten = 2;
            int fresh = 1;
            int none = 0;
            int m = grid.length;
            int n = grid[0].length;
            int result = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == rotten) {
                        queue.add(i + "_" + j);
                        iRotten++;
                    } else if (grid[i][j] == fresh) {
                        iFresh++;
                    }
                }
            }
            HashSet<String> next = new HashSet<>();
            boolean anyRotten = false;
            int i;
            int j;
            while (!queue.isEmpty()) {
                next.clear();
                anyRotten = false;
                for (String value : queue) {
                    i = Integer.parseInt(value.split("_")[0]);
                    j = Integer.parseInt(value.split("_")[1]);
                    if (grid[i][j] != none) {
                        if ((i + 1) < m && grid[i + 1][j] == fresh) {
                            next.add((i + 1) + "_" + j);
                        }
                        if ((i - 1) >= 0 && grid[i - 1][j] == fresh) {
                            next.add((i - 1) + "_" + j);
                        }
                        if ((j + 1) < n && grid[i][j + 1] == fresh) {
                            next.add((i) + "_" + (j + 1));
                        }
                        if ((j - 1) >= 0 && grid[i][j - 1] == fresh) {
                            next.add((i) + "_" + (j - 1));
                        }
                        if (grid[i][j] == fresh) {
                            anyRotten = true;
                            fRotten++;
                        }
                        grid[i][j] = rotten;
                    }
                }
                queue.clear();
                queue.addAll(next);
                if (anyRotten) {
                    result++;
                }
            }
            if (fRotten != iFresh) {
                return -1;
            }
            return result;
        }
    }

}
