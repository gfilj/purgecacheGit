package purgecache.task;

import purgecache.bean.Varnish;

public class OriginTask extends VarnishTask{


	public OriginTask(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void setVarnishArr() {
		for(String varnish : varnishOrigin.split(",")){
			varnishsArr.add(new Varnish(varnish,false));
		}
	}
	
}
