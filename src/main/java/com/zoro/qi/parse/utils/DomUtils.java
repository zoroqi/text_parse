package com.zoro.qi.parse.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class DomUtils {
	public enum Flag{
		ALL,INDEX
	}
	
	public static Element getDom(String path,Element html) {
		List<Element> nodes = getDoms(path,html);
		if (nodes == null || nodes.size() == 0) {
			return null;
		} else {
			return nodes.get(0);
		}
	}
	
	/**
	 * 得到dom路径下所有元素,应该递归实现的最好改为迭代实现
	 * @param path
	 * @param html
	 * @return
	 */
	public static List<Element> getDoms(String path, Element html) {
		if (html == null) {
			return null;
		}
		// 路径分割
		String[] pathArrays = path.split("/");
		// 作为最后输出的数据和中间数据存储
		List<Element> result = new ArrayList<Element>();
		result.add(html);
		
		// 中间存储 作为路径每向下一层中的存储
		List<Element> middle = null;
		for (String p : pathArrays) {
			middle = new ArrayList<Element>();
			String[] pathNode = p.split("@", 5);
			String tag = pathNode[0];
			String id = pathNode[1];
			String classN = pathNode[2];
			// 是否已id和class作为判断标准
			boolean idAclass = false;
			if ("".equals(id) && "".equals(classN)) {
				idAclass = true;
			}
			// 数字标示
			String indexStr = pathNode[3];
			int index = 0;
			Flag flag = null;
			if (indexStr.equals("*")) {
				flag = Flag.ALL;
			} else if (indexStr.contains("-")) {
				flag = Flag.INDEX;
			} else {
				index = Integer.parseInt(indexStr);
			}
			
			for (Element n : result) {
				// 存储每一个索引的键值
				Map<String, Element> ns = new HashMap<String, Element>();
				
				// 在本层中找到的节点
				List<Element> nodes = new ArrayList<Element>();
				Elements childrens = n.children();
				// 标签计数
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
				if (flag == Flag.ALL) {
					for (String key : ns.keySet()) {
						nodes.add(ns.get(key));
					}
				} else if (flag == Flag.INDEX) {
					
					if (indexStr.startsWith("-")) {
						index = Integer.parseInt(indexStr.substring(1, indexStr.length()));
						index = cIndex-index;
						Element ab = ns.get(index + "");
						if (ab != null) {
							nodes.add(ab);
						} else {
							return null;
						}
					} else {
						String [] a = indexStr.split("-",2);
						int start = Integer.parseInt(a[0]);
						int end = Integer.parseInt(a[1]);
						for (int i = start; i <= end; i++) {
							Element ab = ns.get(i+"");
							if (ab != null) {
								nodes.add(ab);
							}
						}
					}
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
