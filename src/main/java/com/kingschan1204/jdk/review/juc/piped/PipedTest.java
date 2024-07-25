package com.kingschan1204.jdk.review.juc.piped;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kings.chan
 * 2024-7-24
 */
public class PipedTest {

    public static void main(String[] args) throws Exception {

        // java 管道  类似线程消费者模式 -> 适应于一对一的场景
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);

        // 使用对象流包装管道流
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        AtomicInteger count = new AtomicInteger(0);

        // 线程1：写入对象
        Thread thread1 = new Thread(() -> {
            try {
                while (count.getAndIncrement() < 10) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("time", System.currentTimeMillis());
                    objectOutputStream.writeObject(map);
                    if (count.get() == 10) {
                        objectOutputStream.close();
                        System.out.println("发送方关闭");
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 线程2：读取对象
        Thread thread2 = new Thread(() -> {
            try {
                while (true) {
                    Map<String, Object> map = (Map<String, Object>) objectInputStream.readObject();
                    System.out.println("接收方收到: " + map);
                }

            } catch (EOFException e) {
                e.printStackTrace();
                System.out.println("接收方完成读取");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    objectInputStream.close();
                    System.out.println("接收方关闭");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
