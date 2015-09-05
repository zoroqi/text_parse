package com.zoro.qi.parse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zoro.qi.parse.p.RegexParse;

public class ParseTemplate {
	
	public static void main(String[] args) throws Exception {
		ParseTemplate pt = new ParseTemplate();
		Template t = pt.parse(FileUtils.readFileToString(new File("conf/bingT.data"),"utf-8"));
		String page = FileUtils.readFileToString(new File("conf/content.data"),"utf-8");
		Parse parse = new RegexParse(page, t,"http:\\bing.com");
//		Template t = pt.parse(FileUtils.readFileToString(new File("conf/domT.data"),"utf-8"));
		
		
		Map out = (Map)parse.parse();
//		
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		System.out.println(gson.toJson(out));
	}
	
	@SuppressWarnings("unchecked")
	public Template parse(String templateStr) {
		Gson parseJson = new Gson();
		Map<String,Object> t = (Map<String,Object>)parseJson.fromJson(templateStr, Map.class);
		Template template = new Template();
		
		String parse = (String) t.get(Parse.PAGE_TYPE);
		template.setPageType(parse);
		
//		parseChild(node, rootNode);
//		parse("root", t);
		template.setNode(parse("root", t));
		return template;
	}
	@SuppressWarnings("unchecked")
	public TemplateNode parse(String key,Map<String, Object> template) {
		Map<String, Object> tnode = (Map<String, Object>) template.get(key);
		String type = (String) tnode.get(Parse.TYPE);
		String value = (String) tnode.get(Parse.VALUE);
		List<List<String>> handlers = (List<List<String>>) tnode.get(Parse.HANDLER);
		TemplateNode node = new TemplateNode(key, type, value);
		if (handlers != null) {
			for (List<String> handler : handlers) {
				String[] strA = handler.toArray(new String[handler.size()]);
				node.addHandle(strA);
			}
		}
		if (Parse.TYPE_FIELD.equals(type)) {
			return node;
		} else if (Parse.TYPE_NODE.equals(type) || Parse.TYPE_BLOCK.equals(type)) {

			Map<String, Object> cnode = (Map<String, Object>) tnode.get(Parse.CHILD);
			if (cnode != null) {
				parseChild(cnode, node);
			} else {
				return node;
			}
		}
		return node;
	}
	@SuppressWarnings("unchecked")
	public void parseChild(Map<String, Object> template, TemplateNode t) {
		for (String key : template.keySet()) {
//			Map<String, Object> tnode = (Map<String, Object>) template.get(key);
			TemplateNode node = parse(key,template);
			t.addChild(key, node);
		}
	}
}
