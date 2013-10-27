package com.cst.stormdroid.net;

import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.cst.stormdroid.net.interfaces.SDOnHttpCallback;
import com.cst.stormdroid.utils.Config;
import com.cst.stormdroid.utils.json.GsonUtil;
import com.cst.stormdroid.utils.log.SDLog;

/**
 * self defined runnable class for http execution
 * @author MonsterStorm
 * @version 1.0
 */
public class SDHttpRunnable implements Runnable {
	private static final String TAG = SDHttpRunnable.class.getSimpleName();

	public static enum HttpMethod {
		GET, POST
	};

	/**
	 * default is string
	 */
	public static enum RequestParamType {
		JSON, XML, DEFAULT
	};

	/**
	 * default is string
	 */
	public static enum ResponseType {
		JSON, XML, DEFAULT
	};

	// http url address
	private String mBaseUrl;
	// params entity
	private Object mParamsEntity;
	// handle message
	private SDOnHttpCallback mOnHttpCallback;
	// http method
	private HttpMethod mMethod;
	// request parameter type
	private RequestParamType mRequestParamType;
	// response type
	private ResponseType mResponseType;
	// result of http execution
	private String mResult = null;
	// request code
	private Integer mRequestCode;
	// context
	private Context mContext;

	//************************************Builder Class start************************************
	/**
	 * SD Http Runnable builder
	 * @author MonsterStorm
	 */
	public static class Builder {
		private SDHttpRunnable mRunnable;
		
		public Builder(){
			mRunnable = new SDHttpRunnable();
		}
		
		public Builder(Context ctx){
			mRunnable = new SDHttpRunnable();
			mRunnable.mContext = ctx;
		}
		
		public Builder(String baseUrl, Context ctx){
			mRunnable = new SDHttpRunnable();
			mRunnable.mBaseUrl = baseUrl;
			mRunnable.mContext = ctx;
		}
		
		public Builder(String baseUrl, Object params, Context ctx){
			mRunnable = new SDHttpRunnable(baseUrl, params, ctx);
		}
		
		public Builder setContext(Context ctx){
			mRunnable.mContext = ctx;
			return this;
		}
		
		public Builder setBaseUrl(String baseUrl){
			mRunnable.mBaseUrl = baseUrl;
			return this;
		}
		
		public Builder setParams(Object params){
			mRunnable.mParamsEntity = params;
			return this;
		}
		
		public Builder setOnHttpCallback(SDOnHttpCallback callback){
			mRunnable.mOnHttpCallback = callback;
			return this;
		}
		
		public Builder setHttpMethod(HttpMethod method){
			mRunnable.mMethod = method;
			return this;
		}
		
		public Builder setRequestParamType(RequestParamType requestParamType){
			mRunnable.mRequestParamType = requestParamType;
			return this;
		}
		
		public Builder setResponseType(ResponseType responseType){
			mRunnable.mResponseType = responseType;
			return this;
		}
		
		public Builder setRequestCode(Integer requestCode){
			mRunnable.mRequestCode = requestCode;
			return this;
		}
		
		public SDHttpRunnable create(){
			return mRunnable;
		}
	}
	//************************************Builder class end************************************
	
	//private method only used by Builder
	private SDHttpRunnable(){
		this.mBaseUrl = null;
		this.mParamsEntity = null;
		this.mOnHttpCallback = null;
		this.mMethod = HttpMethod.POST;
		this.mRequestParamType = RequestParamType.DEFAULT;
		this.mRequestCode = null;
		this.mResponseType = ResponseType.DEFAULT;
		this.mContext = null;
	}
	
	/**
	 * create a runnable object to handler some action
	 * @param baseUrl
	 * @param paramsMap
	 * @param ctx
	 */
	public SDHttpRunnable(String baseUrl, Object params, Context ctx) {
		this(baseUrl, params, null, HttpMethod.POST, RequestParamType.DEFAULT, ResponseType.DEFAULT, ctx);
	}

	/**
	 * create a runnable object to handler some action
	 * @param baseUrl
	 * @param paramsMap
	 * @param onHttpCallback
	 * @param ctx
	 */
	public SDHttpRunnable(String baseUrl, Object params, SDOnHttpCallback onHttpCallback, Context ctx) {
		this(baseUrl, params, onHttpCallback, HttpMethod.POST, RequestParamType.DEFAULT, ResponseType.DEFAULT, ctx);
	}

