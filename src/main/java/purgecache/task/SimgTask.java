package purgecache.task;

import purgecache.bean.Varnish;

public class SimgTask extends VarnishTask{


	public SimgTask(String url) {
		super(url);
	}

	@Override
	protected void setVarnishArr() {
		for(String varnish : varnishSimg.split(",")){
			varnishsArr.add(new Varnish(varnish,true));
		}
	}
	
}
