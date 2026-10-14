package airportflow.dsa.randomized;

import java.util.Comparator;
import java.util.Random;

/**
 * Randomized QuickSort Algorithm.
 *
 * Sorts an array using the QuickSort algorithm with randomized pivot selection.
 * Average time complexity: O(n log n), worst case: O(n^2) but unlikely with randomization.
 *
 * AirportFlow use case: Rank/sort flight records by any field (flight number,
 * scheduled time, passenger count) with unbiased pivot choice.
 */
public class RandomizedQuickSort {

    private static final Random RANDOM = new Random();

    /**
     * Sorts an array of integers using randomized quicksort.
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quicksort(arr, 0, arr.length - 1);
    }

    private static void quicksort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            quicksort(arr, low, pivotIndex - 1);
            quicksort(arr, pivotIndex + 1, high);
        }
    }

    private static int randomizedPartition(int[] arr, int low, int high) {
        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
        return partition(arr, low, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Sorts an array of Comparable objects using randomized quicksort.
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quicksort(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<T>> void quicksort(T[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high);
            quicksort(arr, low, pivotIndex - 1);
            quicksort(arr, pivotIndex + 1, high);
        }
    }

    private static <T extends Comparable<T>> int randomizedPartition(T[] arr, int low, int high) {
        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
        return partition(arr, low, high);
    }

    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Sorts an array using a custom Comparator.
     *
     * @param arr       the array to sort
     * @param comparator the comparison function
     */
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quicksort(arr, 0, arr.length - 1, comparator);
    }

    private static <T> void quicksort(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = randomizedPartition(arr, low, high, comparator);
            quicksort(arr, low, pivotIndex - 1, comparator);
            quicksort(arr, pivotIndex + 1, high, comparator);
        }
    }

    private static <T> int randomizedPartition(T[] arr, int low, int high, Comparator<T> comparator) {
        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
        return partition(arr, low, high, comparator);
    }

    private static <T> int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }
}
