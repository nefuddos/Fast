package interview;

import java.util.ArrayList;
import java.util.Scanner;

/**
*@author    created by Ren Jingui
*@date  2018年9月24日---上午1:07:17
*@problem 春天是鲜花的季节，水仙花就是其中最迷人的代表，数学上有个水仙花数，他是这样定义的： “水仙花数”是指一个三位数，它的各位数字的立方和等于其本身，比如：153=1^3+5^3+3^3。 现在要求输出所有在m和n范围内的水仙花数
*@input 输入数据有多组，每组占一行，包括两个整数m和n（100 ≤ m ≤ n ≤ 999）
*@output 对于每个测试实例，要求输出所有在给定范围内的水仙花数，就是说，输出的水仙花数必须大于等于m,并且小于等于n，如果有多个，则要求从小到大排列在一行内输出，之间用一个空格隔开;
如果给定的范围内不存在水仙花数，则输出no;
每个测试实例的输出占一行。
*@answer 先找出所有的水仙花数，然后直接打表
*@action
*/
public class WaterFairy {
	ArrayList<Integer> data = new ArrayList<Integer>();
	public static boolean judge(int a, int b, int c, int number) {
		if(a*a*a+b*b*b+c*c*c == number)
			return true;
		else
			return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt(),b = scanner.nextInt();
		StringBuffer sb = new StringBuffer();
		for(int i=a;i<=b;i++) {
			if(judge(i/100,(i%100)/10,i%10,i)) {
				sb.append(i + " ");
			}
		}
		if(sb.length() == 0)
			System.out.println("no");
		else {
			sb.deleteCharAt(sb.length()-1);
			System.out.println(sb);
		}
			
	}

}
