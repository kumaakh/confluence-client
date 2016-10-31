package com.ak.confluence.client.page.wiki;

import com.ak.confluence.client.page.Text;

public class WikiLink extends WikiElement{

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
		t.withText((new Text(content)).withCData());
		getChildren().add(getChildren().size(),t);//add last
		return this;
	}
}
