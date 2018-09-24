package interview;

import java.util.Scanner;
import java.text.DecimalFormat;

/**
*@author    created by Ren Jingui
*@date  2018年9月24日---上午10:23:27
*@problem 数列的第一项为n，以后各项为前一项的平方根，求数列的前m项的和。
*@input 输入数据有多组，每组占一行，由两个整数n（n < 10000）和m(m < 1000)组成，n和m的含义如前所述。
*@ouput 对于每组输入数据，输出该数列的和，每个测试实例占一行，要求精度保留2位小数。
*@answer n + n^1/2 + n^1/4 +...一共m项
*@action
*/
public class DataSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		double n = scanner.nextInt();
		int m = scanner.nextInt();
		double sum = 0;
		for(int i=0;i<m;i++) {
			sum += n;
			n = Math.sqrt(n);
		}
		System.out.println(new DecimalFormat("#.00").format(sum));
	}

}
