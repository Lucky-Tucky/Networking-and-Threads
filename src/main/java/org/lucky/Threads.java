package org.lucky;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyRunnable implements Runnable{

    private final CountDownLatch countDownLatch;

    MyRunnable(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        try{
           countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Running Runnable..");
    }
}
public class Threads {

    static void main() {
        go();
    }

    private static void go(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(1);
        service.execute(new MyRunnable(latch));

        System.out.println("Main Thread Running!");
        latch.countDown();
        service.shutdown();
    }
}
