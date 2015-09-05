package com.zoro.qi.parse.stack;

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
		
		TemplateStack t = new TemplateStack();
		t.setPageType(Parse.PARSE_REGEX);
		TemplateNodeStack rootNode = new TemplateNodeStack("root",Parse.TYPE_NODE,"[\\s\\S]*");
		t.setNode(rootNode);
		
		
		TemplateNodeStack node1 = new TemplateNodeStack("abstract",
					Parse.TYPE_NODE,"<div class=\"bk_b_container\">[\\s\\S]*?</a></p></div></div></div>");
		node1.addChild("keyWord", new TemplateNodeStack("keyWord",Parse.TYPE_FIELD,"<h1>(.+?)</h1>"));
		node1.addChild("keyWord2", new TemplateNodeStack("keyWord2",Parse.TYPE_FIELD,"</h1><span>(.+?)</span></div>"));
		node1.addChild("imgUrl", new TemplateNodeStack("imgUrl",Parse.TYPE_FIELD,"<img class=\"rms_img\".*?src=\"(.*?)\""));
		node1.addChild("content", new TemplateNodeStack("content",Parse.TYPE_FIELD,"<div class=\"bk_image_bottomright\">([\\s\\S]*?)<div class=\"bk_moreContent\">"));
		rootNode.addChild("abstract", node1);
		
		node1 = new TemplateNodeStack("information",Parse.TYPE_BLOCK,"<div class=\"bk_sidebar_attr_key\">[\\s\\S]*?</div></div>");
		node1.addChild("key", new TemplateNodeStack("key",Parse.TYPE_FIELD,"<div class=\"bk_sidebar_attr_key\">([^>]*?)："));
		node1.addChild("value", new TemplateNodeStack("value",Parse.TYPE_FIELD,"<div class=\"bk_sidebar_attr_value\">(.*?)</div>"));
		rootNode.addChild("information", node1);
		
		node1 = new TemplateNodeStack("abortWord",Parse.TYPE_BLOCK,"<div class=\"bk_sidebar_related_img\">[\\s\\S]*?</p></div>");
		node1.addChild("word", new TemplateNodeStack("key",Parse.TYPE_FIELD,"h=\"ID=.*?>([^>]*?)</a></p>"));
//		node1.addChild("imgUrl", new TemplateNode("imgUrl",Parse.TYPE_FIELD,"data-src=\"(.*?)\">"));
		node1.addChild("word2", new TemplateNodeStack("word2",Parse.TYPE_FIELD,"<p class=\"bk_sidebar_related_tag\">(.*?)</p></div>"));
		TemplateNodeStack node2 = new TemplateNodeStack("imgUrl",Parse.TYPE_BLOCK,"amp;.*?[&\"]");
		node2.addChild("three1", new TemplateNodeStack("three1",Parse.TYPE_FIELD,"amp"));
		node2.addChild("three2", new TemplateNodeStack("three1",Parse.TYPE_FIELD,"amp;([\\d\\w]+)"));
		node1.addChild("imgUrl", node2);
		
		
		rootNode.addChild("abortWord", node1);
		

		
		String page = FileUtils.readFileToString(new File("conf/content.data"),"utf-8");
//		Parse parse = new RegexParse(page, t,"http:\\bing.com");
		Parse parse = new RegexParseStack(page, t,"http:\\bing.com");
		Map out = (Map)parse.parse();
//		
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		System.out.println(gson.toJson(out));
	}
}
