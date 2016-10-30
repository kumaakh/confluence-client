package com.ak.confluence.client.page;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Paragraph extends PageElement {

	private static final String MARGIN_LEFT = "margin-left";
	private static final String TEXT_ALIGN = "text-align";
	Map<String,String> styles= new HashMap<String,String>();
	public enum Alignment {
		
		LEFT("left"),
		CENTER("center"),
		RIGHT("right");
		String type;
		Alignment(String type){
			this.type=type;
		}
	}
	protected Paragraph() {
		super("p");
	}
	public Paragraph with(Alignment a)
	{
		styles.put(TEXT_ALIGN, a.type);
		return this;
	}
	public Paragraph withLeftmargin(int px)
	{
		styles.put(MARGIN_LEFT, ""+px+".0px");
		return this;
	}
	@Override
	public String toString(StringBuffer sb) {
		addStyleAttrib();
		return super.toString(sb);
	}
	private void addStyleAttrib() {
		StringBuffer sb=new StringBuffer();
		for(Entry<String, String> kv:styles.entrySet())
		{
			sb.append(kv.getKey()).append(": ")
			.append(kv.getValue())
			.append(";");
		}
		addAttrib(STYLE, sb.toString());
		
	}
	
}
