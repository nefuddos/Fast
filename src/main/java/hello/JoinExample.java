package hello;
/**
*@author    created by Ren Jingui
*@date  2018年9月6日---下午9:00:25
*@problem use thread.join() method to wait another thread
*@answer
*@action
*/
public class JoinExample {
	private class A extends Thread {
		@Override
		public void run() {
			System.out.println("A");
		}
	}
	
	private class B extends Thread {
		private A a;
		B(A a){
			this.a = a;
		}
		@Override
		public void run() {
			try {
				a.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("B");
		}
	}
	public void test() {
		A a = new A();
		B b = new B(a);
		b.start();
		a.start();
	}
	
	public static void main(String[] args) {
		JoinExample example = new JoinExample();
		example.test();
	}
}
