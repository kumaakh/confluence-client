package com.ak.confluence.client.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AbsElement {

	protected static final String STYLE = "style";
	protected String myTag;
	protected List<AbsElement> children = new ArrayList<AbsElement>();
	protected Map<String,String> attribs = new HashMap<String,String>();

	/**
	 * Class to encapsulate the page writing logic
	 */
	protected AbsElement(String tag)
	{
		myTag=tag;
	}

	public List<AbsElement> getChildren() {
		return children;
	}

	protected void addAttrib(String n, String v) {
		attribs.put(n,v);
	}

	void writeAttrib(StringBuffer sb) {
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

	protected String toString(StringBuffer sb) {
		if(children.size()==0)
		{
			empty(sb);
		}
		else
		{
			pre(sb);
			for(AbsElement c:children)
			{
				c.toString(sb);
			}
			post(sb);
		}
		return sb.toString();
	}

	protected StringBuffer pre(StringBuffer sb) {
		return pre(sb,myTag);
	}

	protected StringBuffer pre(StringBuffer sb, String tag) {
		sb.append("<").append(tag);
		writeAttrib(sb);		
		sb.append(">");
		return sb;
	}

	protected StringBuffer post(StringBuffer sb) {
		return post(sb,myTag);
	}

	protected StringBuffer post(StringBuffer sb, String tag) {
		return sb.append("</")
				.append(tag)
				.append(">");
	}

	protected StringBuffer empty(StringBuffer sb) {
		sb.append("<").append(myTag);
		writeAttrib(sb);
		return sb.append("/>");
	}

	protected AbsElement with(AbsElement c) {
		children.add(c);
		return this;
	}

}
