package com.ak.confluence.client.page.table;

import com.ak.confluence.client.page.AbsElement;


public class Table extends AbsElement {

	TBody body;
	public Table(){
		super("table");
		body=new TBody();
		this.children.add(body);
	}
	public TR addRow() 
	{
		return body.addRow();
	}
	public TR addHeaderRow()throws Exception 
	{
		return body.addHeaderRow();
	}
	public int getRowCount()
	{
		return body.getRowCount();
	}
}
