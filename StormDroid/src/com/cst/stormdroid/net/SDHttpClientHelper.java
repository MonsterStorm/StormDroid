package com.cst.stormdroid.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * HttpClient Singleton HttpGet or HttpPost can redefine parms of httpclient
 * 
 * @author MonsterStorm
 * @version 1.0
 */
public class SDHttpClientHelper {
	private static DefaultHttpClient instance;
	/**
	 * max connection count
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 800;
	/**
	 * max waitting time in queue
	 */
	public final static int WAIT_TIMEOUT = 60000;
	/**
	 * connections for every route
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 400;
	/**
	 * time out for connection
	 */
	public final static int CONNECT_TIMEOUT = 10000;
	/**
	 * time out for content read
	 */
	public final static int READ_TIMEOUT = 30000;

	public static synchronized DefaultHttpClient getInstance() {
		if (null == instance) {
			KeyStore trustStore;
			SSLSocketFactory sf;
			try {
				trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				trustStore.load(null, null);
				sf = new SSLSocketFactoryEx(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			} catch (Exception e) {
				e.printStackTrace();
				sf = SSLSocketFactory.getSocketFactory();
			}

			// init
			HttpParams httpParams = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setUseExpectContinue(httpParams, true);

			ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);
			ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
			ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
			HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", sf, 443));

			ClientConnectionManager conManager = new ThreadSafeClientConnManager(httpParams, schReg);

			instance = new DefaultHttpClient(conManager, httpParams);
		}

		return instance;
	}

	/**
	 * shut down the httpclient
	 */
	public static void shutdown() {
		if (instance != null) {
			instance.getConnectionManager().shutdown();
			instance = null;
		}
	}

	private static class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}
