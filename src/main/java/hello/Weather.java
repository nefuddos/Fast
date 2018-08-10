package hello;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
/**
*@author    created by Ren Jingui
*@date  2018��8��10��---����10:20:57
*@problem ����city��ȡcity���¶�
*@answer ģ��get���󣬽������ص�����
*@action ����api:https://www.sojson.com/open/api/weather/json.shtml?city=
*/


public class Weather {
	static private String url = "https://www.sojson.com/open/api/weather/json.shtml?city=";
	public int getWeather(String city)
	{
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url+city);
		//add request header
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		System.out.println("reponse content: "+ response.getEntity().getContentType());
		//JSONObject jsobject = JSONObject.parseObject(response.getEntity().getContent());
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while((line = rd.readLine()) != null)
			{
				result.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsObject = JSONObject.parseObject(result.toString());
		System.out.println(city+"�¶�Ϊ��"+jsObject.getJSONObject("data").getString("wendu"));
		return Integer.parseInt(jsObject.getJSONObject("data").getString("wendu"));
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(result.toString());
		//return "rainning";
	}
	public static void main(String[] args) {
		Weather weather = new Weather();
		int temperature = weather.getWeather("�Ͼ�");
		System.out.println("�¶�Ϊ��"+ temperature);
	}
}

