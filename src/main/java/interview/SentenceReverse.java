package interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
*@author    created by Ren Jingui
*@date  2018年9月24日---上午12:24:33
*@problem 给定一个句子（只包含字母和空格）， 将句子中的单词位置反转，单词用空格分割, 单词之间只有一个空格，前后没有空格。 比如： （1） “hello xiao mi”-> “mi xiao hello”
*@answer 首先反转句子，然后对每一个单词进行反转
*@action
*/
public class SentenceReverse {
	public static void reverseWord(char[] strArr, int begin, int end) {
		while(begin < end) {
			char temp = strArr[begin];
			strArr[begin++] = strArr[end];
			strArr[end--] = temp;
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		line = br.readLine();
		char[] strArr = line.toCharArray();
		reverseWord(strArr, 0, strArr.length-1);
		int begin = 0, end;
		for(end=0;end<strArr.length;end++) {
			if(strArr[end] == ' ') {
				reverseWord(strArr, begin, end-1);
				begin = end+1;
			}
		}
		reverseWord(strArr, begin,end -1);
		StringBuilder sb = new StringBuilder();
		for(char c: strArr) {
			sb.append(c);
		}
		System.out.println(sb);
	}

}
