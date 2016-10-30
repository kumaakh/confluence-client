package com.ak.confluence.client.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PageElement {
	protected String myTag;
	List<PageElement> children= new ArrayList<PageElement>();
	Map<String,String> attribs= new HashMap<String,String>();
	protected PageElement(String tag)
	{
		myTag=tag;
	}
	
	public PageElement add(PageElement c) throws Exception
	{
		children.add(c);
		return this;
	}
	void addAttrib(String n, String v)
	{
		attribs.put(n,v);
	}
	void writeAttrib(StringBuffer sb)
	{
		if(attribs.size()==0) return;
		for(Entry<String, String> kv:attribs.entrySet())
		{
			sb.append(" ")
			.append(kv.getKey()).append("=")
			.append("'")
			.append(kv.getValue())
			.append("'")
			.append(" ");
		}
	}

	@Override
	public String toString() {
		return toString(new StringBuffer());
	}
	public String toString(StringBuffer sb) {
		if(children.size()==0)
		{
			empty(sb);
		}
		else
		{
			pre(sb);
			for(PageElement c:children)
			{
				c.toString(sb);
			}
			post(sb);
		}
		return sb.toString();
	}
	protected StringBuffer pre(StringBuffer sb){
		return pre(sb,myTag);
	}
	protected StringBuffer pre(StringBuffer sb, String tag){
		sb.append("<").append(tag);
		writeAttrib(sb);		
		sb.append(">");
		return sb;
	}
	protected StringBuffer post(StringBuffer sb){
		return post(sb,myTag);
	}
	protected StringBuffer post(StringBuffer sb, String tag){
		return sb.append("</")
				.append(tag)
				.append(">");
	}
	protected StringBuffer empty(StringBuffer sb){
		return sb.append("<")
				.append(myTag)
				.append("/>");
	}
	//methods for simplification 
	public PageElement addHeading(int level, String text) throws Exception
	{
		Heading h = new Heading(level);
		h.add(new Text().with(text));
		return add( h);
	}
	public PageElement addBR()throws Exception
	{
		return add( new Break());
	}
	public PageElement addHR()throws Exception
	{
		return add( new HRule());
	}
	public PageElement addText(String content)throws Exception
	{
		return add( new Text().with(content));
	}
	public PageElement addPara(Text t)throws Exception
	{
		Paragraph p = new Paragraph();
		return add(p.add(t));
	}
	public PageElement addText(Text t)throws Exception
	{
		add(t);
		return this;
	}
}
