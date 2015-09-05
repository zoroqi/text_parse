package com.zoro.qi.parse;

import java.util.List;
import java.util.Map;

import com.zoro.qi.parse.handler.ParseExceptionHandler;

public class ParseHandleA implements ParseHandle {
	private List<String[]> action = null;
	public ParseHandleA(List<String[]> action) {
		this.action = action;
	}
	@Override
	public String action(String str, String ...strA) {
		ParseHandle handler = null;
		String value = str;
		String handlerName = "";
		for (String[] a: action) {
			handlerName = "com.zoro.qi.parse.handler.Parse" + a[0] + "Handler";
			handler = getHandler(handlerName);
			value = handler.action(value,a);
		}
		return value;
	}
	/**
	 * 得到处理类
	 * @param handle
	 * @return
	 */
	public ParseHandle getHandler(String handle) {
		ParseHandle re = null;
		Class<?> c = null;
		try {
			 c = Class.forName(handle);
			 re = (ParseHandle)c.newInstance(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (re == null) {
			return new ParseExceptionHandler();
		}
		return re;
	}
}
