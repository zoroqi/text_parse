package com.zoro.qi.parse.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DomUtils {
	public static final int ALL = 1000000;
	public static final int PART = 100000;
	
	public static Element getDom(String path,Element html) {
		List<Element> nodes = getDoms(path,html);
		if (nodes == null) {
			return null;
		} else {
			return nodes.get(0);
		}
	}
	
	public static List<Element> getDoms(String path, Element html) {

		String[] pathArrays = path.split("/");
		List<Element> result = new ArrayList<Element>();
		result.add(html);
		
		for (String p : pathArrays) {
			List<Element> middle = new ArrayList<Element>();
			String[] pathNode = p.split("@", 5);
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
			
			for (Element n : result) {
				Map<String, Element> ns = new HashMap<String, Element>();
				List<Element> nodes = new ArrayList<Element>();
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
				middle.addAll(nodes);
			}
			result = middle;
		}
		
		return result;
	}
}
