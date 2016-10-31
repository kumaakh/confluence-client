package com.ak.confluence.client.page.wiki;

import com.ak.confluence.client.page.PageElement;

public abstract class WikiElement extends PageElement {

	public WikiElement(String tag) {
		super(tag);
	}

	public WikiAttachmentLink toAttachment(String filename) {
		WikiAttachmentLink p = new WikiAttachmentLink(filename);
		getChildren().add(0, p);
		return p;
	}
}