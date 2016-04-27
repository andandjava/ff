package com.falconpack.android.webservicecall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.falconpack.android.common.Constants;
import android.util.Log;

public class WebServiceCalls {

	public String result = "";
	String ff = "";
	public final static int GET = 1;
	public final static int POST = 2;

	public String urlPost(String url) {

		System.out.println("url is" + url);
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 5000);
		HttpClient client = new DefaultHttpClient(httpParameters);

		try {

			HttpPost post = new HttpPost(url);

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String re = EntityUtils.toString(entity, HTTP.UTF_8);
				// ff = re;

				result = re.trim();
			} else {
				result = "";
			}

		} catch (SocketTimeoutException e) {
			client.getConnectionManager().shutdown();
			urlPost(url);

		} catch (Exception e) {
			client.getConnectionManager().shutdown();
			// urlPost(url);
			return null;
		}

		return result;

	}

	public String urlPostNew(String url) {

		HttpClient hClient = new DefaultHttpClient();
		HttpPost hPost = new HttpPost(url);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		try {
			hPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = hClient.execute(hPost);
			HttpEntity hEntity = response.getEntity();
			InputStream is = hEntity.getContent();

			// hClient.getConnectionManager().shutdown();

			result = convertStreamToString(is);

		} catch (UnsupportedEncodingException e) {
			hClient.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			hClient.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			hClient.getConnectionManager().shutdown();
			e.printStackTrace();
		}

		return result;

	}

	InputStream is;
	JSONObject jObj = null;
	String json = "";

	public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			// httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
		}

		// return JSON String
		return jObj;

	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String getJSONString(String url) {
		String jsonString = null;
		System.out.println("url is" + url);

		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			post.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String re = EntityUtils.toString(entity, HTTP.UTF_8);
				jsonString = re.trim();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	public static String convertJsonToStringUsingHttpResponse(String url)
			throws IOException {

		String jsonString = "";
		Log.e("Url ", url);
		try {

			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				// get contents from stream through entity
				HttpEntity entity = response.getEntity();
				// String line=EntityUtils.toString(entity, HTTP.UTF_8);
				jsonString = EntityUtils.toString(entity, HTTP.UTF_8)
						.toString();
			} else {
				// throw giveSomeException();
				Log.e("RemoteDataFetcher", "Error in download" + statusCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	public static String postValues(List<NameValuePair> pairs, String methodName) {

		HttpParams httpParameters = new BasicHttpParams();
//		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
//		HttpConnectionParams.setSoTimeout(httpParameters, 5000);
		HttpClient client = new DefaultHttpClient(httpParameters);
		HttpPost post = new HttpPost(Constants.MAIN_HOST + methodName.trim());
		
		Log.e("URL : ",Constants.MAIN_HOST + methodName.trim());
		HttpResponse response;
		int responseCode = 0;
		String re = "";
		
		try {

			pairs.add(new BasicNameValuePair("apikey", "ETG123"));

			post.setEntity(new UrlEncodedFormEntity(pairs));

			response = client.execute(post);

			responseCode = response.getStatusLine().getStatusCode();

			if (responseCode == HttpStatus.SC_OK) {
				try {
					HttpEntity entity = response.getEntity();
					re = EntityUtils.toString(entity, HTTP.UTF_8);
					System.out.println("status " + responseCode + " response "
							+ re.toString());
					return re.toString();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				Log.v("Internal Exception ",""+responseCode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Making service call
	 * 
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */
	public static String makeServiceCall(String url, int method,
			List<NameValuePair> params) {
		
		String response="";
		
		try {
			// http client
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
			HttpConnectionParams.setSoTimeout(httpParameters, 5000);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;

			// Checking http request method type
			if (method == POST) {
				HttpPost httpPost = new HttpPost(url);
				// adding post params
				if (params != null) {
					params.add(new BasicNameValuePair("apikey", "ETG123"));
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				}

				httpResponse = httpClient.execute(httpPost);

			} else if (method == GET) {
				// appending params to url
				if (params != null) {
					String paramString = URLEncodedUtils
							.format(params, "utf-8");
					url += "?" + paramString;
				}
				HttpGet httpGet = new HttpGet(url);
				httpResponse = httpClient.execute(httpGet);
			}
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
