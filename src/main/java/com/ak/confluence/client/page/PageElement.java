package com.ak.confluence.client.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ak.confluence.client.page.table.Table;

/**
 * This class uses two symantics:
 * withxxx(...) or with(xxx) functions return the container with the new element appended
 * addXXX(...) or add(xxx) return the added item.
 * @author mamigo
 *
 */
public class PageElement {
	protected static final String STYLE = "style";
	protected String myTag;
	protected List<PageElement> children= new ArrayList<PageElement>();
	Map<String,String> attribs= new HashMap<String,String>();
	protected PageElement(String tag)
	{
		myTag=tag;
	}
	public List<PageElement> getChildren()
	{
		return children;
	}
	/**
	 * appends a new PageElement
	 * @param c
	 * @return itself
	 * @throws Exception
	 */
	public PageElement with(PageElement c) throws Exception
	{
		children.add(c);
		return this;
	}
	protected void addAttrib(String n, String v)
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
		sb.append("<").append(myTag);
		writeAttrib(sb);
		return sb.append("/>");
	}
	//methods for simplification 
	public Heading addHeading(int level) throws Exception
	{
		Heading h = new Heading(level);
		with(h);
		return h;
	}
	public PageElement withBR()throws Exception
	{
		return with( new Break());
	}
	public PageElement withHR()throws Exception
	{
		return with( new HRule());
	}
	public Text addText(String content)
	{
		Text t = new Text().with(content);
		getChildren().add(t);
		return t;
	}
	public PageElement withText(Text t)throws Exception
	{
		return with( t);
	}
	public Paragraph addPara()throws Exception
	{
		Paragraph p = new Paragraph();
		with(p);
		return p;
	}
	public Table addTable() throws Exception {
		Table t= new Table();
		with(t);
		return t;
		
	}
}
