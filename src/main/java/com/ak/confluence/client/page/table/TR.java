package com.ak.confluence.client.page.table;

import com.ak.confluence.client.page.PageElement;

public class TR extends TableElement {

	protected TR() {
		super("tr");
		// TODO Auto-generated constructor stub
	}
	/**
	 * Can be both row header and col header
	 * @return
	 */
	public TH addHeader() 
	{
		TH h= new TH();
		getChildren().add(h);
		return h;
	}
	public TD addCell() 
	{
		TD c= new TD();
		getChildren().add(c);
		return c;
	}
	public TD addMergedCell(int rows, int cols) 
	{
		TD c= new TD();

		if(rows>0) c.withRowSpan(rows);
		if(cols>0) c.withColSpan(cols);

		getChildren().add(c);
		return c;
	}

	public boolean hasColHeaders()
	{
		for(PageElement t:children)
		{
			if(TH.class.isInstance(t))
				return true;
		}
		return false;
	}

}
