package hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
*@author    created by Ren Jingui
*@date  2018年9月7日---下午10:12:18
*@problem 使用ForkJoin 继承RecursiveTask来并行计算
*ForkJoinPool 实现了工作窃取算法来提高 CPU 的利用率。每个线程都维护了一个双端队列，
*用来存储需要执行的任务。工作窃取算法允许空闲的线程从其它线程的双端队列中窃取一个任务来执行。
*窃取的任务必须是最晚的任务，避免和队列所属线程发生竞争
*@answer
*@action
*/
public class ForkJoinExample extends RecursiveTask{
	private final int hold = 5;
	private int first;
	private int last;
	public ForkJoinExample(int first, int last) {
		this.first = first;
		this.last = last;
	}
	@Override
	protected Integer compute() {
		int result = 0;
		if(last - first <= this.hold) {
			for(int i=first;i<last;i++) {
				result += i;
			}
		} else {
			int middle = first + (last - first)/2;
			ForkJoinExample task1 = new ForkJoinExample(first, middle);
			ForkJoinExample task2 = new ForkJoinExample(middle + 1, last);
			task1.fork();
			task2.fork();
			result = (int) task1.join() + (int) task2.join();
		}
		return result;
	}
	public static void main(String[] args){
		// TODO Auto-generated method stub
		ForkJoinExample task = new ForkJoinExample(1,10000);
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Future result = forkJoinPool.submit(task);
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
