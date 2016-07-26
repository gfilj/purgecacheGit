package purgecache.task;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import purgecache.web.PurgeController;

public class Test {
	public static void main(String[] args) {
//		new SimgTask("http://s.cimg.163.com/i/cms-bucket.nosdn.127.net/d74134e974e04c1d9fe794979d045ef520160714145638.gif.50x50.auto.gif").run();
		
		new PurgeController().purgeVarnish();

	}
}
