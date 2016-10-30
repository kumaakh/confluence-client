package com.ak.confluence.client.page;

public class SectionCell extends PageElement {
	int index;

	public int getIndex() {
		return index;
	}

	public SectionCell(int index) {
		super("ac:layout-cell");
		this.index = index;
	}
	
}
