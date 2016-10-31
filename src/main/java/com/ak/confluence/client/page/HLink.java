package com.ak.confluence.client.page;

import java.net.URI;

public class HLink extends PageElement {

	public HLink(URI uri) {
		super("a");
		addAttrib("href", uri.toASCIIString());
	}
}
