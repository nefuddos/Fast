package hello;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
*@author    created by Ren Jingui
*@date  2018年9月6日---下午9:24:55
*@problem use condition.signal() condition.await() and condition.signalAll()
*@answer
*@action
*/
public class AwaitSignalExample {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void before() {
		lock.lock();
		System.out.println("before");
		condition.signal();
		lock.unlock();
	}
	public void after() {
		lock.lock();
		try {
			condition.await();
			System.out.println("after");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService service = Executors.newCachedThreadPool();
		AwaitSignalExample example = new AwaitSignalExample();
		service.execute(() -> example.after());
		service.execute(() -> example.before());
	}

}
