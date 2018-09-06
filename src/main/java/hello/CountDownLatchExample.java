package hello;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*@author    created by Ren Jingui
*@date  2018年9月6日---下午9:48:19
*@problem use CountDownLatch to create thread
*@answer
*@action
*/
public class CountDownLatchExample {
	
	public static void main(String[] args) {
		final int totalThread = 10;
		CountDownLatch countDownLatch = new CountDownLatch(totalThread);
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0;i<totalThread;i++) {
			service.execute(() -> {
				System.out.println("run...");
				countDownLatch.countDown();
			});
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
		service.shutdown();
	}

}
