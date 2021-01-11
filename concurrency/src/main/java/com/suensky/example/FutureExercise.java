package com.suensky.example;

import java.util.Random;
import java.util.concurrent.*;

public class FutureExercise {
    private static Random random = new Random();

    public static void checkAndSavePrice() {

        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);
        // 异步向电商S1询价
        cs.submit(() -> getPriceFromStore(1));
        // 异步向电商S2询价
        cs.submit(() -> getPriceFromStore(2));
        // 异步向电商S3询价
        cs.submit(() -> getPriceFromStore(3));
        // 将询价结果异步保存到数据库
        for (int i = 0; i < 3; i++) {
            try {
                Integer r = cs.take().get();
                executor.execute(() -> save(r));
                System.out.println("The result: " + r.toString());
            } catch (InterruptedException | ExecutionException ex) {

            }
        }
    }

    private static int getPriceFromStore(int storeLabel) throws InterruptedException {
        try {
            Thread.sleep(getSleepMilliSeconds());
            System.out.println("[getPriceFromStore] thread: " + Thread.currentThread().getName() + " label:" + storeLabel);
        } catch (InterruptedException ex) {

        }
        return storeLabel;
    }

    private static int save(Integer price) {
        try {
            Thread.sleep(getSleepMilliSeconds());
            System.out.println("[save] thread: " + Thread.currentThread().getName() + " label:" + price);
        } catch (InterruptedException ex) {

        }
        return price;
    }

    private static int getSleepMilliSeconds() {
        return random.nextInt(5000);
    }
}
