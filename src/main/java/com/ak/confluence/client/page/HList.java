package com.ak.confluence.client.page;

public class HList extends PageElement {

	static class Item extends PageElement{
		Item(){
			super("li");
		}
	}
	
	HList(boolean numbered) {
		super((numbered)?"ol":"ul");
	}
	public HList.Item addItem()
	{
		Item i=new Item();
		with(i);
		return i;
	}
	public HList withItem(AbsElement e)
	{
		addItem().with(e);
		return this;
	}

}