	/**
	 * create a runnable object to handler some action
	 * @param baseUrl
	 * @param paramsMap
	 * @param onHttpCallback
	 * @param ctx
	 */
	public SDHttpRunnable(String baseUrl, Object params, SDOnHttpCallback onHttpCallback, RequestParamType requestParamType, Context ctx) {
		this(baseUrl, params, onHttpCallback, HttpMethod.POST, requestParamType, ResponseType.DEFAULT, ctx);
	}

	/**
	 * create a runnable object to handler some action
	 * @param baseUrl
	 * @param params
	 * @param onHttpCallback
	 * @param requestParamType
	 * @param responseType
	 * @param ctx
	 */
	public SDHttpRunnable(String baseUrl, Object params, SDOnHttpCallback onHttpCallback, RequestParamType requestParamType, ResponseType responseType, Context ctx) {
		this(baseUrl, params, onHttpCallback, HttpMethod.POST, requestParamType, responseType, ctx);
	}
	
	/**
	 * create a runnable object to handler some action
	 * @param baseUrl
	 * @param params
	 * @param onHttpCallback
	 * @param httpMethod
	 * @param requestParamType
	 * @param ctx
	 */
	public SDHttpRunnable(String baseUrl, Object params, SDOnHttpCallback onHttpCallback, HttpMethod httpMethod, RequestParamType requestParamType, Context ctx) {
		this(baseUrl, params, onHttpCallback, httpMethod, requestParamType, ResponseType.DEFAULT, ctx);
	}

	/**
	 * create a runnable object to handler some action
	 * @param baseUrl
	 * @param params
	 * @param onHttpCallback
	 * @param httpMethod
	 * @param requestParamType
	 * @param responseType
	 * @param ctx
	 */
	public SDHttpRunnable(String baseUrl, Object params, SDOnHttpCallback onHttpCallback, HttpMethod httpMethod, RequestParamType requestParamType, ResponseType responseType, Context ctx) {
		this.mBaseUrl = baseUrl;
		this.mParamsEntity = params;
		this.mOnHttpCallback = onHttpCallback;
		this.mMethod = httpMethod;
		this.mRequestParamType = requestParamType;
		this.mResponseType = responseType;
		this.mContext = ctx;
	}

	@Override
	public void run() {
		if (mParamsEntity != null && mParamsEntity instanceof Map<?, ?>) {
			Map<String, String> mParamsMap = (Map<String, String>) mParamsEntity;
			// get all the params
			List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
			for (Iterator<String> iterator = mParamsMap.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String value = mParamsMap.get(key);
				params.add(new BasicNameValuePair(key, value));
			}

			// do http request
			doHttpWithRetry(this.mMethod, mBaseUrl, params, 3);
		} else {
			doHttpWithRetry(this.mMethod, mBaseUrl, null, 3);
		}
	}

	/**
	 * @param method
	 * @param baseUrl
	 * @param params
	 * @param MAX_RETRY
	 */
	private void doHttpWithRetry(HttpMethod method, String baseUrl, List<BasicNameValuePair> params, int MAX_RETRY) {
		int count = 0;
		while (count < MAX_RETRY) {
			count += 1;
			try {
				if (HttpMethod.GET == method) {
					doHttpGet(baseUrl, params);
				} else {
					doHttpPost(baseUrl, params);
				}
				return;
			} catch (Exception e) {
				if (count < MAX_RETRY) {
					SDLog.i(TAG, "Http Retry : " + baseUrl + "," + count);
					continue;
				} else {
					SDLog.i(TAG, "Http Failed : " + baseUrl + "," + count);
					if (this.mOnHttpCallback != null) {
						this.mOnHttpCallback.onHttpError(mRequestCode, mBaseUrl, null);
					}
				}
			}
			break;
		}
	}

