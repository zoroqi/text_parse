package com.zoro.qi.parse.p;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Element;

import com.zoro.qi.parse.ParseRecursion;
import com.zoro.qi.parse.ParseHandleA;
import com.zoro.qi.parse.Template;
import com.zoro.qi.parse.TemplateNode;
import com.zoro.qi.parse.utils.DomUtils;
import com.zoro.qi.parse.utils.MyStringUtils;

/**
 * dom树模板解析
 * @author acer
 */
public class DomParse extends ParseRecursion {
	private static final Log logger = LogFactory.getLog(DomParse.class);
	public DomParse(Object data, Template template,String url) {
		super(data, template, url);
	}

	@Override
	public Object getField(TemplateNode template, Object data) {
		Element field = DomUtils.getDom(template.getValue(), (Element)data);
		if (field == null) {
			return "";
		}
		return field.text();
	}

	@Override
	public List<Map<String, Object>> getBlock(TemplateNode template, Object data) {
		List<Element> blocks = null;
		if (data != null) {
			 blocks = DomUtils.getDoms(template.getValue(), (Element) data);
		}
		Map<String, TemplateNode> nodes = template.getChildNode();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (blocks != null) {
			for (Element l : blocks) {
				Map<String, Object> v = new LinkedHashMap<String, Object>();
				for (String key : nodes.keySet()) {
					v.put(key, parse(nodes.get(key), l));
				}
				list.add(v);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getNode(TemplateNode template, Object data) {
		Element field = null;
		if (data != null) {
			field = DomUtils.getDom(template.getValue(), (Element) data);
		}

		Map<String, TemplateNode> childNode = template.getChildNode();

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (field != null) {
			for (String key : childNode.keySet()) {
				map.put(key, parse(childNode.get(key), field));
			}
		}
		return map;
	}
}
