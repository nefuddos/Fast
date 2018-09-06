package hello;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
*@author    created by Ren Jingui
*@date  2018年9月6日---下午8:44:02
*@problem use ReentrantLock example
*@answer
*@action
*/
public class LockExample {
	private Lock lock = new ReentrantLock();
	public void func() {
		lock.lock();
		try {
			for(int i=0;i<10;i++) {
				System.out.print(i + " ");
			}
			System.out.println("");
		} finally {
			lock.unlock();
		}
	}
	public static void main(String[] args) {
		LockExample lockExample = new LockExample();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(() -> lockExample.func());
		service.execute(() -> lockExample.func());
	}
}
