package com.zoro.qi.parse;

public interface Parse {
	public static final String REFERENCE = "reference";
	public static final String URL = "url";
	public static final String PAGE_TYPE = "pageType";
	public static final String PURL = "purl";
	public static final String PARSE = "parse";
	public static final String PARSE_REGEX = "regex";
	public static final String PARSE_JSON = "json";
	public static final String PARSE_JSOUP = "jsoup";
	public static final String DATA = "data";
	public static final String HANDLER = "handler";
	public static final String VALUE = "value";
	public static final String TYPE = "type";
	public static final String TYPE_BLOCK = "block";
	public static final String TYPE_FIELD = "field";
	public static final String TYPE_NODE = "node";
	public static final String CHILD = "childNode";

	public Object parse();
}
