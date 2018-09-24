package interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
*@author    created by Ren Jingui
*@date  2018年9月23日---下午10:43:36
*@problem 继MIUI8推出手机分身功能之后，MIUI9计划推出一个电话号码分身的功能：首先将电话号码中的每个数字加上8取个位，然后使用对应的大写字母代替 （"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"）， 然后随机打乱这些字母，所生成的字符串即为电话号码对应的分身。
*@answer 找出数字规律，(2+8)%10=data[0]->ZERO(Z), (4+8)%10=data[2]->TWO(W), (6+8)%10=data[4]->FOUR(U), (8+8)%10=data[6]->SIX(X), (0+8)%10=data[8]->EIRHT(G).
*然后分析出，ONE(O)=data[1]-data[0]-data[2]-data[4],THREE(T)=data[3]-data[2]-data[8],FIVE(F)=data[5]-data[4], SEVEN(S)=data[7]-data[6], NINE(I)=data[9]-data[8]-data[6]-data[5]
*@action
*/
public class SepTel {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		line = br.readLine().trim();
		int n = Integer.parseInt(line);
		Integer data[] = new Integer[10];
		for(int i=0;i<n;i++) {
			line = br.readLine().trim();
			for(int j=0;j<10;j++) {
				data[j] = 0;
			}
			char[] array = line.toCharArray();
			for(char c : array) {
				switch(c){
				case 'Z':data[0]++;break;
				case 'W':data[2]++;break;
				case 'U':data[4]++;break;
				case 'X':data[6]++;break;
				case 'G':data[8]++;break;
				case 'O':data[1]++;break;
				case 'T':data[3]++;break;
				case 'F':data[5]++;break;
				case 'S':data[7]++;break;
				case 'I':data[9]++;break;
				}
			}
			//(每个数字+8)%10 取个位得到data[0-9]，那么最小的数字应该为data[8-9,0-7]
			StringBuffer sb = new StringBuffer();
			int temp;
			temp = data[8];
			while(temp-- > 0) sb.append('0');
			temp = data[9]-data[8]-data[6]-data[5]+data[4];
			while(temp-- > 0) sb.append('1');
			temp = data[0];
			while(temp-- > 0) sb.append('2');
			temp = data[1]-data[0]-data[2]-data[4];
			while(temp-- > 0) sb.append('3');
			temp = data[2];
			while(temp-- > 0) sb.append('4');
			temp = data[3] - data[2] - data[8];
			while(temp-- > 0) sb.append('5');
			temp = data[4];
			while(temp-- > 0) sb.append('6');
			temp = data[5]-data[4];
			while(temp-- > 0) sb.append('7');
			temp = data[6];
			while(temp-- > 0) sb.append('8');
			temp = data[7]-data[6];
			while(temp-- > 0) sb.append('9');
			System.out.println(sb);
		}
	}

}
