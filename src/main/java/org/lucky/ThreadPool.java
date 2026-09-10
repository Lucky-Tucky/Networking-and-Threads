package org.lucky;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Balance{
    public int balance =0;

    public void increment(){
        balance++;
    }
}

class LostUpdate{

    public void update() throws  InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        Balance balance = new Balance();
        for(int i =0;i<1000;i++){
            executorService.execute(()-> balance.increment());
        }
        executorService.shutdown();
        if(executorService.awaitTermination(5,TimeUnit.MINUTES)){
            System.out.println(balance.balance);
        }
    }
}
public class ThreadPool {

    private void go(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for(int i =0;i<25;i++){
            int finalI = i;
            executorService.execute(()-> System.out.println("Thread number--"+ finalI));
        }

        executorService.shutdown();

        try{
            boolean finished = executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
            System.out.println("Finished->"+finished);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        executorService.shutdownNow();
    }

    static void main() {
        try {
            new LostUpdate().update();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
