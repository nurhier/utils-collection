package org.common.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nurhier
 * @date 2020/1/15
 */
public abstract class AbstractConcurrency {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static CountDownLatch countDownLatchMain;
    private static CountDownLatch countDownLatch;

    /**
     * 执行任务
     */
    protected abstract void runTask();

    public void execute(int concurrentCount) throws InterruptedException {
        countDownLatchMain = new CountDownLatch(concurrentCount);
        countDownLatch = new CountDownLatch(concurrentCount);
        for (int count = 0; count < concurrentCount; count++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        runTask();
                    } catch (Exception e) {
                        e.getStackTrace();
                    } finally {
                        countDownLatchMain.countDown();
                    }
                }
            });
            countDownLatch.countDown();
        }
        countDownLatchMain.await();
    }
}
