import java.util.PriorityQueue;
import java.util.Random;

/**
 * SP11
 * Created by Ketki Mahajan
 * Meghna Mathur
 **/
public class KLargestElements {

    public static Random random = new Random();
    public static void main(String[] args) {
        int n = 16000000;

        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = i;
        }
        Shuffle.shuffle(arr);
        int choice=1;
        Timer timer = new Timer();
        switch(choice) {
            case 1:
                select(arr,n/2);
                break;
            case 2:
               pqSelect(arr, n/2);
                break;
        }
        timer.end();
        System.out.println("Choice: " + choice + "\n" + timer);

    }

    private static void select(int[] arr, int k) {
        select(arr,0,arr.length,k);
    }

    private static int select(int[] arr, int start, int end, int k)
    {
        if(end <= 99){
            insertionSort(arr, start, start + end - 1);
         //   System.out.println(arr[start + end - k]);
            return start + end - k;

        }else{


            int q= randomizedPartition(arr, start, start+end-1);
            int left= q-start;
            int right= end-left-1;
            if(right>= k)
            {
                return select(arr,q+1,right,k);
            }
            else if(right+1 == k)
            {
              //  System.out.println(arr[q]);
                return q;
            }
            else{
                return select(arr,start,left,k-right-1);
            }
        }

    }

    private static int randomizedPartition(int[] arr, int p, int r) {
        Random ran = new Random();
            int i = p + ran.nextInt((r-p)+1);
            int temp;
            temp = arr[i];
            arr[i] = arr[r];
            arr[r] = temp;
            return partition(arr, p, r);

    }
    public static int partition(int[] arr, int p, int r){

        int temp;
        int i;
        i=p-1;
        int x = arr[r];
        for(int j=p; j<r;j++){
            if(arr[j]<=x){
                i=i+1;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        temp = arr[i+1];
        arr[i+1] = arr[r];
        arr[r] = temp;

        return i+1;
    }


    private static void insertionSort(int[] arr, int start, int end) {
        for(int i=start+1; i <= end; i++)
        {
            int temp= arr[i];
            int j=i-1;
            while(j>=start && temp<arr[j]){
                arr[j+1]=arr[j];
                j--;
            }
            arr[j+1] = temp;
        }
    }
    private static void pqSelect(int[] arr, int k) {
        PriorityQueue<Integer> pq= new PriorityQueue<>(k);

        for(int i:arr){
            if(pq.size()<k){
                pq.offer(i);
            } else {
                if (i > pq.peek()) {
                    pq.offer(i);
                    pq.poll();
                }
            }
        }
       // System.out.println(pq.peek());
    }

    /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

        public void scale(int num) { elapsedTime /= num; }

        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }


    /** @author rbk : based on algorithm described in a book
     */


    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static<T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from  + 1;
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        public static<T> void shuffle(T[] arr, int from, int to) {
            int n = to - from  + 1;
            Random random = new Random();
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static<T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static<T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length-1, message);
        }

        public static<T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for(int i=from; i<=to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }
}
