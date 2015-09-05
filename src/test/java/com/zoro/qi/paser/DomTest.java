package com.zoro.qi.paser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zoro.qi.parse.Parse;
import com.zoro.qi.parse.ParseTemplate;
import com.zoro.qi.parse.Template;
import com.zoro.qi.parse.p.DomParse;

public class DomTest {
	public static void main(String[] args) throws IOException {
		ParseTemplate pt = new ParseTemplate();
		Template t = pt.parse(FileUtils.readFileToString(new File("conf/domT.data"),"utf-8"));
		String page = FileUtils.readFileToString(new File("conf/content1.data"),"utf-8");
		Document document = Jsoup.parse(page);
		Elements roots = document.getElementsByTag("html");
		Element html = roots.get(0);
		Elements childrens = html.children();
		Element body = childrens.get(1);
		System.out.println(body.nodeName());
		Parse parse = new DomParse(html, t,"http:\\bing.com");
		Map out = (Map)parse.parse();
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		System.out.println(gson.toJson(out));
	}
}
