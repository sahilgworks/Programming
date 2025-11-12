public class leetcode_24_swap_nodes {

    public static void main(String[] args) {
        leetcode_24_swap_nodes obj = new leetcode_24_swap_nodes();
        obj.run();
    }

    private void run() {
        testcase(new int[]{1, 2, 3, 4, 5, 6});
        testcase(new int[]{1, 2, 3, 4});
        testcase(new int[]{1, 2, 3});
        testcase(new int[]{1});
        testcase(new int[]{});
    }

    private void testcase(int[] nums) {
        ListNode listNode = convertArrayToList(nums);
        System.out.println(listToString(new Solution().swapPairs(listNode)));
    }

    public class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    ListNode convertArrayToList(int[] arr) {
        ListNode head = new ListNode();
        ListNode prev = head;
        for (int num : arr) {
            ListNode temp = new ListNode(num);
            temp.next = null;
            prev.next = temp;
            prev = temp;
        }
        return head.next;
    }

    String listToString(ListNode head) {
        StringBuilder sb = new StringBuilder("\n");
        ListNode temp = head;
        while (temp != null) {
            sb.append(temp.val);
            sb.append(", ");
            temp = temp.next;
        }
        return sb.toString();
    }

    class Solution {

        public ListNode swapPairs(ListNode head) {
            if (head == null) {
                return head;
            }
            ListNode slow = head;
            ListNode fast = slow.next;
            head = (fast == null) ? slow : fast;
            ListNode prev = new ListNode(0);
            while (slow != null && fast != null) {
                ListNode temp = fast.next;
                fast.next = slow;
                slow.next = temp;
                prev.next = fast;
                prev = slow;
                slow = temp;
                fast = slow == null ? null : slow.next;

            }
            return head;
        }
    }
}
