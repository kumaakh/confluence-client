package com.ak.confluence.client.page.wiki;

import java.net.URI;

import com.ak.confluence.client.page.PageElement;

public class RemoteLink extends PageElement {

	public RemoteLink(URI uri) {
		super("ri:url");
		addAttrib("ri:value", uri.toASCIIString());
	}

}
