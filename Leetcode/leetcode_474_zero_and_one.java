import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class leetcode_474_zero_and_one {

    public static void main(String[] args) {
        SolutionV1 solution = new SolutionV1();
        assertEquals(4, solution.findMaxForm(new String[]{"10", "0001", "111001", "1", "0"}, 5, 3));
        assertEquals(2, solution.findMaxForm(new String[]{"10", "0", "1"}, 1, 1));
    }

    static class SolutionV1 {
        public int findMaxForm(String[] strs, int m, int n) {
            //lets first count 0's and 1's and create a new object for each string
            Count[] counts = parse(strs);
            return findMaxForm(counts, m, n);
        }

        public int findMaxForm(Count[] counts, int m, int n) {

            return 0;
        }

        Count[] parse(String[] array) {
            Count[] result = new Count[array.length];
            int i = 0;
            for (String s : array) {
                result[i++] = parse(s);
            }
            return result;
        }

        Count parse(String s) {
            int z = 0;
            int o = 0;
            for (char ch : s.toCharArray()) {
                if (ch == '1') {
                    o++;
                } else {
                    z++;
                }
            }
            return new Count(z, o);
        }

        class Count {
            int z;
            int o;
            int sum;

            Count(int z, int o) {
                this.z = z;
                this.o = o;
                this.sum  = z+o;
            }
        }

    }

}
