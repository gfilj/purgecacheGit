package purgecache.bean;

public class Varnish {

	private String url;
	private int status;
	private String message;
	private long retVal;
	private int retryTime;
	private boolean useProxy;
	
	public void incrementRetryTime(){
		retryTime++;
	}
	
	public int getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
	}

	public boolean isUseProxy() {
		return useProxy;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public Varnish(String url,boolean useProxy) {
		super();
		this.url = url;
		this.useProxy = useProxy;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getRetVal() {
		return retVal;
	}

	public void setRetVal(long retVal) {
		this.retVal = retVal;
	}

	@Override
	public String toString() {
		return "Varnish [url=" + url + ", status=" + status + ", message=" + message + ", retVal=" + retVal
				+ ", retryTime=" + retryTime + ", useProxy=" + useProxy + "]";
	}
	
	
}
