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
	protected PageElement with(PageElement c){
		throw new IllegalArgumentException("can not add a child to "+myTag);
	}

	@Override
	protected String toString(StringBuffer sb) {
		return empty(sb).toString();
	}

}
