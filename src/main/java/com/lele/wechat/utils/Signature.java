package com.lele.wechat.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Signature {
	
	private static final String payKey = "leleeduleleeduleleeduleleedu0621";
	
    public static String getSign(Object o) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + payKey;
        try {
			result = byteToStr(MessageDigest.getInstance("MD5").digest(result.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }

	private static String byteToStr(byte[] byteArray) {  

        String strDigest = "";  

        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  

        return strDigest;  
    }  
	
	private static String byteToHexStr(byte mByte) {  

        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  

        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
        String s = new String(tempArr);  

        return s;  
    }  
    
    public static String getSign(Map<String,Object> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + payKey;
        try {
			result = byteToStr(MessageDigest.getInstance("MD5").digest(result.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }

    public static String getSignFromResponseString(String responseString) throws IOException, SAXException, ParserConfigurationException {
        Map<String,Object> map = getMapFromXML(responseString);
        map.put("sign","");
        return Signature.getSign(map);
    }

    private static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  getStringStream(xmlString);
        Document document = builder.parse(is);

        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;

    }
    
    private static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }

    public static boolean checkIsSignValidFromResponseString(String responseString) throws ParserConfigurationException, IOException, SAXException {

        Map<String,Object> map = getMapFromXML(responseString);

        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
            return false;
        }
        map.put("sign","");
        String signForAPIResponse = Signature.getSign(map);

        if(!signForAPIResponse.equals(signFromAPIResponse)){
            return false;
        }
        return true;
    }
}
