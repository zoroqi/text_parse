package com.zoro.qi.parse.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseHandle;


public class ParseTestHandler implements ParseHandle {
	private static Log logger = LogFactory.getLog(ParseTestHandler.class);

	@Override
	public String action(String str, String ...strA) {
		return str.length() < 3 ? str : strA[1] ;
	}
	
}
