package org.lucky;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BankAccount{
    private int balance = 1000;

    public int getBalance(){
        return balance;
    }

    public synchronized void spend(int amount,String name){
        System.out.println(name+" is about to spend");
        balance=balance-amount;
        if(balance<0){
            System.out.println("Overdrawn");
        }
        System.out.println(name+" Finished Spending");
    }
}

class RyanAndMonicaJob implements  Runnable{

    private final String name;
    private final BankAccount bankAccount;
    private final int amountToSpend;

    RyanAndMonicaJob(String name , BankAccount bankAccount, int amountToSpend){
        this.name = name;
        this.bankAccount = bankAccount;
        this.amountToSpend = amountToSpend;
    }

    @Override
    public void run() {
        goShopping(amountToSpend);
    }

    private void goShopping(int amount){
        bankAccount.spend(amount,name);
    }

}
public class Concurrent {
    static void main() {
        BankAccount account = new BankAccount();
        ExecutorService execution = Executors.newFixedThreadPool(2);
        RyanAndMonicaJob ryan = new RyanAndMonicaJob("ryan",account,600);
        RyanAndMonicaJob monica = new RyanAndMonicaJob("monica",account,600);
        execution.execute(ryan);
        execution.execute(monica);
        execution.shutdown();
    }
}
