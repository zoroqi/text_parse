package com.zoro.qi.parse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.zoro.qi.parse.Parse;

public abstract class ParseRecursion implements Parse {
	
	/**
	 * 模板
	 */
	private Object data = "";
	private Template template = null;
	protected String url = "";
	
	
	public ParseRecursion(Object data, Template template,String url) {
		this.data = data;
		this.template = template;
		this.url = url;
	}
	
	public ParseRecursion(Template template) {
		this.template = template;
	}
	/**
	 * 解析
	 */
	@Override
	public Map<String, Object> parse() {
		TemplateNode node = template.getNode();
//		OutputNode output = null;
		Map<String,Object> output = new LinkedHashMap<String,Object>();
		output = parseOutput(node,data);
		return output;
	}
	
	private Map<String, Object> parseOutput(TemplateNode template, Object data) {

		String type = template.getType();
//		OutputNode output = new OutputNode();
		Map<String,Object> output = new LinkedHashMap<String,Object>();
		if (type.equals(TYPE_NODE)) {
			output.put(template.getKey(), getNode(template, data));
		} else if (type.equals(TYPE_BLOCK)) {
			output.put(template.getKey(), getBlock(template, data));
		} else if (type.equals(TYPE_FIELD)) {
			output.put(template.getKey(), getField(template, data));
		}

		return output;
	}
	
	protected Object parse(TemplateNode template, Object data) {

		String type = template.getType();
		if (type.equals(TYPE_NODE)) {
			return getNode(template, data);
		} else if (type.equals(TYPE_BLOCK)) {
			return getBlock(template, data);
		} else if (type.equals(TYPE_FIELD)) {
			return getField(template, data);
		}
		return "";
	}
	
	
	/**
	 * 处理单个字段
	 * @param template
	 * @param data
	 * @return String,Map,List
	 */
	public abstract Object getField(TemplateNode template, Object data);
	/**
	 * 处理快 json数组
	 * @param template
	 * @param data
	 * @return
	 */
	public abstract List<Map<String,Object>> getBlock(TemplateNode template, Object data);
	/**
	 * 处理快
	 * @param template
	 * @param data
	 * @return
	 */
	public abstract Map<String,Object> getNode(TemplateNode template, Object data);

	protected Object getData() {
		return data;
	}

	protected void setData(Object data) {
		this.data = data;
	}

	protected Template getTemplate() {
		return template;
	}

	protected void setTemplate(Template template) {
		this.template = template;
	}

	protected String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}
}
