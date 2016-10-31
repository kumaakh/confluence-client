package com.ak.confluence.client.page;

import java.net.URI;

import com.ak.confluence.client.page.table.Table;
import com.ak.confluence.client.page.wiki.TaskList;
import com.ak.confluence.client.page.wiki.WikiImageLink;
import com.ak.confluence.client.page.wiki.WikiLink;

/**
 * This class uses two symantics:
 * withxxx(...) or with(xxx) functions return the container with the new element appended
 * addXXX(...) or add(xxx) return the added item.
 * @author akhil.kumar@gmail.com
 *
 */
public class PageElement extends AbsElement{
	protected PageElement(String tag)
	{
		super(tag);
	}
	//add methods should return the added 
	public Heading addHeading(int level) throws Exception
	{
		Heading h = new Heading(level);
		getChildren().add(h);
		return h;
	}
	public Paragraph addPara()
	{
		Paragraph p = new Paragraph();
		getChildren().add(p);
		return p;
	}
	public Table addTable(){
		Table t= new Table();
		getChildren().add(t);
		return t;
		
	}
	public HLink addHLink(URI uri) 
	{
		HLink h = new HLink(uri);
		getChildren().add(h);
		return h;
	}
	public WikiLink addWikiLink() 
	{
		WikiLink h = new WikiLink();
		getChildren().add(h);
		return h;
	}
	public WikiImageLink addImageLink()
	{
		WikiImageLink h = new WikiImageLink();
		getChildren().add(h);
		return h;
	}
	public HList addBulletList()
	{
		HList h = new HList(false);
		with(h);
		return h;
	}
	public HList addNumberedList()
	{
		HList h = new HList(true);
		with(h);
		return h;
	}
	public TaskList addTaskList()
	{
		TaskList h = new TaskList();
		with(h);
		return h;
	}
	public Quote addQuote()
	{
		Quote h= new Quote();
		with(h);
		return h;
	}
	public Text addText(String content) {
		Text t = new Text().with(content);
		getChildren().add(t);
		return t;
	}

	public PageElement withText(Text t) {
		getChildren().add(t);
		return this;
	}
	public PageElement withBR()
	{
		getChildren().add( new Break());
		return this;
	}

	public PageElement withHR()
	{
		getChildren().add( new HRule());
		return this;
	}
}
