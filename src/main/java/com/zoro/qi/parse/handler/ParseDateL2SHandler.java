package com.zoro.qi.parse.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseHandle;


public class ParseDateL2SHandler implements ParseHandle {
	private static Log logger = LogFactory.getLog(ParseDateL2SHandler.class);

	@Override
	public String action(String str, String ...strA) {
		String defaultStr = strA[1];
		String format = strA[2];
//		String language = strA[1];
		SimpleDateFormat dateFormat = new SimpleDateFormat(format,Locale.CHINA);
		try {
			defaultStr = dateFormat.format(Long.parseLong(str));
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return defaultStr;
	}
	
	public static void main(String[] args) {
//		ParseDateHandler p = new ParseDateHandler("yyyy MM dd HH:mm:ss");
//		System.out.println(p.action("1970 01 01 00:00:00"));
	}
}
