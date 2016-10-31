package com.ak.confluence.client.page;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class PageBuilder {
	protected String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	protected PageElement tree= new PageElement("ac:layout");
	public PageBuilder(String title)
	{
		this.title=title;
	}
	private AbsElement add(AbsElement c) throws Exception
	{
		return tree.with(c);
	}
	/**
	 * Adds a single section which has a single cell
	 * @returns the single SectionCell added
	 * @throws Exception
	 */
	public SectionCell addSingleSection()throws Exception
	{
		return addSection(1, false,false).getCell(0);
	}
	/**
	 * Adds a section with given number of cells:1,2,3
	 * which are equally divided
	 * @param cellCount
	 * @return the section added
	 * @throws Exception
	 */
	public PageSection addSection(int cellCount)throws Exception
	{
		return addSection(1, true,false);
	}
	/**
	 * Adds section with given number of cells: 1,2,3 
	 * @param cellCount
	 * @param equal should the cells be equally distributed in width
	 * @param leftSideBar : should the left cell be a sidebar or right one. Applys if "equal" is set to false 
	 * @return the section added
	 * @throws Exception
	 */
	public PageSection addSection(int cellCount, boolean equal, boolean leftSideBar) throws Exception{
		PageSection s = new PageSection(cellCount, equal, leftSideBar);
		add(s);
		return s;
	}
	@Override
	public String toString() {
		return tree.toString();
	}
	
	public boolean validate()
	{
		return parse()!=null;
	}
	
	public Document parse()
	{
		//basic validation: xml document parsing should pass
		try{
			DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(toString())));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

}
