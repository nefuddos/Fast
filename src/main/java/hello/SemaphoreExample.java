package hello;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
*@author    created by Ren Jingui
*@date  2018年9月6日---下午10:18:02
*@problem use Semaphore to limit the parallel thread numbers of mutual resource
*@answer
*@action
*/
public class SemaphoreExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int clientCount = 20;
		final int totalRequestCount = 10;
		Semaphore semaphore = new Semaphore(clientCount);
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0;i<totalRequestCount;i++) {
			service.execute(() ->{
				try {
					semaphore.acquire();
					System.out.print(semaphore.availablePermits() + " ");
//					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					semaphore.release();
				}
			});
		}
		service.shutdown();
	}

}
