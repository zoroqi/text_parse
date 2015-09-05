package com.zoro.qi.parse.p;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseRecursion;
import com.zoro.qi.parse.ParseHandleA;
import com.zoro.qi.parse.Template;
import com.zoro.qi.parse.TemplateNode;
import com.zoro.qi.parse.utils.MyStringUtils;

/**
 * 正则模板接卸
 * @author acer
 */
public class RegexParse extends ParseRecursion {
	private static final Log logger = LogFactory.getLog(RegexParse.class);
	public RegexParse(Object data, Template template,String url) {
		super(data, template, url);
	}

	@Override
	public Object getField(TemplateNode template, Object data) {
		String value = MyStringUtils.getRegexContent(template.getValue(), (String)data, 2);
		return value;
	}

	@Override
	public List<Map<String,Object>> getBlock(TemplateNode template, Object data) {
		List<String> strList = MyStringUtils.getRegexContents(template.getValue(), (String)data, 2);
		Map<String, TemplateNode> nodes = template.getChildNode();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (String l : strList) {
			Map<String,Object> v = new LinkedHashMap<String, Object>();
			for (String key : nodes.keySet()) {
				v.put(key, parse(nodes.get(key), l));
			}
			list.add(v);
		}
		
		return list;
	}

	@Override
	public Map<String, Object> getNode(TemplateNode template, Object data) {
		String value = MyStringUtils.getRegexContent(template.getValue(), (String)data, 2);
		Map<String, TemplateNode> childNode = template.getChildNode();
		
		Map<String,Object> output = new LinkedHashMap<String, Object>();
		
		for (String key : childNode.keySet()) {
			output.put(key, parse(childNode.get(key),value));
		}
		return output;
	}
}
