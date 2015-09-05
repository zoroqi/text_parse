package com.zoro.qi.parse.p;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zoro.qi.parse.Parse;
import com.zoro.qi.parse.Template;
import com.zoro.qi.parse.TemplateNode;
import com.zoro.qi.parse.p.RegexParse;
import com.zoro.qi.parse.stack.RegexParseStack;
import com.zoro.qi.parse.stack.TemplateStack;

public class TestMain {
	
	public static final int ALL = 1000000;
	public static final int PART = 100000;
	public static void main(String[] args) throws IOException {
		
		
		String page = FileUtils.readFileToString(new File("conf/content1.data"),"utf-8");
		Document document = Jsoup.parse(page);
		Elements roots = document.getElementsByTag("html");
		Element html = roots.get(0);
		Elements childrens = html.children();
		Element body = childrens.get(1);
//		childrens =body.children();
//		for (Element e : childrens) {
//			System.out.print(e.tagName());
//			System.out.print("\tclass:" + e.attr("class"));
//			System.out.println("\tid:" + e.attr("id"));
//		}
//		System.out.println(body.getElementsByTag("div").size());
//		System.out.println(body.childNodeSize());
		String path = "div@@layout@4/div@@g-2-r@1/div@@g-3-c@0/div@@section@0/div@@section-list mark@4/span@@hover@*/a@@@*";
		List<Element> n = gets(path,body);
		System.out.println(n.size());
		System.out.println(n.get(0).text());
	}
	
	public static Element get(String path,Element html) {
		
		String[] pathArrays = path.split("/");
		Element node = html;
		for (String p : pathArrays) {
			String[] pathNode = p.split("@",4);
			String tag = pathNode[0];
			String id = pathNode[1];
			String classN = pathNode[2];
			String indexStr = pathNode[3];
			int index = 0;
			if (indexStr.equals("*") || indexStr.contains("-")) {
				index = ALL;
			} else {
				index = Integer.parseInt(indexStr);
			}
			
			Element shouxuan = null;
			Element beixuan = null;
			Elements childrens = node.children();
			int cIndex = 0;
			for (Element c : childrens) {
				String cTag = c.tagName();
				if (tag.equals(cTag)) {
					String cId = c.attr("id");
					String cClassN = c.attr("class");
					if (cId.equals(id) && cClassN.equals(classN)) {
						if (cIndex == index || index == ALL) {
							shouxuan = c;
							cIndex = 0;
							break;
						} else {
							if (beixuan == null) {
								beixuan = c;
							}
						}
					}
					cIndex++;
				}
			}
			if (shouxuan != null) {
				node = shouxuan;
			} else {

				if (beixuan != null) {
					node = beixuan;
				} else {
					return null;
				}
			}
			
		}
		return node;
	}
	
	
	
	public static List<Element> gets(String path, Element html) {

		String[] pathArrays = path.split("/");
		Element node = html;
		List<Element> nodes = new ArrayList<Element>();

		nodes.add(node);
		for (String p : pathArrays) {
			Map<String, Element> ns = new HashMap<String, Element>();
			String[] pathNode = p.split("@", 4);
			String tag = pathNode[0];
			String id = pathNode[1];
			String classN = pathNode[2];
			boolean idAclass = false;
			if ("".equals(id) && "".equals(classN)) {
				idAclass = true;
			}
			String indexStr = pathNode[3];
			int index = 0;
			int a = 0;
			if (indexStr.equals("*")) {
				a = ALL;
			} else if (indexStr.contains("-")) {
				a = PART;
			} else {
				index = Integer.parseInt(indexStr);
			}
			for (Element n : nodes) {
				Elements childrens = n.children();
				int cIndex = 0;
				for (Element c : childrens) {
					String cTag = c.tagName();
					if (tag.equals(cTag)) {
						String cId = c.attr("id");
						String cClassN = c.attr("class");
						if ((cId.equals(id) && cClassN.equals(classN)) || idAclass) {
							ns.put(cIndex + "", c);
						}
						cIndex++;
					}
				}
			}
			nodes = new ArrayList<Element>();
			if (a == ALL) {
				for (int i = 0; i < ns.size(); i++) {
					nodes.add(ns.get(i + ""));
				}
			} else if (a == PART) {
				// if (indexStr.startsWith("-")) {
				//
				// } else {
				// String[] parta = indexStr.split("-",3);
				// }
			} else {
				Element ab = ns.get(index + "");
				if (ab != null) {
					nodes.add(ab);
				} else {
					return null;
				}
			}
		}
		return nodes;
	}
}
