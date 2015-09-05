package com.zoro.qi.parse;

public class Template {
	private String pageType = "";
	private TemplateNode node = null;
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public TemplateNode getNode() {
		return node;
	}
	public void setNode(TemplateNode node) {
		this.node = node;
	}
}
