package com.zoro.qi.paser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class JsoupTraversal {
	public static void main(String[] args) throws IOException {
//		String pageContent = FileUtils.readFileToString(new File("file/小米论坛.html"), "utf-8");
//		Document document = Jsoup.parse(pageContent, "http://www.800pharm.com/shop/search_InfoList.html?groupId=1_H20090834");
//		List<Node> childNode = document.childNodes();
//		System.out.println(childNode.size());
//		for (Node node : childNode) {
////			System.out.println(node.attr("class"));
//			traversal(node,"");
//		}
		JsoupTraversal t = new JsoupTraversal();
		String url = "http://xueqiu.com/S/SH603806";
		String charset = "utf-8";
//		// 设置要查找字段
//		t.searchCI("pager talc zwpager","无");
		t.searchCI("hover","无");
		t.bfdXpath(url,charset);
//		System.out.println(t.getUrlContent(url,charset));
	}
	
	// 输出队列
	private LinkedList<QSnode> qs = new LinkedList<QSnode>();
	// 要查找的 class id
	private Map<String,String> ci = new LinkedHashMap<String,String>();
	
	public void searchCI(String classN, String id) {
		if (classN == null || id == null) {
			System.err.println("没有查找的数据");
		}
		ci.put(classN, id);
	}
	// 解析
	public void bfdXpath(String url, String charset) throws IOException {
//		String pageContent = FileUtils.readFileToString(new File("file/tmall.txt"), "utf-8");
		if (ci.size() == 0) {
			System.err.println("没有查找的数据");
		}
		String pageContent = "";
		// TODO 读取本地文件
		pageContent = FileUtils.readFileToString(new File("conf/content1.data"), "utf-8");
//		pageContent = getUrlContent(url,charset);
		
		// 删除不要的标签
//		pageContent = pageContent.replaceAll("(?!)<script[\\s\\S]*?</\\s*?script>", "");
//		pageContent = pageContent.replaceAll("(?!)<style[\\s\\S]*?</\\s*?style>", "");
		
		// 生成dom树
		Document document = Jsoup.parse(pageContent, url);
		// 得到body节点
		Element body = document.getElementsByTag("body").get(0);
		
		// 接卸
		xmlPath(body,0);
	}
	/**
	 * 得到网页正文
	 * @param url url 
	 * @param charset 编码格式
	 * @return
	 */
	public String getUrlContent(String url, String charset) {
		if (url == null || url.equals("")) {
			return "";
		}
		if (charset == null || charset.equals("")) {
			charset = "utf-8";
		}
		// 访问链接
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpGet httpGet = new HttpGet(url);
		// 追加header
//		httpGet.setHeader("Referer", "http://detail.tmall.com/item.htm?id=22455211513&skuId=33223122446&areaId=440300&cat_id=2&rn=64d697d765bd45b6a3c8a1f0405a343a&user_id=230988517&is_b=1");
		HttpResponse response;
		String pageContent = "";
		try {
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity en = response.getEntity();
				pageContent = EntityUtils.toString(en, charset);
			} else {
				System.err.println("无法访问");
				return "";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("无法访问");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("无法访问");
		}
		return pageContent;
	}
	
	/**
	 * 查找制定class 标签 id
	 * @param node
	 * @param depth
	 */
	public void xmlPath(Node node , int depth) {
		List<Node> nodes = node.childNodes();
		Map<String,Integer> tagNumMap = new HashMap<String, Integer>();
		for (Node child : nodes) {
			// 标签名
			String nodeName = child.nodeName();
			// 跳过没用的
			if (nodeName.equals("#text")) {
				continue;
			}
			
			// 计算单个标签的 个数
			if (tagNumMap.get(nodeName) == null) {
				tagNumMap.put(nodeName, 0);
			}
			// 得到个数
			int tagNum = tagNumMap.get(nodeName);
			
			// 存储新的个数
			tagNumMap.put(nodeName, tagNum+1);
			
			// 得到class 和id
			String classN = child.attr("class");
			String id = child.attr("id");
			
			// 从新赋值
			classN = classN.equals("") ? "无" : classN;
			id = id.equals("") ? "无" : id;
			// 入队
			qs.add(new QSnode(classN, id, tagNum, child.nodeName()));
			// 判断是否要输出
			String searchId = ci.get(classN);
			if (searchId != null) {
				if (searchId.equals(id)) {
					System.out.print(classN + ":\t");
					output();
				}
			}
			
			xmlPath(child,depth+1);
		}
		// 出队
		if (!qs.isEmpty()) {
			qs.removeLast();
		}
	}
	private void output() {
		int i = 0;
		for (QSnode node : qs) {
			if (i != 0) {
				System.out.print("/");
			}
			System.out.print(node.getName() + "@" + (node.getId().equals("无") ? "" : node.getId()) + "@"
					+ (node.getClassN().equals("无") ? "" : node.getClassN()) + "@" + node.getNum());
			i++;
		}
		System.out.println();
		
	}
	// 队列
	private class QSnode {
		private String classN = "";
		private String id = "";
		private int num = 0;
		private String name = "";
		public QSnode(String classN, String id, int num,String name) {
			super();
			this.classN = classN;
			this.id = id;
			this.num = num;
			this.name = name;
		}
		public String getClassN() {
			return classN;
		}
		public String getId() {
			return id;
		}
		public int getNum() {
			return num;
		}
		public String getName() {
			return name;
		}
	}
	
	public static void traversal(Node node,String fenge) {
		if (!node.nodeName().equals("#text")) {
			System.out.print(fenge + node.nodeName());
			if (!node.attr("id").equals("")) {
				System.out.print("\tid: " + node.attr("id"));
			} else {
				System.out.print("\tid: 无");
			}
			if (!node.attr("class").equals("")) {
				System.out.print("\tclass: " + node.attr("class"));
			} else {
				System.out.print("\tclass: 无");
			}
//			if (node.nodeName().equals("a")) {
//				System.out.print("\t"+ node.);
//			}
			System.out.println("\t"+ node.childNodeSize());
		}
		List<Node> childNode = node.childNodes();
		if (childNode == null || childNode.size() == 0 ) {
			return;
		}
		
		for (Node child : childNode) {
			traversal(child,fenge+"\t");
		}
	}
}


