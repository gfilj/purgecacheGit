package purgecache.task;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import purgecache.bean.Varnish;
import purgecache.util.common.Configuration;
import purgecache.util.httpUtil.HttpPurge;

public abstract class VarnishTask implements Runnable {

	public static ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	protected static String varnishSimg;
	protected static String varnishOrigin;
	private static int retryTime;
	private static long waitMillis;

	static {
		// Configuration config = Configuration.getInstance();
		// varnishOrigin = config.getString("varnishOrigin", "");
		// varnishSimg = config.getString("varnishSimg", "");
		// retryTime = config.getInt("retryTime", 3);
		// waitMillis = config.getLong("waitMillis", 300L);
		varnishOrigin = "http://10.112.99.64:6666";
		varnishSimg = "http://10.112.99.66:6666";
		retryTime = 3;
		waitMillis = 2000l;
	}

	private String url;
	private CountDownLatch counDownLatch;
	protected ArrayList<Varnish> varnishsArr = new ArrayList<Varnish>();

	public VarnishTask(String url) {
		this.url = url;
		setVarnishArr();
	}

	protected abstract void setVarnishArr();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		counDownLatch = new CountDownLatch(varnishsArr.size());
		for (Varnish var : varnishsArr) {
			service.execute(new SendPurgeRun(url, var));
		}
		try {
			counDownLatch.await(waitMillis, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Varnish var : varnishsArr) {
			System.out.println(var);
		}
	}

	class SendPurgeRun implements Runnable {

		String url;
		HttpPurge httpPurge;
		Varnish varnish;

		public SendPurgeRun(String url, Varnish varnish) {
			super();
			this.url = url;
			this.varnish = varnish;
			httpPurge = new HttpPurge();
		}

		@Override
		public void run() {
			long start = System.currentTimeMillis();
			boolean retry = false;
			do {
				retry = !httpPurge.purge(url, varnish);
				varnish.incrementRetryTime();
				;
				// System.out.println(String.format("retry:%s,retryTime:%s",
				// retry, varnish.getRetryTime()));
				if (varnish.getRetryTime() >= retryTime) {
					retry = false;
				}
			} while (retry);
			long end = System.currentTimeMillis();
			varnish.setRetVal(end - start);
			varnish.setRetryTime(varnish.getRetryTime());
			counDownLatch.countDown();
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

}
