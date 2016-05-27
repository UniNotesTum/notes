import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;

public class Pasquicklina {

    public static void quicksort(int[] numbers, int threads) {

        Partitioner[] threadArray = new Partitioner[threads];

        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new Partitioner(numbers, threadArray);
        }

        Barrier barrier = new Barrier(2);

        threadArray[0].init(0, numbers.length - 1, 0, threadArray.length, barrier);

        barrier.barrierWait();

    }

    private static int partition(int[] a, int l, int r) {

        int i = l - 1, j = r;

        int pivot = r;

        do {

            do {
                i++;
            } while (a[i] < a[pivot]);

            do {
                j--;
            } while (j >= l && a[j] > a[pivot]);

            if (i < j) {
                swap(a, i, j);
            }

        } while (i < j);

        swap(a, i, r);

        return i;

    }
  
    public static void quicksort(int[] numbers) {

        quicksort(numbers, 0, numbers.length - 1);

    }

    private static void quicksort(int[] a, int l, int r) {

        if (l < r) {

            int i = partition(a, l, r);

            quicksort(a, l, i - 1);
            quicksort(a, i + 1, r);

        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static class Partitioner extends Thread {

        private static final int PARTITION = 1, RECURSIVE = 0;

        int l = 0, r = 0, leftThread = 0, rightThread = 0;

        Barrier barrier;

        Partitioner[] threads;

        int[] a;

        int state = 0, hop = 0, number = RECURSIVE;

        Partitioner(int[] a, Partitioner[] threads) {
            this.a = a;
            this.threads = threads;
        }

        private void init(int l, int r, int leftThread, int rightThread, Barrier barrier) {
            this.l = l;
            this.r = r;
            this.leftThread = leftThread;
            this.rightThread = rightThread;
            this.barrier = barrier;
            this.state = RECURSIVE;
            this.start();
        }

        private void initPartition(int l, int r, int hop, int number) {
            this.l = l;
            this.r = r;
            this.hop = hop;
            this.number = number;
            this.state = PARTITION;
            this.start();
        }


        private void partitionHop(int[] a, int l, int r, int hop, int number) {

            int i = l + number, j = i;

            while (j + hop < r) j += hop;

            int pivot = r;

            do {

                while (i < r && a[i] < a[pivot]) {
                    i += hop;
                }

                while (j >= l && a[j] > a[pivot]) {
                    j -= hop;
                }

                if (i < j) {
                    swap(a, i, j);
                    i += hop;
                    j -= hop;
                }

            } while (i < j);

        }

        public void run() {

            if (state == PARTITION) {

                partitionHop(a, l, r, hop, number);

                return;

            }

            if (l < r) {

                int countThreads = rightThread - leftThread;


                for (int q = 1; q < countThreads; q++) {

                    if (l + q > r) break;

                    if (threads[leftThread + q].getState() == State.TERMINATED) {
                        threads[leftThread + q] = new Partitioner(a, threads);
                    }

                    threads[leftThread + q].initPartition(l, r, countThreads, q);

                }

                partitionHop(a, l, r, countThreads, 0);

                for (int i =  leftThread + 1; i < rightThread; i++) {
                    try {
                        threads[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int left = l, right = r;

                while (a[left] < a[r]) left++;
                while (right > l && a[right] > a[r]) right--;

                int i = partition(a, left, right);

                if (countThreads > 1) {
                    int mid = leftThread + (rightThread - leftThread) / 2;

                    Barrier bar = new Barrier(2);

                    if (threads[mid].getState() == State.TERMINATED) {
                        threads[mid] = new Partitioner(a, threads);
                    }

                    threads[mid].init(i + 1, r, mid, rightThread, bar);
                    r = i - 1;
                    rightThread = mid;

                    Barrier temp = barrier;
                    barrier = bar;

                    run();

                    barrier = temp;

                } else {
                    Barrier tempBar = barrier;
                    barrier = null;
                    int temp = r;
                    r = i - 1;
                    run();
                    r = temp;
                    l = i + 1;
                    run();
                    barrier = tempBar;
                }

            }

            if (barrier != null) barrier.barrierWait();

        }

    }

    public static void main(String[] args) {

        int[] a = {3, 2, 5, 1, 12, 0, 1, 2, 3, 4};

        quicksort(a, 2);

        System.out.println(Arrays.toString(a));

    }

}
