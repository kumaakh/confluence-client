package com.ak.confluence;

import static com.ak.confluence.client.page.TextStyle.*;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.junit.Test;
import org.w3c.dom.Document;

import com.ak.confluence.client.page.PageBuilder;
import com.ak.confluence.client.page.Paragraph.Alignment;
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
		c.addHeading(1).addText("Heading 1");
		c.withBR().addPara().withText(bold(undeline(new Text("a very important message"))));
		c.withHR().addHeading(2).withText((new Text("Heading 2")).with(Color.RED));
		c.withBR().addPara().withText(italic(new Text("some italicized text")));
		c.withHR().addPara().withText(monoSpace(new Text("some interesting code block with monospace")));
		c.withHR().addPara().withText(subScript(new Text("some tiny text")));
		c.withHR().addPara().withText(new Text("Some text in red color",Color.RED));
		c.withHR().addPara().with(Alignment.CENTER).addText("center aligned");
		c.withHR().addPara().with(Alignment.RIGHT).addText("right aligned");
		c.withHR().addPara().withLeftmargin(60).addText("indented");
		c.withHR().addPara().withLeftmargin(60).with(Alignment.CENTER). addText("indented and center aligned");
		
		return b;
	}

}
