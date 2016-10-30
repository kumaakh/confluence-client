package com.ak.confluence;

import static com.ak.confluence.client.page.TextStyle.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.w3c.dom.Document;

import com.ak.confluence.client.page.PageBuilder;
import com.ak.confluence.client.page.SectionCell;
import com.ak.confluence.client.page.Text;

public class PageBuilderTest {

	@Test
	public void testSimplePage() throws Exception {
		PageBuilder b = makeSimplePage();
		Document d=b.parse();
		assertNotNull(d);
	}

	public static PageBuilder makeSimplePage() throws Exception {
		PageBuilder b = new PageBuilder("Content Test Page");
		SectionCell c= b.addSingleSection();
		c.addHeading(1, "Heading 1");
		c.addBR();
		c.addPara(bold(undeline(new Text("a very important message"))));
		c.addHR(); 
		c.addHeading(2, "Heading 2");
		c.addBR();
		c.addPara(italic(new Text("some italicized text")));
		c.addHR();
		c.addPara(monoSpace(new Text("some interesting code block with monospace")));
		c.addHR();
		c.addPara(subScript(new Text("some tiny text")));
		return b;
	}

}
