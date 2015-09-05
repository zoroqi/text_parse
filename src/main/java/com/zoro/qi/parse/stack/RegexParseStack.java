package com.zoro.qi.parse.stack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zoro.qi.parse.ParseRecursion;
import com.zoro.qi.parse.ParseHandleA;
import com.zoro.qi.parse.Template;
import com.zoro.qi.parse.utils.MyStringUtils;

/**
 * 正则模板解析
 * @author acer
 */
public class RegexParseStack extends ParseStack {
	private static final Log logger = LogFactory.getLog(RegexParseStack.class);
	public RegexParseStack(Object data, TemplateStack template,String url) {
		super(data, template, url);
	}

	@Override
	public Object getField(TemplateNodeStack template, Object data) {
		data = template.getParseData();
		String value = MyStringUtils.getRegexContent(template.getValue(), data.toString(), 2);
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getBlock(TemplateNodeStack template, Object data) {
		List<String> strList = MyStringUtils.getRegexContents(template.getValue(), data.toString(), 2);
		Map<String, TemplateNodeStack> nodes = template.getChildNode();
		
		List<Object> list = (List<Object>) template.getOutputData();
		for (String l : strList) {
			Map<String,Object> v = new LinkedHashMap<String, Object>();
			
			for (String key : nodes.keySet()) {
				TemplateNodeStack cnode = nodes.get(key);
				cnode = parse(cnode, l);
				super.templateStack.push(cnode);
//				v.put(key, parse(cnode, l));
			}
			
		}
		
		return null;
	}

	@Override
	public Map<String, Object> getNode(TemplateNodeStack template, Object data) {
		String value = MyStringUtils.getRegexContent(template.getValue(), (String)data, 2);
		Map<String, TemplateNodeStack> nodes = template.getChildNode();
		
		Map<String,Object> output = new LinkedHashMap<String, Object>();
		
		for (String key : nodes.keySet()) {
			TemplateNodeStack cnode = nodes.get(key);
			parse(cnode,value);
			super.templateStack.push(cnode);
//			output.put(key, parse(childNode.get(key),value));
		}
		return output;
	}
}
