package com.company;

import java.util.Random;

public class Atm implements Runnable {
    Account account;
    Random random = new Random();
    String name;

    public Atm(Account acc, String name) {
        this.account = acc;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (account) {
                output();
                try {
                    account.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                account.notifyAll();
                try {
                    account.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int moneyGenerator() {
        if (random.nextBoolean()) {
            return random.nextInt(10000);
        } else {
            return -random.nextInt(10000);
        }
    }

    public void output() {
        int temp = moneyGenerator();
        if (-temp > account.balance) {
            System.out.print("недостаточно средств для снятия");
        } else if (temp < 0) {
            account.balance += temp;
            System.out.print("снято " + -temp);
        } else {
            account.balance += temp;
            System.out.print("начислено " + temp);
        }
        System.out.println("; баланс: " + account.balance + " работала " + name);
    }


}

