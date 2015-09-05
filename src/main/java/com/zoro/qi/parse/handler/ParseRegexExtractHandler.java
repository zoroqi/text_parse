package com.zoro.qi.parse.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseHandle;


public class ParseRegexExtractHandler implements ParseHandle {
	private static Log logger = LogFactory.getLog(ParseRegexExtractHandler.class);

	@Override
	public String action(String str, String ...strA) {
		String regex = strA[0];
		int groupNum = Integer.parseInt(strA[1]);
		String s = "";
		try {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			if (m.find()) {
				s = m.group(groupNum);
			} else {
				logger.warn("regex extract is error. regex : " + regex + " date: " + str);
			}
		} catch (Exception e) {
			logger.error("regex extract is error. regex : " + regex + " date: " + str + " " + e.getMessage());
		}
		return s;
	}
}
