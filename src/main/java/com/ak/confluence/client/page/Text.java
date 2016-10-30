package com.ak.confluence.client.page;

public class Text extends Terminal {
	String content;
	protected Text() {
		this("");
	}
	public Text(String content) {
		super("");
		this.content=content;
	}
	Text with(String content)
	{
		this.content=content;
		return this;
	}
	@Override
	public String toString(StringBuffer sb) {
		return sb.append(content).toString();
	}

}
