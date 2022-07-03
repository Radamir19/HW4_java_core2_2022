package org.example;

public class TestThread {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];

    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            arr[i] = 1;

        }

        TestThread test = new TestThread();
        long single = methodA();

        long multi = methodB();

        increase(single,multi);

    }

    private static long methodA(){
        long start = System.currentTimeMillis();
         for (int i = 0; i < size; i++) {
             arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
             System.currentTimeMillis();

         }
         long singleTime = System.currentTimeMillis() - start;

         System.out.printf("single thread time: %d%n", singleTime);

         return singleTime;
    }

    private static long methodB(){
        float[] a = new float[h];
        float[] b = new float[h];

        long start = System.currentTimeMillis();

        System.arraycopy(arr, 0, a, 0, h);
        System.arraycopy(arr, 0, b, 0, h);

        Thread t1 = new Thread();
        Thread t2 = new Thread();

        t1.start();
        t2.start();

        try{
           t1.join();
           t1.join();
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }

        System.arraycopy(a,0,arr,0,h);
        System.arraycopy(b,0,arr,a.length,b.length);

        long multiTime = System.currentTimeMillis() - start;

        System.out.printf("multi thread time: %d%n", multiTime);

        return multiTime;
    }

    private static void increase(long singleTime, long multiTime) {
        double diff = ((double) singleTime / (double) multiTime) - 1;
        int increase = (int) (diff * 100);

        System.out.printf("increase: %d%%%n", increase);
    }
}
