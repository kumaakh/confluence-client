package com.ak.confluence.client.page;

public class Heading extends PageElement {

	public Heading(int level) throws Exception{
		super("h"+level);
		if(level<1 || level>6) throw new Exception("Unsupported heading level "+level);
	}

}
