package com.ak.confluence.client.page;

public class PageSection extends PageElement {
	int cellCount;
	public PageSection(int cellCount, boolean equal, boolean leftSidebar) throws Exception {
		super("ac:layout-section");
		this.cellCount=cellCount;
		String acType;
		if(cellCount==3)
		{
			acType=(equal)?"three_equal":"three_with_sidebars";
		}
		else if(cellCount==2)
		{
			acType=(equal)?"two_equal":(leftSidebar)?"two_left_sidebar":"two_right_sidebar";
		}
		else if(cellCount==1)
		{
			acType="single";
		}
		else
		{
			throw new Exception("Unsupported cell count");
		}
		this.addAttrib("ac:type", acType);
		for(int i=0;i<cellCount;i++)
		{
			with( new SectionCell(i));
		}
	}
	public SectionCell getCell(int index) throws Exception
	{
		for(PageElement p:children)
		{
			if (SectionCell.class.isInstance(p))
			{
				SectionCell c= (SectionCell)p;
				if(c.getIndex()==index)
					return c;
			}
		}
		return null;
	}

}
