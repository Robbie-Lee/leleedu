package com.lele.manager.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class HttpRequester {

	public static String HttpGetAccess(String requestURL, Object ... args) {
	
		int index = 0;
		
		for (Object arg : args) {
			requestURL = requestURL.replace("=?" + index, "=" + arg.toString());
			index ++;
		}
		
		String respStr = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();  
		try {	
            HttpGet httpget = new HttpGet(requestURL);  
            CloseableHttpResponse response = httpclient.execute(httpget);  

            respStr = EntityUtils.toString(response.getEntity(), "UTF-8");  
            respStr = JSON.parseObject(respStr, String.class);
            
            response.close();  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (JSONException e) {
        	e.printStackTrace();
        	respStr = null;
        } finally {  
            try {  
                httpclient.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		
        return respStr;
	}
	
	public static <T> String HttpPostAccess(String requestURL, Map<String, T> postBody) {

		String respStr = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httppost = new HttpPost(requestURL);  

		System.out.println("requestURL:" + requestURL);

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for(Iterator<String> i = postBody.keySet().iterator();i.hasNext();) {
			String bodyKey = i.next();
			if (postBody.get(bodyKey) instanceof String) {
				formparams.add(new BasicNameValuePair(bodyKey, (String)postBody.get(bodyKey)));
				System.out.println("body key:" + bodyKey + " \nbody content:" + (String)postBody.get(bodyKey));
			}
			else {
				String test = JSON.toJSONString(postBody.get(bodyKey));
				formparams.add(new BasicNameValuePair(bodyKey, JSON.toJSONString(postBody.get(bodyKey))));
				System.out.println("body key:" + bodyKey + " \nbody content:" + test);
			}
		}
	        
        try {
        	UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  

            CloseableHttpResponse response = httpclient.execute(httppost);  
            respStr = EntityUtils.toString(response.getEntity(), "UTF-8");  
            respStr = JSON.parseObject(respStr, String.class);
            response.close();  

        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (JSONException e) {
        	e.printStackTrace();
        	System.out.println(respStr);
        	respStr = null;
        }
       	finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
	
        return respStr;
	}
}

