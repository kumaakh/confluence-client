package com.ak.confluence.client.page;

public class TextStyle extends Text {
	String style;
	Text t;
	private TextStyle(String style, Text t) {
		this.t=t;
		this.style=style;
	}

	public static Text bold(Text t){
		return new TextStyle("strong",t);
	}
	public static Text italic(Text t){
		return new TextStyle("em",t);
	}
	public static Text undeline(Text t){
		return new TextStyle("u",t);
	}
	public static Text strike(Text t){
		return new TextStyle("s",t);
	}
	public static Text superScript(Text t){
		return new TextStyle("sup",t);
	}
	public static Text subScript(Text t){
		return new TextStyle("sub",t);
	}
	public static Text monoSpace(Text t){
		return new TextStyle("code",t);
	}

	@Override
	public String toString(StringBuffer sb) {
		pre(sb,style);
		t.toString(sb);
		post(sb,style);
		return sb.toString();
	}
	
}
