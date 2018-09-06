package hello;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*@author    created by Ren Jingui
*@date  2018年9月6日---下午10:01:28
*@problem all the threads arrive to the begin, and run again.
*@answer
*@action
*/
public class CyclicBarrierExample {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int totalThread = 10;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThread);
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0;i<totalThread;i++) {
			service.execute(() -> {
				System.out.print("before..");
				try {
					Thread.sleep(100);
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print("after..");
			});
		}
		service.shutdown();
	}

}
