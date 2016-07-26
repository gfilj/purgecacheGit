package purgecache.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import purgecache.task.OriginTask;
import purgecache.task.SimgTask;
import purgecache.task.VarnishTask;

@Controller
public class PurgeController {
	
	@RequestMapping(value = "/", method = GET)
	public String home() {
		purgeVarnish();
		return "home";
	}
	public void purgeVarnish(){
		String surl = "http://s.cimg.163.com/i/cms-bucket.nosdn.127.net/d74134e974e04c1d9fe794979d045ef520160714145638.gif.50x50.auto.gif";
		
		URL url = null;
		try {
			url = new URL(surl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String u = null;
		Pattern pattern = Pattern.compile("^/(i|e|pi|pe)/+(.*)\\.([0-9]+)x([0-9]+)(\\.[0-9]+)?(\\.auto)?\\.(webp|jpg|gif)$");
		Matcher matcher=pattern.matcher(url.getPath());
		if(matcher.find()){
			u = matcher.group(2);
		}
		String ourl =  url.getProtocol() + "://" + url.getHost() + "/s/" + u;
		VarnishTask.service.execute(new OriginTask(ourl));
		VarnishTask.service.execute(new SimgTask(surl));
	}
}
