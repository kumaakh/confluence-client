package com.ak.confluence.client.page.wiki;

import com.ak.confluence.client.page.AbsElement;
import com.ak.confluence.client.page.Text;

public class WikiLink extends WikiElement{
	
	class WikiPlainText extends AbsElement {

		WikiPlainText() {
			super("ac:plain-text-link-body");
		}
	}


	public WikiLink() {
		super("ac:link");
	}
	public WikiPageLink toPage(String pageTitle)
	{
		WikiPageLink p = new WikiPageLink(pageTitle);
		getChildren().add(0, p);
		return p;
	}
	public WikiLink withText(String content)
	{
		WikiPlainText t = new WikiPlainText();
		t.getChildren().add((Text.create(content)).withCData());
		getChildren().add(getChildren().size(),t);//add last
		return this;
	}
}
