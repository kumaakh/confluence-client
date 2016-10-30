package com.ak.confluence.client.page.table;

import com.ak.confluence.client.page.PageElement;

public class TBody extends TableElement {

	int rowCount=0;
	protected TBody() {
		super("tbody");
	}

	public TR addRow() {
		TR t= new TR();
		children.add(t);
		rowCount++;
		return t;
	}

	/**
	 * adds as first row no validations
	 * @return
	 * @throws Exception
	 */
	public TR addHeaderRow() throws Exception {
		TR t= new TR();
		children.add(0, t);
		return t;
	}
	public boolean hasHeaderRow() {
		for(PageElement p:children)
		{
			if(TR.class.isInstance(p))
			{
				if(((TR)p).hasColHeaders())
					return true;
			}
		}
		return false;
	}

	public int getRowCount() {
		return rowCount;
	}
}
