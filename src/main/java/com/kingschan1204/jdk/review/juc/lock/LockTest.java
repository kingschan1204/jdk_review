package com.kingschan1204.jdk.review.juc.lock;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kingschan
 * 2024-7-25
 */
public class LockTest {
    private final Lock lock = new ReentrantLock();

    public void performTask(String threadName) {
        System.out.println(threadName + " is trying to get the lock");
        try {
            lock.lock(); // 线程尝试获取锁
            System.out.println(threadName + " got the lock");
            // 模拟一些工作
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(threadName + " is releasing the lock");
            lock.unlock(); // 确保锁释放
        }
    }

    public static void main(String[] args) {
        LockTest competition = new LockTest();

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            // 不同实例 不影响 不互斥
            // new LockTest().performTask(threadName);
            // 同一个对象 互斥
            competition.performTask(threadName);
        };
        List<Thread> list = Arrays.asList(
                new Thread(task, "Thread-1"),
                new Thread(task, "Thread-2"),
                new Thread(task, "Thread-3")

        );
        list.forEach(r -> {
            r.start();
        });
        list.forEach(r -> {
            try {
                r.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("job done.");

    }
}
