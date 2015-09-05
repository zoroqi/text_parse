package com.zoro.qi.parse.handler;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseHandle;


public class ParseUrlComplementHandler implements ParseHandle {
	private static Log logger = LogFactory.getLog(ParseUrlComplementHandler.class);

	@Override
	public String action(String str, String ...strA) {
		String host = strA[0];
		URL  u = null;
		String s = "";
		try {
			u = new URL(new URL(host),str);
			s = u.toExternalForm();
		} catch (MalformedURLException e) {
			logger.error("url complement error. host : " + host + " url: " + str);
		}
		return s;
	}
}
