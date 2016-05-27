
import java.util.Arrays;

public class Pasquicklina {

    public static void quicksort(int[] numbers, int threads) {



    }
  
    public static void quicksort(int[] numbers) {

        quicksort(numbers, 0, numbers.length - 1);

    }

    private static void quicksort(int[] a, int l, int r) {

        if (l < r) {

            int i = l, j = r - 1;

            int pivot = r;

            do {

                while (a[i] < a[pivot]) i++;
                while (j >= l && a[j] > a[pivot]) j--;

                if (i < j) {
                    swap(a, i++, j--);
                }

            } while (i < j);

            if (a[i] < a[pivot]) i++;
            swap(a, i, r);

            quicksort(a, l, i - 1);
            quicksort(a, i + 1, r);

        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {

        int[] a = {191, 119, 50, 208, 19, 215, 470, 31, 19, 25, 119, 356, 35, 374, 5, 331, 463, 409};

        quicksort(a);

        System.out.println(Arrays.toString(a));

    }

}
