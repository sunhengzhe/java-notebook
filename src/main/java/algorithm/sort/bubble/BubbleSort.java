package algorithm.sort.bubble;

import java.util.stream.IntStream;

public class BubbleSort {

    public static void sort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }

        // #2 有序区边界：记录有序区的边界
        int sortedBorder = arr.length - 1;
        int lastExchangeIndex = 0;

        for (int i = 0; i < arr.length; i++) {
            // #1 有序即停：是否已经有序（没有交换）
            boolean isSorted = true;

            for (int j = 0; j < sortedBorder; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;

                    // #1 有序即停：有交换，非有序
                    isSorted = false;
                    // #2 有序区边界：记录最后一次交换的位置
                    lastExchangeIndex = j;
                }
            }

            // #2 有序区边界：设置有序区边界
            sortedBorder = lastExchangeIndex;

            // #1 有序即停：提前退出
            if (isSorted) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 2, 1, 3, 4, 1, 8};
        sort(arr);
        IntStream.of(arr).forEach(System.out::print);
    }
}
