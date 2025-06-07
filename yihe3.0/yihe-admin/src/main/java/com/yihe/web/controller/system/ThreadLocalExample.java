package com.yihe.web.controller.system;

public class ThreadLocalExample {

    private static final ThreadLocal<Integer> threadLocalCounter = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        // 创建三个线程，每个线程都会有自己独立的变量副本
        Thread t1 = new Thread(() -> {
            incrementAndPrint();
        });

        Thread t2 = new Thread(() -> {
            incrementAndPrint();
        });

        Thread t3 = new Thread(() -> {
            incrementAndPrint();
        });

        // 启动线程
        t1.start();
        t2.start();
        t3.start();
    }

    private static void incrementAndPrint() {
        for (int i = 0; i < 5; i++) {
            int currentValue = threadLocalCounter.get();
            threadLocalCounter.set(currentValue + 1);
            System.out.println(Thread.currentThread().getName() + " : " + threadLocalCounter.get());
        }
    }
}

