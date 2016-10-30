package com.ak.confluence.client.page;

import java.awt.Color;

public class Text extends Terminal {
	Color clr;
	String content;
	protected Text() {
		this("");
	}
	public Text(String content) {
		super("");
		this.content=content;
	}
	public Text(String content, Color clr) {
		super("");
		this.content=content;
		with(clr);
	}
	public Text with(String content)
	{
		this.content=content;
		withoutColor();
		return this;
	}
	public Text withoutColor()
	{
		this.clr=null;
		this.attribs.remove(STYLE);
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
	@Override
	public String toString(StringBuffer sb) {
		if(clr==null){
			sb.append(content);
		}
		else {
			String tag="span";
			pre(sb,tag);
			sb.append(content);
			post(sb,tag);
		}
		return sb.toString();
	}

}
