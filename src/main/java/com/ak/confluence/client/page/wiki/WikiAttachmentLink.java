package com.ak.confluence.client.page.wiki;


public class WikiAttachmentLink extends WikiElement {

	public WikiAttachmentLink(String fileName) {
		super("ri:attachment");
		addAttrib("ri:filename", fileName);
	}
	/*
	 * Use if the attachment is from a different page
	 */
	public WikiPageLink ofPage(String title)
	{
		WikiPageLink l = new WikiPageLink(title);
		with(l);
		return l;
	}

}
