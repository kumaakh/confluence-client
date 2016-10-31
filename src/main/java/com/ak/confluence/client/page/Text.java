package com.ak.confluence.client.page;

import java.awt.Color;

public class Text extends Terminal {
	enum Style{
		NONE(""),
		BOLD("strong"),
		ITALIC("em"),
		UNDERLINE("u"),
		STRIKE("s"),
		SUPERSCRIPT("sup"),
		SUBSCRIPT("sub"),
		MONOSPACE("code"),
		PRE("pre");
		
		Style(String tag){ 
			this.tag=tag;
		}
		String tag;
	}
	
	public static Text create(String content, Color clr) {
		return (new Text(content, clr)).wrap(Style.NONE);
	}
	public static Text create(String content) {
		return (new Text(content)).wrap(Style.NONE);
	}
	protected Text wrap(Style style)
	{
		return new Delegate(this, style);
	}

	public Text bold(){throw new IllegalAccessError();}
	public Text italic(){throw new IllegalAccessError();}
	public Text undeline(){throw new IllegalAccessError();}
	public Text strike(){throw new IllegalAccessError();}
	public Text superScript(){throw new IllegalAccessError();}
	public Text subScript(){throw new IllegalAccessError();}
	public Text monoSpace(){throw new IllegalAccessError();}
	public Text preformatted(){throw new IllegalAccessError();}

	
	Color clr;
	String content;
	boolean inCData;
	protected Text() {
		this("");
	}
	private Text(String content) {
		super("");
		this.content=content;
	}
	private Text(String content, Color clr) {
		super("");
		this.content=content;
		with(clr);
	}
	public Text with(String content)
	{
		this.content=content;
		return this;
	}
	//style="color: rgb(255,102,0);"
	public Text with(Color color)
	{
		this.clr=color;
		StringBuffer sb = new StringBuffer("color:rgb(");
		sb.append(clr.getRed()).append(",");
		sb.append(clr.getGreen()).append(",");
		sb.append(clr.getBlue()).append(");");
		addAttrib(STYLE, sb.toString());
		return this;
	}
	public Text withCData()
	{
		inCData=true;
		return this;
	}
	protected void getContent(StringBuffer sb)
	{
		if(inCData)
		{
			sb.append("<![CDATA[")
			.append(content)
			.append("]]>");
		}
		else{
			sb.append(content);
		}
	}
	@Override
	protected String toString(StringBuffer sb) {
		if(clr==null){
			getContent(sb);
		}
		else {
			String tag="span";
			pre(sb,tag);
			getContent(sb);
			post(sb,tag);
		}
		return sb.toString();
	}
	
	static class Delegate extends Text {
		Text inner;
		Style style;
		Delegate(Text inner,Style style) {
			this.inner=inner;
			this.style=style;
		}
		@Override
		protected void getContent(StringBuffer sb) {
			this.inner.getContent(sb);
		}
		@Override
		protected String toString(StringBuffer sb) {
			if(Style.NONE==style){
				return this.inner.toString(sb);
			}
			else{
				pre(sb,style.tag);
				inner.toString(sb);
				post(sb,style.tag);
				return sb.toString();
			}
		}
		@Override
		public Text with(String content) {
			inner.with(content);
			return this;
		}
		@Override
		public Text with(Color color) {
			inner.with(color);
			return this;
		}
		@Override
		public Text withCData() {
			inner.withCData();
			return this;
		}
		@Override
		protected Text wrap(Style s){
			Delegate d = new Delegate(inner, s);
			inner=d;
			return this;
		}
		
		@Override
		public Text bold(){
			return wrap(Style.BOLD);
		}
		@Override
		public Text italic(){
			return wrap(Style.ITALIC);
		}
		@Override
		public Text undeline(){
			return wrap(Style.UNDERLINE);
		}
		@Override
		public Text strike(){
			return wrap(Style.STRIKE);
		}
		@Override
		public Text superScript(){
			return wrap(Style.SUPERSCRIPT);
		}
		@Override
		public Text subScript(){
			return wrap(Style.SUBSCRIPT);
		}
		@Override
		public Text monoSpace(){
			return wrap(Style.MONOSPACE);
		}
		@Override
		public Text preformatted(){
			return wrap(Style.PRE);
		}
	}

}
