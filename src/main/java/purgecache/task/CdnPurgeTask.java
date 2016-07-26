package purgecache.task;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class CdnPurgeTask implements Runnable {

	private String cdnUrl;
	private String cleanUrl;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public void cdnPostPurge() {
		String json = "{\"urls\":[\"http://img1.cache.netease.com/cms/test/lua.png\"]}";
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://r.chinacache.com/content/refresh");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", "163web"));
			nvps.add(new BasicNameValuePair("password", "F}?/7r1f_4"));
			nvps.add(new BasicNameValuePair("task", json));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpclient.execute(httpPost);

			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			System.out.println(entity.toString());
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void cdnGetPurge() {
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
//			String url = "http://ccms.chinacache.com/index.jsp?user=163web&pswd=F}?/7r1f_4&ok=ok&urls="+URLEncoder.encode("http://img1.cache.netease.com/cms/test/lua.png","UTF-8");
			String url = "http://ccms.chinacache.com/index.jsp?user=163web&pswd=F}?/7r1f_4&ok=ok&urls=http%3A%2F%2Fimg1.cache.netease.com%2Fcms%2Ftest%2Flua.png";

			System.out.println(url);
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			// The underlying HTTP connection is still held by the response
			// object
			// to allow the response content to be streamed directly from the
			// network socket.
			// In order to ensure correct deallocation of system resources
			// the user MUST call CloseableHttpResponse#close() from a finally
			// clause.
			// Please note that if response content is not fully consumed the
			// underlying
			// connection cannot be safely re-used and will be shut down and
			// discarded
			// by the connection manager.

			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void main(String[] args) {
		
//		new CdnPurgeTask().cdnGetPurge();
		new CdnPurgeTask().cdnPostPurge();
	}
}
