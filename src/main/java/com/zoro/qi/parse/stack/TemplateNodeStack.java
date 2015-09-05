package com.zoro.qi.parse.stack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TemplateNodeStack{
	private String key = "";
	/**
	 * 类型 field block node
	 */
	private String type = "";
	/**
	 * 要解析的路径
	 */
	private String value = "";
	/**
	 * 进行处理
	 */
	private List<String[]> handlers = null;
	/**
	 * 下级数据孩子节点
	 */
	private Map<String,TemplateNodeStack> childNode = null;
	
	/**
	 * 本节点数据
	 */
	private Object parseData = null;
	
	private Object outputData = null;
	private Object fatherData = null;
	
	public TemplateNodeStack(String key, String type, String value, List<String[]> handlers, boolean isData,
			Map<String, TemplateNodeStack> childNode) {
		super();
		this.key = key;
		this.type = type;
		this.value = value;
		if (handlers == null) {
			handlers =  new ArrayList<String[]>();
		}
		this.handlers = handlers;
		this.childNode = childNode;
		if (childNode == null) {
			childNode =  new LinkedHashMap<String, TemplateNodeStack>();
		}
	}
	
	public TemplateNodeStack(String key, String type, String value) {
		super();
		this.key = key;
		this.type = type;
		this.value = value;
		handlers =  new ArrayList<String[]>();
		childNode = new LinkedHashMap<String, TemplateNodeStack>();
	}
	
	public TemplateNodeStack() {
		super();
		handlers =  new ArrayList<String[]>();
		childNode = new LinkedHashMap<String, TemplateNodeStack>();
	}
	
	public void addChild(String key,TemplateNodeStack t) {
		childNode.put(key, t);
	}
	
	public void addHandle(String[] handler) {
		handlers.add(handler);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public List<String[]> getHandlers() {
		return handlers;
	}

	protected void setHandlers(List<String[]> handlers) {
		this.handlers = handlers;
	}

	public Map<String, TemplateNodeStack> getChildNode() {
		return childNode;
	}
	public void setChildNode(Map<String, TemplateNodeStack> childNode) {
		this.childNode = childNode;
	}

	protected Object getParseData() {
		return parseData;
	}

	protected void setParseData(Object parseData) {
		this.parseData = parseData;
	}

	protected Object getOutputData() {
		return outputData;
	}

	protected void setOutputData(Object outputData) {
		this.outputData = outputData;
	}

	protected Object getFatherData() {
		return fatherData;
	}

	protected void setFatherData(Object fatherData) {
		this.fatherData = fatherData;
	}
	
}
