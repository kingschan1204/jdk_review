package com.kingschan1204.jdk.review.juc.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author kings.chan
 * 2024-7-24
 */
@Slf4j
public class SchedulerTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        // Schedule a task that returns a string after 2 seconds
        scheduler.schedule(() -> {
            log.info("{} 在指定的延迟后执行一次！我只执行一次", Thread.currentThread().getName());
        }, 2, TimeUnit.SECONDS);

        //调度一个任务在初始延迟后开始，以固定的周期执行
        //定时执行: 任务会在固定的时间间隔（周期）后重复执行，时间间隔是从任务开始时间算起。
        //时间计算: 任务的执行时间间隔是固定的，不会受到任务执行时间的影响。例如，如果你的任务每 10 秒执行一次，即使任务的执行时间超过 10 秒，下一次执行仍然会在前一次任务开始后的 10 秒钟执行。
        //可能的任务重叠: 如果任务执行时间超过了固定周期，可能会导致任务重叠。例如，如果任务需要 15 秒来完成，而你设定的是每 10 秒执行一次，那么任务会在下一个周期开始时继续执行。
        scheduler.scheduleAtFixedRate(() -> {
            try {
                log.info("scheduleAtFixedRate: {} {}",Thread.currentThread().getName(), System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, 0, 1, TimeUnit.SECONDS);

//        固定延迟: 任务会在前一次任务完成后的固定时间间隔（延迟）后执行。
//        时间计算: 任务的执行时间间隔是从前一次任务完成时间算起。如果任务执行时间比较长，那么下一个任务会在前一个任务完成后经过设定的延迟时间后执行。
//        避免重叠: 如果任务执行时间很长，可以避免任务重叠，因为每次任务的执行都需要等到上一个任务完成后才开始延迟。
        scheduler.scheduleWithFixedDelay(() -> {
            try {
                log.info("scheduleWithFixedDelay: {} {}",Thread.currentThread().getName(), System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, 0, 1, TimeUnit.SECONDS);



        // Shutdown the scheduler
//        scheduler.shutdown();
//         总结：
//        scheduleAtFixedRate: 适合于你希望任务按照固定时间间隔执行，而不管上一个任务是否完成。这可能导致任务重叠。
//        scheduleWithFixedDelay: 适合于你希望任务在每次完成后经过一定的延迟时间后再执行，避免任务重叠。

    }
}
