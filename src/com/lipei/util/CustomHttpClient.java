package com.lipei.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



import android.content.Context;
import android.util.Log;

public class CustomHttpClient
{
	private static String TAG = "CustomHttpClient";
	private static final String CHARSET_UTF8 = HTTP.UTF_8;
	private static HttpClient customerHttpClient;

	private CustomHttpClient()
	{
	}

	/**
	 * HttpClient post 方法向服务端提交数据
	 * @param url 
	 * @param nameValuePairs
	 * @return 服务端返回的结果
	 */
	public static String PostFromWebByHttpClient(Context context, String url,
			NameValuePair... nameValuePairs)
	{
		try
		{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (nameValuePairs != null)
			{
				for (int i = 0; i < nameValuePairs.length; i++)
				{
					params.add(nameValuePairs[i]);
					System.out.println("values send to server");
					System.out.println( nameValuePairs[i].getValue());
				}
			}
			UrlEncodedFormEntity urlEncoded = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(urlEncoded);
			HttpClient client = getHttpClient(context);
			HttpResponse response = null;
			try
			{
				 response = client.execute(httpPost);
			} catch (Exception e)
			{
				Log.d("---> "+e.toString(), "---> "+e.toString());
				System.out.println("---> "+e.toString());
			}
			if (null==response) {
				return null;
			}
			System.out.println("请求是否成功 " + String.valueOf(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK));
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{
				Log.d("-->"+"POST方法出错", "-->"+"POST方法出错"+response.getStatusLine().getStatusCode());
				return null;
			}
			HttpEntity resEntity = response.getEntity();
			return (resEntity == null) ? null : EntityUtils.toString(resEntity,
					CHARSET_UTF8);
		} catch (UnsupportedEncodingException e)
		{
			Log.w(TAG, e.getMessage());
			
			return null;
		} catch (ClientProtocolException e)
		{
			Log.w(TAG, e.getMessage());
			return null;
		} catch (IOException e)
		{
			Log.d(TAG, e.getMessage());
			return null;
		}
	}

	/**
	 * 直接以Json结构提交数据
	 * @param context
	 * @param url
	 * @param things
	 * @return
	 */
	public static String postFromWebWithJson(Context context, String url,
			String things)
	{
		try
		{
			StringEntity entity = new StringEntity(things,CHARSET_UTF8);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			httpPost.setEntity(entity);
			HttpClient client = getHttpClient(context);
			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{
				return null;
			}
			HttpEntity resEntity = response.getEntity();
			return (resEntity == null) ? null : EntityUtils.toString(resEntity,
					CHARSET_UTF8);
		} catch (UnsupportedEncodingException e)
		{
			Log.w(TAG, e.getMessage());
			return null;
		} catch (ClientProtocolException e)
		{
			Log.w(TAG, e.getMessage());
			return null;
		} catch (IOException e)
		{
			Log.w(TAG, e.getMessage());
			return null;
		}
	}
	/**
	 * HttpClient 的get方法，向服务器请求数据
	 * @param context
	 * @param url
	 * @param nameValuePairs
	 * @throws Exception
	 */
	public static String getFromWebByHttpClient(Context context, String url,
			NameValuePair... nameValuePairs) throws Exception
	{
		try
		{

			StringBuilder sb = new StringBuilder();
			sb.append(url);
			if (nameValuePairs != null && nameValuePairs.length > 0)
			{
				sb.append("?");
				for (int i = 0; i < nameValuePairs.length; i++)
				{
					if (i > 0)
					{
						sb.append("&");
					}
					sb.append(String.format("%s=%s",
							nameValuePairs[i].getName(),
							nameValuePairs[i].getValue()));
				}
			}
			// HttpGet连接对象
			HttpGet httpRequest = new HttpGet(sb.toString());
			// 取得HttpClient对象
			HttpClient httpclient = getHttpClient(context);
			// 请求HttpClient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);

			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			{
				return null;
			}

			String result = EntityUtils.toString(httpResponse.getEntity());

			return result;

		} catch (ParseException e)
		{
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 创建httpClient实例
	 * 
	 * @return
	 * @throws Exception
	 */
	private static synchronized HttpClient getHttpClient(Context context)
	{
		if (null == customerHttpClient)
		{
			HttpParams params = new BasicHttpParams();
			// 设置�?些基本参�?
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET_UTF8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 超时设置
			/* 从连接池中取连接的超时 */
			ConnManagerParams.setTimeout(params, 5000);
			/* 连接超时 */
			int ConnectionTimeOut = 10000;
			if (!NetWorkHelper.isWifiDataEnable(context))
			{
				ConnectionTimeOut = 10000;
			}
			HttpConnectionParams
					.setConnectionTimeout(params, ConnectionTimeOut);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 10000); 
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			customerHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customerHttpClient;
	}
}
