package com.evan.baselib.net.HashMapTask;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


/**
 * @description TODO
 * @author huanghj
 * @date 2015年11月16日 上午9:32:34
 */

public class NetWorkUtils {


	/**
	 * @descripiton 请求网络线程
	 * @author huanghj
	 * @param method  POST GET
	 * @param strURL  请求地址
	 * @param params  map参数
	 * @param strParam  json参数
	 * @param header  
	 * @param timeout 超时，毫秒
	 * @return String
	 */
	public static String accessNetwork(String method, String strURL, Map<String, String> params, String strParam, Map<String, String> header, int timeout) {

		int TIMEOUT = timeout;
		
		String data = null;

		URL url = null;
		try {
			url = new URL(strURL);

			HttpURLConnection urlConnection = null;
			try {

				// params = "123123123";

				if(null == method || "".equals(method.trim())){
					method = "POST";
				}
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod(method);
				urlConnection.setDoOutput(true); // 开启输出流
				urlConnection.setDoInput(true);
				urlConnection.setUseCaches(false);
				urlConnection.setConnectTimeout(TIMEOUT); // 连接超时为10秒
				urlConnection.setReadTimeout(TIMEOUT); // 读取数据超时

//				if(null != header){
//					for (String key : header.keySet()) {
//						urlConnection.setRequestProperty(key, header.get(key));
//					}
//				}
				
				// 请求头部分参数
//				urlConnection.setRequestProperty("client-version", MyApplication.application.getVersionName());
//				urlConnection.setRequestProperty("channel-id", Common.CHANNEL_NAME);
//				urlConnection.setRequestProperty("e-type", String.valueOf(Common.ISENCRYPT));
//				urlConnection.setRequestProperty("User-Agent", String.valueOf(Common.ISENCRYPT));

				// urlConnection.setRequestProperty("Content-Length",
				// buffer.length + "");
				// urlConnection.setRequestProperty("Content-type",
				// "application/json;charset=utf-8;");
				// urlConnection.setRequestProperty("Accept-Encoding",
				// "gzip, deflate");
//				urlConnection.setRequestProperty("Content-type", "application/json"); // ;
																						// boundary="
																						// +
																						// myBoundary
				urlConnection.setRequestProperty("Connection", "Keep-Alive");
				urlConnection.setRequestProperty("Accept-Charset", "UTF-8");

//				if(null != strParam && !"".equals(strParam.trim())){
//					urlConnection.setRequestProperty("Content-type", "application/json");
//				}
//
				urlConnection.connect();

				DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

		        if (params != null && params.size() > 0) {
		        	byte[] buffer = encodeParameters(params, "UTF-8");
		        	out.write(buffer);
		        }else if(null != strParam){
		        	byte[] buffer = strParam.getBytes("UTF-8");
		        	out.write(buffer);
		        }

				out.flush();
				out.close();

				Log.d("Log", "header-->" + urlConnection.getResponseCode());
//				for(int index = 0,size = urlConnection.getHeaderFields().size();index<size;index++){
					Log.d("Log","headers = "+ urlConnection.getHeaderFields().toString());
				for(int index2 =0,size = urlConnection.getHeaderFields().get("Set-Cookie").size();index2<size;index2++){
					Log.d("Log","headers = "+ urlConnection.getHeaderFields().get("Set-Cookie").get(index2));
				}

//				}

				data = getConnectionResponse(urlConnection);

				urlConnection.disconnect();

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}finally{
				if(null != urlConnection){
					urlConnection.disconnect();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}finally{
			url = null;
		}

		return data;
	}
	
	private static byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
	
	private static String getConnectionResponse(HttpURLConnection conn) {

		StringBuffer sBuffer = new StringBuffer();
		ByteArrayOutputStream out = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			if (conn.getResponseCode() == 400) {
				byte[] buf = new byte[1024];
				InputStream erris = conn.getErrorStream();
				for (int n; (n = erris.read(buf)) != -1;) {
					sBuffer.append(new String(buf, 0, n, "UTF-8"));
				}
				erris.close();
				sBuffer.append("");

			} else if (conn.getResponseCode() == 200) {
				int len = conn.getContentLength();
				if (len > 0) {
					out = new ByteArrayOutputStream(len);
				} else {
					out = new ByteArrayOutputStream(100 * 1024);
				}
				is = conn.getInputStream();
				bis = new BufferedInputStream(is, 1024 * 16);
				int i = -1;
				byte data[] = new byte[1024 * 8];
				while ((i = bis.read(data)) != -1) {
					out.write(data, 0, i);
				}
				out.flush();
				sBuffer.append(new String(out.toByteArray(), "utf-8"));
				Log.d("Log",sBuffer.toString());
			}
		} catch (UnsupportedEncodingException e) {
			sBuffer.append("");
		} catch (IOException e) {
			sBuffer.append("");
		} finally {
			try {
				if (null != is) {
					is.close();
				}
				if (null != bis) {
					bis.close();
				}
				if (null != out) {
					out.close();
				}
				is = null;
				bis = null;
				out = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sBuffer.toString();
	}
}
