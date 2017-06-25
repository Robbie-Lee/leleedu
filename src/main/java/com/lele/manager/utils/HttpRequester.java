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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class HttpRequester {

	public static String HttpGetAccess(String requestURL, Object ... args) {
	
		String index = "0";
		
		for (Object arg : args) {
			requestURL = requestURL.replace("=?" + index, "=" + arg.toString());
			int i = Integer.valueOf(index) + 1;
			if (i == 10) {
				index = "a";
			}
			else if (i == 11) {
				index = "b";
			}
			else if (i == 12) {
				index = "c";
			}
			else if (i == 13) {
				index = "d";
			}
			else if (i == 14) {
				index = "e";
			}
			else if (i == 15) {
				index = "f";
			}
			else {
				index = String.valueOf(i);
			}
		}
		
		String respStr = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();  
		try {	
            HttpGet httpget = new HttpGet(requestURL);  
            CloseableHttpResponse response = httpclient.execute(httpget);  

            respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//            respStr = JSON.parseObject(respStr, String.class);
            
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
	
	public static <T> String HttpPostAccess(String requestURL, String postBody) {

		String respStr = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(requestURL);  

		System.out.println("requestURL:" + requestURL);

        StringEntity postEntity = new StringEntity(postBody, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        
        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);  
            respStr = EntityUtils.toString(response.getEntity(), "UTF-8");  
//            respStr = JSON.parseObject(respStr, String.class);
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

