import java.util.*;
import java.lang.*;

class HeapSort {

    private static int[] input = new int[]{4, 9, 10, 3, 2, 1, 0, -1, -20, -5, 5};

    public static void main(String[] args) {
        sort(input);
        System.out.println(Arrays.toString(input));
    }

    private static void sort(int[] input) {
        MaxHeap maxHeap = new MaxHeap(input);
        maxHeap.sort();
    }

    static class MaxHeap {

        final int[] array;
        final int len;
        int heapSize;

        MaxHeap(int[] array) {
            this.array = array;
            this.len = array.length;
        }

        void buildMaxHeap() {
            heapSize = len;
            for (int i = len / 2; i >= 0; i--) {
                heapify(i);
            }
        }

        void sort() {
            buildMaxHeap();
            System.out.println(Arrays.toString(input));
            for (int i = len - 1; i > 0; i--) {
                swap(0, i);
                heapSize--;
                heapify(0);
            }
        }

        private void heapify(int index) {
            int largest = index;
            int left = leftC(index);
            if (left < heapSize && array[largest] < array[left]) {
                largest = left;
            }
            int right = rightC(index);
            if (right < heapSize && array[largest] < array[right]) {
                largest = right;
            }
            swap(largest, index);
        }

        private void swap(int a, int b) {
            if (a != b) {
                int temp = array[a];
                array[a] = array[b];
                array[b] = temp;
            }
        }

        private int parent(int i) {
            return (i - 1) / 2;
        }

        private int leftC(int i) {
            return 2 * i + 1;
        }

        private int rightC(int i) {
            return 2 * i + 2;
        }
    }

}