	/**
	 * send a get request
	 * 
	 * @param baseUrl
	 */
	private void doHttpGet(String baseUrl, List<BasicNameValuePair> params) throws Exception {
		// connect params and baseUrl
		DefaultHttpClient httpClient = SDHttpClientHelper.getInstance();

		// add params for http client
		addClientParams(httpClient);

		HttpGet getMethod = createHttpGet(baseUrl, params);

		if (this.mOnHttpCallback != null)
			this.mOnHttpCallback.onHttpStart(mRequestCode, mBaseUrl);
		try {
			// execute get method
			HttpResponse response = httpClient.execute(getMethod);

			//handle response
			handleResponse(response, getMethod);

		} catch (ConnectionPoolTimeoutException e) {
			getMethod.abort();
			e.printStackTrace();
			throw e;
		} catch (ConnectTimeoutException e) {
			getMethod.abort();
			e.printStackTrace();
			throw e;
		} catch (SocketTimeoutException e) {
			getMethod.abort();
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			getMethod.abort();
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * send a post request
	 */
	private void doHttpPost(String baseUrl, List<BasicNameValuePair> params) throws Exception {
		DefaultHttpClient httpClient = SDHttpClientHelper.getInstance();

		addClientParams(httpClient);

		HttpPost postMethod = createHttpPost(baseUrl, params);

		if (this.mOnHttpCallback != null)
			this.mOnHttpCallback.onHttpStart(mRequestCode, mBaseUrl);

		try {
			// execute the post method
			HttpResponse response = httpClient.execute(postMethod);
			//handler response
			handleResponse(response, postMethod);

		} catch (ConnectionPoolTimeoutException e) {
			postMethod.abort();
			e.printStackTrace();
			throw e;
		} catch (ConnectTimeoutException e) {
			postMethod.abort();
			e.printStackTrace();
			throw e;
		} catch (SocketTimeoutException e) {
			postMethod.abort();
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			postMethod.abort();
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * add client params for http client
	 * @param client
	 */
	private void addClientParams(HttpClient client) {
		// handler http 417 error
		HttpParams httpParams = client.getParams();
		httpParams.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
	}

	/**
	 * create http get method
	 * @param baseUrl
	 * @param params
	 * @return
	 */
	private HttpGet createHttpGet(String baseUrl, List<BasicNameValuePair> params) {
		// encode the params
		if(params != null){
			String param = URLEncodedUtils.format(params, Config.PARAMS_ENCODE);
			return new HttpGet(baseUrl + "?" + param);
		} else {
			return new HttpGet(baseUrl);
		}
	}

	/**
	 * create Http post method
	 * @param baseUrl
	 * @param params
	 * @return
	 */
	private HttpPost createHttpPost(String baseUrl, List<BasicNameValuePair> params) throws Exception {
		HttpPost postMethod = new HttpPost(baseUrl);

		//set content type of request 
		if(mRequestParamType != null){
			switch (mRequestParamType) {
			case DEFAULT:
				if(params != null)
					postMethod.setEntity(new UrlEncodedFormEntity(params, Config.PARAMS_ENCODE));	
				break;
			case JSON:
				postMethod.setHeader("Content-Type", "application/json;charset=UTF-8");
				if(mParamsEntity != null){
					HttpEntity entity = new  StringEntity(GsonUtil.toJson(mParamsEntity));
					postMethod.setEntity(entity);
				}
				break;
			case XML:
				postMethod.setHeader("Content-Type", "application/xml;charset=UTF-8");
				break;
			}
		}

		// set content type of response
		if(mResponseType != null){
			switch (mResponseType) {
			case DEFAULT:
				break;
			case JSON:
				postMethod.setHeader("Accept", "application/json");
				break;
			case XML:
				postMethod.setHeader("Accept", "application/xml");
				break;
			}
		}

		return postMethod;
	}

	/**
	 * handler response from server
	 * @param response
	 * @return whether response is
	 */
	private void handleResponse(HttpResponse response, HttpRequestBase request) throws Exception {
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			request.abort();
			this.mResult = String.valueOf(response.getStatusLine().getStatusCode()) + ":" + EntityUtils.toString(response.getEntity(), Config.PARAMS_ENCODE);
			if (this.mOnHttpCallback != null){
				this.mOnHttpCallback.onHttpError(mRequestCode, mBaseUrl, mResult);
			}
		} else {
			this.mResult = EntityUtils.toString(response.getEntity(), Config.PARAMS_ENCODE);
			if (this.mOnHttpCallback != null) {
				this.mOnHttpCallback.onHttpFinish(mRequestCode, mBaseUrl, mResult);
			}
		}
	}

}
