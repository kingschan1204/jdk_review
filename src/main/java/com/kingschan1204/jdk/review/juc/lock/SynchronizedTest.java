package com.kingschan1204.jdk.review.juc.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author kingschan
 * 2024-7-24
 */
@Slf4j
public class SynchronizedTest {

    // synchronized 是 Java 中用于同步的关键字，它可以保证多线程环境中，某些代码块或方法在同一时间只能被一个线程执行，从而避免数据的不一致性和并发问题。
    // synchronized 提供了一种互斥锁机制，即每个锁一次只能被一个线程拥有。

    // 原理
    // synchronized 实际上使用了对象的监视器锁（Monitor Lock）。每个对象都有一个监视器和一个相关的锁。线程在进入
    // synchronized 方法或块之前，必须获得这个对象的监视器锁。如果一个线程持有了锁，其他试图进入 synchronized 方法或块的线程将被阻塞，直到持有锁的线程释放它。

    //    注意事项
    //    锁的粒度：锁的粒度越大（如同步整个方法），并发性能越低。尽量只锁定必要的代码段。
    //    避免死锁：多个线程之间互相等待彼此释放锁时会产生死锁。为了避免死锁，尽量减少锁的持有时间，避免嵌套锁。
    //    性能影响：synchronized 会引入一些性能开销，尤其是在高并发环境中。因此，应在实际需要时才使用，并尽量优化同步代码块的效率。


    //////////////////////////////////////////////////////////////////////
    public synchronized void type1A() {
        log.info("{} start  ", Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
        }
        log.info("{} end  ", Thread.currentThread().getName());
    }

    public synchronized void type1B() {
        log.info("{} start  ", Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
        }
        log.info("{} end  ", Thread.currentThread().getName());
    }

    //////////////////////////////////////////////////////////////////

    public static synchronized void type2A() {
        log.info("{} start  ", Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
        }
        log.info("{} end  ", Thread.currentThread().getName());
    }


    public void type3A() {
        synchronized (this) {
            log.info("{} start  ", Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            log.info("{} end  ", Thread.currentThread().getName());
        }

    }


    /**
     * synchronized修饰不同对象的两个非静态方法时不会互斥
     */
    public static void test1() {

        new Thread(() -> {
            // 这里new 了一个SynchronizedTest对象
            new SynchronizedTest().type1A();
        }).start();

        new Thread(() -> {
            // 这里new 了另一个SynchronizedTest对象
            new SynchronizedTest().type1B();
        }).start();
    }

    /**
     * synchronized static方法互斥
     */
    public static void test2() {

        new Thread(() -> {
            // 这里new 了一个SynchronizedTest对象
            SynchronizedTest.type2A();
        }).start();

        new Thread(() -> {
            // 这里new 了另一个SynchronizedTest对象
            SynchronizedTest.type2A();
        }).start();
    }

    /**
     * 代码块是this 锁的是实例 同一个实例互斥  不同实例对象不影响
     */
    public static void test3() {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        new Thread(() -> {
            // 这里new 了一个SynchronizedTest对象
            synchronizedTest.type3A();
        }).start();

        new Thread(() -> {
            // 这里new 了另一个SynchronizedTest对象
            synchronizedTest.type3A();
        }).start();
    }

    public static void main(String[] args) {

//        test1();
//        test2();
        //代码块是this 锁的是实例 同一个实例互斥  不同实例对象不影响
        test3();

    }
}
