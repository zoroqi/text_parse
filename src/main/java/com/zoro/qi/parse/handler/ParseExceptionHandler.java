package com.zoro.qi.parse.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseHandle;


public class ParseExceptionHandler implements ParseHandle {
	private static Log logger = LogFactory.getLog(ParseExceptionHandler.class);

	@Override
	public String action(String str, String ...strA) {
		logger.warn("没有对应处理方法, the handler is not find !");
		return "没有对应处理方法, the handler is not find !";
	}
	
}
