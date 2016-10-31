package com.ak.confluence.client.page.wiki;


public class WikiPageLink extends WikiElement {

	WikiPageLink(String title) {
		super("ri:page");
		addAttrib("ri:content-title", title);
	}
	/*
	 * Use if the page is in another space
	 */
	public WikiPageLink inSpace(String spaceKey) {
		addAttrib("ri:space-key", spaceKey);
		return this;
	}
}
