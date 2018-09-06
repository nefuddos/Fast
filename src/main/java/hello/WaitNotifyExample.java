package hello;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*@author    created by Ren Jingui
*@date  2018年9月6日---下午9:12:51
*@problem use object.wait() and object.notify()//object.notifyAll()
*wait() 和 sleep() 的区别
    wait() 是 Object 的方法，而 sleep() 是 Thread 的静态方法；
    wait() 会释放锁，sleep() 不会。
*@answer 
*@action
*/
public class WaitNotifyExample {
	public synchronized void before() {
		System.out.println("this is before");
		this.notify();
	}
	
	public synchronized void after() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("this is after");
	}
	
	public static void main(String[] args) {
		WaitNotifyExample example = new WaitNotifyExample();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(() ->example.after());
		service.execute(() ->example.before());
	}

}
