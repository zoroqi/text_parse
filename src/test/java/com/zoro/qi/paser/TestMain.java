package com.zoro.qi.paser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

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
	public static void main(String[] args) throws IOException {
		
		Template t = new Template();
		t.setPageType(Parse.PARSE_REGEX);
		TemplateNode rootNode = new TemplateNode("root",Parse.TYPE_NODE,"[\\s\\S]*");
		t.setNode(rootNode);
		
		
		TemplateNode node1 = new TemplateNode("abstract",
					Parse.TYPE_NODE,"<div class=\"bk_b_container\">[\\s\\S]*?</a></p></div></div></div>");
		node1.addChild("keyWord", new TemplateNode("keyWord",Parse.TYPE_FIELD,"<h1>(.+?)</h1>"));
		node1.addChild("keyWord2", new TemplateNode("keyWord2",Parse.TYPE_FIELD,"</h1><span>(.+?)</span></div>"));
		node1.addChild("imgUrl", new TemplateNode("imgUrl",Parse.TYPE_FIELD,"<img class=\"rms_img\".*?src=\"(.*?)\""));
		node1.addChild("content", new TemplateNode("content",Parse.TYPE_FIELD,"<div class=\"bk_image_bottomright\">([\\s\\S]*?)<div class=\"bk_moreContent\">"));
		rootNode.addChild("abstract", node1);
		
		node1 = new TemplateNode("information",Parse.TYPE_BLOCK,"<div class=\"bk_sidebar_attr_key\">[\\s\\S]*?</div></div>");
		node1.addChild("key", new TemplateNode("key",Parse.TYPE_FIELD,"<div class=\"bk_sidebar_attr_key\">([^>]*?)："));
		node1.addChild("value", new TemplateNode("value",Parse.TYPE_FIELD,"<div class=\"bk_sidebar_attr_value\">(.*?)</div>"));
		rootNode.addChild("information", node1);
		
		node1 = new TemplateNode("abortWord",Parse.TYPE_BLOCK,"<div class=\"bk_sidebar_related_img\">[\\s\\S]*?</p></div>");
		node1.addChild("word", new TemplateNode("key",Parse.TYPE_FIELD,"h=\"ID=.*?>([^>]*?)</a></p>"));
//		node1.addChild("imgUrl", new TemplateNode("imgUrl",Parse.TYPE_FIELD,"data-src=\"(.*?)\">"));
		node1.addChild("word2", new TemplateNode("word2",Parse.TYPE_FIELD,"<p class=\"bk_sidebar_related_tag\">(.*?)</p></div>"));
		TemplateNode node2 = new TemplateNode("imgUrl",Parse.TYPE_BLOCK,"amp;.*?[&\"]");
		node2.addChild("three1", new TemplateNode("three1",Parse.TYPE_FIELD,"amp"));
		node2.addChild("three2", new TemplateNode("three1",Parse.TYPE_FIELD,"amp;([\\d\\w]+)"));
		node1.addChild("imgUrl", node2);
		
		
		rootNode.addChild("abortWord", node1);
		

		
		String page = FileUtils.readFileToString(new File("conf/content.data"),"utf-8");
		Parse parse = new RegexParse(page, t,"http:\\bing.com");
		Map out = (Map)parse.parse();
//		
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		System.out.println(gson.toJson(out));
	}
}
