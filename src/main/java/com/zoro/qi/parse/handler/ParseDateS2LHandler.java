package com.zoro.qi.parse.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseHandle;


public class ParseDateS2LHandler implements ParseHandle {
	private static Log logger = LogFactory.getLog(ParseDateS2LHandler.class);

	@Override
	public String action(String str, String ...strA) {
		long time = System.currentTimeMillis();
		String defaultStr = strA[1];
		String format = strA[2];
//		String language = strA[1];
		SimpleDateFormat dateFormat = new SimpleDateFormat(format,Locale.CHINA);
		try {
			time = dateFormat.parse(str).getTime();
		} catch (ParseException e) {
			logger.error("date format is error. format : " + format + " date: " + str);
			return defaultStr;
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			return defaultStr;
		}
		return time+"";
	}
	
	public static void main(String[] args) {
//		ParseDateHandler p = new ParseDateHandler("yyyy MM dd HH:mm:ss");
//		System.out.println(p.action("1970 01 01 00:00:00"));
	}
}
