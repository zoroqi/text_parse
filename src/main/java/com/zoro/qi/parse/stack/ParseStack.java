package com.zoro.qi.parse.stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.zoro.qi.parse.Parse;


public abstract class ParseStack implements Parse {
	
	/**
	 * 模板
	 */
	private Object data = "";
	private TemplateStack template = null;
	protected String url = "";
	protected Stack<TemplateNodeStack> templateStack = new Stack<TemplateNodeStack>();
	
	public ParseStack(Object data, TemplateStack template,String url) {
		this.data = data;
		this.template = template;
		this.url = url;
	}
	
	public ParseStack(TemplateStack template) {
		this.template = template;
	}
	/**
	 * 解析
	 */
	@Override
	public Map<String, Object> parse() {
		TemplateNodeStack node = template.getNode();
//		OutputNode output = null;
		Map<String,Object> output = new LinkedHashMap<String,Object>();
		node.setParseData(data);
		templateStack.push(node);
		
		String type = node.getType();
		if (type.equals(TYPE_NODE)) {
			node.setFatherData(new LinkedHashMap<String, Object>());
		} else if (type.equals(TYPE_BLOCK)) {
			node.setFatherData(new ArrayList<Object>());
		}

		output = parseOutput();
		return output;
	}
	
	private Map<String, Object> parseOutput() {
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		
		while (!templateStack.empty()) {
			TemplateNodeStack node = templateStack.pop();
			Object d = node.getParseData();
			String type = node.getType();
			parse(node, d);
			if (type.equals(TYPE_NODE)) {
//				if (node.getOutputData() == null) {
//					node.setOutputData(new LinkedHashMap<String, Object>());
//				}
				getNode(node, d);
			} else if (type.equals(TYPE_BLOCK)) {
//				if (node.getOutputData() == null) {
//					node.setOutputData(new ArrayList<Object>());
//				}
				getBlock(node, d);
			} else if (type.equals(TYPE_FIELD)) {
				getField(node, d);
			}
		}
		return output;
	}
	
	@SuppressWarnings("unchecked")
	protected TemplateNodeStack parse(TemplateNodeStack template, Object data) {

		String type = template.getType();
		template.setParseData(data);
		if (type.equals(TYPE_NODE)) {
			if (template.getOutputData() == null) {
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				if (template.getFatherData() instanceof List) {
					((List<Object>)template.getFatherData()).add(map);
				} else if (template.getFatherData() instanceof Map) {
					((Map<String,Object>)template.getFatherData()).put(template.getKey(),map);
				}
				template.setOutputData(map);
			}
		} else if (type.equals(TYPE_BLOCK)) {
			if (template.getOutputData() == null) {
				List<Object> list = new ArrayList<Object>();
				if (template.getFatherData() instanceof List) {
					((List<Object>)template.getFatherData()).add(list);
				} else if (template.getFatherData() instanceof Map) {
					((Map<String,Object>)template.getFatherData()).put(template.getKey(),list);
				}
				template.setOutputData(list);
			}
		} else if (type.equals(TYPE_FIELD)) {
			Object field = getField(template, data);
			if (template.getFatherData() instanceof List) {
				((List<Object>)template.getFatherData()).add(field);
			} else if (template.getFatherData() instanceof Map) {
				((Map<String,Object>)template.getFatherData()).put(template.getKey(),field);
			}
		}
		return template;
	}
	
	
	/**
	 * 处理单个字段
	 * @param template
	 * @param data
	 * @return String,Map,List
	 */
	public abstract Object getField(TemplateNodeStack template, Object data);
	/**
	 * 处理快 json数组
	 * @param template
	 * @param data
	 * @return
	 */
	public abstract List<Map<String,Object>> getBlock(TemplateNodeStack template, Object data);
	/**
	 * 处理快
	 * @param template
	 * @param data
	 * @return
	 */
	public abstract Map<String,Object> getNode(TemplateNodeStack template, Object data);

	protected Object getData() {
		return data;
	}

	protected void setData(Object data) {
		this.data = data;
	}

	protected TemplateStack getTemplate() {
		return template;
	}

	protected void setTemplate(TemplateStack template) {
		this.template = template;
	}

	protected String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}
}
