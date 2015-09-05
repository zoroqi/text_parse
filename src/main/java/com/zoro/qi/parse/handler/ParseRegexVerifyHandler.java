package com.zoro.qi.parse.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseHandle;


public class ParseRegexVerifyHandler implements ParseHandle {
	private static Log logger = LogFactory.getLog(ParseRegexVerifyHandler.class);

	@Override
	public String action(String str, String ...strA) {
		String regex = strA[0];
		String s = "false";
		try {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.matches()) {
				s = "true";
			} else {
				s = "false";
			}
		} catch (Exception e) {
			logger.error("regex verify is error. regex : " + regex + " date: " + str + " " + e.getMessage());
		}
		return s;
	}
}
