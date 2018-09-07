package hello;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*@author    created by Ren Jingui
*@date  2018年9月7日---下午10:36:47
*@problem 线程不安全例子，数据读写不一致
*@answer
*@action
*/
public class ThreadUnSafeExample {
    private int cnt = 0;

    public void add() {
        cnt++;
    }

    public int get() {
        return cnt;
    }
    
	public static void main(String[] args) {
	    final int threadSize = 1000;
	    ThreadUnSafeExample example = new ThreadUnSafeExample();
	    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
	    ExecutorService executorService = Executors.newCachedThreadPool();
	    for (int i = 0; i < threadSize; i++) {
	        executorService.execute(() -> {
	            example.add();
	            countDownLatch.countDown();
	        });
	    }
	    try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    executorService.shutdown();
	    System.out.println(example.get());
	}

}
