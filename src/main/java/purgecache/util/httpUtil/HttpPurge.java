package purgecache.util.httpUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

import purgecache.bean.PurgeResult;
import purgecache.bean.Varnish;

public class HttpPurge extends HttpRequestBase {

	public static final String METHOD_NAME = "PURGE";

	@Override
	public String getMethod() {
		return METHOD_NAME;
	}

	public HttpPurge() {
		super();
	}

	public HttpPurge(final String uri) {
		super();
		setURI(URI.create(uri));
	}

	private PurgeResult callPrugeVarnish(String urlHost, String urlPath, int urlPort, String urlProtocol,
			boolean useProxy, String urlProxyHost, int urlProxyPort, String urlProxyProtocol) {

		HttpHost host = new HttpHost(urlHost, urlPort, urlProtocol);

		HttpClient httpclient = HttpClientBuilder.create().build();
		this.setURI(URI.create(urlPath));
		if (useProxy) {
			HttpHost proxy = new HttpHost(urlProxyHost, urlProxyPort, urlProtocol);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(1000).setConnectionRequestTimeout(300).build();
			this.setConfig(config);
		}
		try {
			HttpResponse response = httpclient.execute(host, this);
//			System.out.println(String.format("statusLine:%s, content:%s", response.getStatusLine(),
//					response.getEntity().toString()));
			return new PurgeResult(response.getStatusLine().getStatusCode(),
					response.getStatusLine().getReasonPhrase());
		} catch (Exception e) {
			System.out.println(String.format("connect failed!"));
		}
		return null;
	}

	private PurgeResult purgeWithProxy(String surl, String proxysUrl, boolean useProxy) {
		URL url = null;
		try {
			url = new URL(surl);

		} catch (MalformedURLException e) {
			System.err.println(String.format("the url %s is not malformed", surl));
			e.printStackTrace();
		}
		URL urlProxy = null;
		try {
			urlProxy = new URL(proxysUrl);

		} catch (MalformedURLException e) {
			System.err.println(String.format("the proxyUrl %s is not malformed", urlProxy));
		}
		if (useProxy) {

			return callPrugeVarnish(url.getHost(), url.getPath(), url.getPort(), url.getProtocol(), true,
					urlProxy.getHost(), urlProxy.getPort(), urlProxy.getProtocol());
		} else {
			return callPrugeVarnish(urlProxy.getHost(), url.getPath(), urlProxy.getPort(), urlProxy.getProtocol(),
					false, null, 0, null);
		}
	}

	public boolean purge(String url, Varnish varnish) {
		PurgeResult purgeResult = purgeWithProxy(url, varnish.getUrl(), varnish.isUseProxy());
		if (purgeResult != null) {

			varnish.setStatus(purgeResult.getStatus());
			varnish.setMessage(purgeResult.getMessage());
		} 
		return judgeResult(varnish);
	}

	public boolean judgeResult(Varnish varnish) {
		return 200 == varnish.getStatus();
	}
}
