package com.ak.confluence.client.page.table;

import com.ak.confluence.client.page.PageElement;
import com.ak.confluence.client.page.Text;

public class TD extends PageElement {
	public enum TDStyle {
		GRAY("grey"),
		RED("red"),
		GREEN("green"),
		BLUE("blue"),
		YELLOW("yellow");
		String color;
		TDStyle(String color)
		{
			this.color=color;
		}
	}
	TDStyle tdStyle=null;
	int colSpan=0;
	int rowSpan=0;
	boolean isNumCol=false;
	public TD() {
		super("td");
	}
	public TD with(TDStyle tdStyle)
	{
		this.tdStyle=tdStyle;
		return this;
	}
	public TD withNumbering()
	{
		this.isNumCol=true;
		return this;
	}
	public TD withColSpan(int colSpan)
	{
		this.colSpan=colSpan;
		return this;
	}
	public TD withRowSpan(int rowSpan)
	{
		this.rowSpan=rowSpan;
		return this;
	}
	private void addAttribs() {
		StringBuffer sb= new StringBuffer();
		if(tdStyle!=null)
		{
			sb.append("highlight-").append(tdStyle.color);
		}
		if(isNumCol)
		{
			if(sb.length()>0) sb.append(" ");
			sb.append("numberingColumn");
		}
		if(sb.length()>0)
		{
			addAttrib("class",sb.toString());
		}
		if(tdStyle!=null)
		{
			addAttrib("data-highlight-colour",tdStyle.color);
		}
		if(colSpan>0) addAttrib("colspan", ""+colSpan);
		if(rowSpan>0) addAttrib("rowSpan", ""+rowSpan);
	}
	@Override
	protected String toString(StringBuffer sb) {
		addAttribs();
		return super.toString(sb);
	}
	public Text addRowNum(int rowNum) {
		this.withNumbering();
		return this.addText(""+rowNum);
	}
}
