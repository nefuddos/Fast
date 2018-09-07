package hello;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
*@author    created by Ren Jingui
*@date  2018年9月7日---下午9:23:35
*@problem 使用BlockingQueue实现生产者消费者
*@answer
*@action
*/
public class ProducerConsumer {
	private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
	private static class Producer extends Thread{
		public void run() {
			try {
				queue.put("product");
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("produce...");
		}
	}
	
	private static class Consumer extends Thread{
		public void run() {
			try {
				String product = queue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("consume...");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++) {
			Producer producer = new Producer();
			producer.start();
			
			Consumer consumer = new Consumer();
			consumer.start();
		}
	}

}
