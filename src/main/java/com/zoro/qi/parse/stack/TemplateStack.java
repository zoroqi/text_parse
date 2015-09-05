package com.zoro.qi.parse.stack;

import com.zoro.qi.parse.Template;

public class TemplateStack {
	private String pageType = "";
	private TemplateNodeStack node = null;
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public TemplateNodeStack getNode() {
		return node;
	}
	public void setNode(TemplateNodeStack node) {
		this.node = node;
	}
}
