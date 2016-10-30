package com.ak.confluence.client.page;
/**
 * No children can be added to a terminal
 * 
 */
public abstract class Terminal extends PageElement {

	protected Terminal(String tag) {
		super(tag);
	}

	@Override
	public PageElement with(PageElement c) throws Exception {
		throw new Exception("can not add a child to "+myTag);
	}

	@Override
	public String toString(StringBuffer sb) {
		return empty(sb).toString();
	}

}
