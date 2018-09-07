package hello;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
*@author    created by Ren Jingui
*@date  2018年9月7日---下午8:52:22
*@problem 使用FutureTask封装任务，用于异步获取结果或取消任务的场景,
* 当一个任务需要很长时间，就需要FutureTask来封装这个任务
*@answer
*@action
*/
public class FutureTaskExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
			@Override
			public Integer call() throws InterruptedException {
				int result = 0;
				for(int i=1;i<=100;i++) {
					Thread.sleep(10);
					result += i;
				}
				return result;
			}
		});
		
		Thread computeThread = new Thread(futureTask);
		computeThread.start();
		
		Thread otherThread = new Thread(() ->{
			System.out.println("other thread is running");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		otherThread.start();
		System.out.println(futureTask.cancel(true));
	}

}
