package interview;

import java.util.Scanner;

/**
*@author    created by Ren Jingui
*@date  2018年9月16日---下午5:30:39
*@problem
*@answer
*@action
*/
public class StringMulty {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		String str1 = scanner.next();
		String str2 = scanner.next();
		String  result = null;
		for(int index1 = str1.length() - 1;index1 >=0;index1--) {
			int temp1 = 0, temp2 = 0;
			for(int index2 = str2.length() - 1; index2 >=0;index2--) {
				temp1 = (temp2 + (str1.charAt(index1) - '0') * (str2.charAt(index2) - '0')) %10;
				temp2 = (temp2 + (str1.charAt(index1) - '0') * (str2.charAt(index2) - '0')) /10;
				result += temp1+'0';
			}
		}
	}

}
