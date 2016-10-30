package com.ak.confluence;

import static com.ak.confluence.client.page.TextStyle.bold;
import static com.ak.confluence.client.page.TextStyle.italic;
import static com.ak.confluence.client.page.TextStyle.monoSpace;
import static com.ak.confluence.client.page.TextStyle.subScript;
import static com.ak.confluence.client.page.TextStyle.undeline;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.junit.Test;
import org.w3c.dom.Document;

import com.ak.confluence.client.page.PageBuilder;
import com.ak.confluence.client.page.Paragraph.Alignment;
import com.ak.confluence.client.page.SectionCell;
import com.ak.confluence.client.page.Text;
import com.ak.confluence.client.page.table.TR;
import com.ak.confluence.client.page.table.Table;
import com.ak.confluence.client.page.table.TD.TDStyle;

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
	
	@Test
	public void tesTablePage() throws Exception {
		PageBuilder b = makeTablePage();
		Document d=b.parse();
		assertNotNull(d);
	}

	public static PageBuilder makeTablePage() throws Exception {
		PageBuilder b = new PageBuilder("Table Test Page");
		SectionCell c= b.addSingleSection();
		{
			c.addHeading(1).addText("Simple table");
			Table t=c.addTable();
			{
				TR headRow=t.addHeaderRow();
				headRow.addHeader().withNumbering().withText(new Text("Sl."));
				headRow.addHeader().with(TDStyle.BLUE).addPara().addText("Name");
				headRow.addHeader().with(TDStyle.GREEN).addText("City");
			}
			{
				TR row=t.addRow();
				row.addCell().addRowNum(t.getRowCount());
				row.addCell().with(TDStyle.BLUE).addPara().addText("AK");
				row.addCell().with(TDStyle.GREEN).addText("BLR");
			}
			{
				TR row=t.addRow();
				row.addCell().addRowNum(t.getRowCount());
				row.addCell().with(TDStyle.BLUE).addPara().addText("BK");
				row.addCell().addText("NYC");
			}
		}
		{
			c.withHR().addHeading(1).addText("Table with merged cells");
			Table t=c.addTable();
			{
				TR headRow=t.addHeaderRow();
				headRow.addHeader().withNumbering().withText(new Text("Sl."));
				headRow.addHeader().with(TDStyle.BLUE).addPara().addText("Name");
				headRow.addHeader().with(TDStyle.GREEN).addText("City");
			}
			{
				TR row=t.addRow();
				row.addCell().addRowNum(t.getRowCount());
				row.addMergedCell(0,2).with(TDStyle.RED).addText("merged");
			}
			{
				TR row=t.addRow();
				row.addCell().addRowNum(t.getRowCount());
				row.addMergedCell(2,0).addText("hmerged");
				row.addCell().addText("CK");
			}
			{
				TR row=t.addRow();
				row.addCell().addRowNum(t.getRowCount());
				row.addCell().addText("DK");
			}
		}
		
		return b;
	}

}
