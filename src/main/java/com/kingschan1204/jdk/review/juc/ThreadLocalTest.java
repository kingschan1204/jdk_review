package com.kingschan1204.jdk.review.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author kingschan
 * 2024-7-24
 */
@Slf4j
public class ThreadLocalTest {

   public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

   static {
       threadLocal.set(0);
   }

    public static void main(String[] args) throws InterruptedException {
        // ThreadLocal 是 Java 中的一个类，用于为每个线程提供一个独立的变量副本。这意味着每个线程都可以独立地修改其本地变量副本，而不会影响其他线程中的副本。
        // ThreadLocal 在多线程编程中非常有用，特别是在需要确保每个线程都有自己独立的状态而不相互干扰的场景下。
        Thread t1 = new Thread(() -> {
            threadLocal.set(100);
            System.out.println(Thread.currentThread().getName() + " value: " + threadLocal.get());
        });

        Thread t2 = new Thread(() -> {
            threadLocal.set(200);
            System.out.println(Thread.currentThread().getName() + " value: " + threadLocal.get());
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(Thread.currentThread().getName() + " value: " + threadLocal.get());
    }
}
