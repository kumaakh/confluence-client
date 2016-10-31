package com.ak.confluence;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ak.confluence.client.ConflClient;
import com.ak.confluence.client.page.PageBuilder;


public class ConflClientTest {

	static ConflClient client;
	static String rootPage="8486914";
	static List<String> pages= new ArrayList<String>();
	@BeforeClass
	public static void setUp() throws Exception
	{
		if(client==null)
		{
			client= new ConflClient();
			client.setSpaceKey("TEST");
			//all properties are loaded from xml file
		}
		if(StringUtils.isEmpty(client.getAccessToken()))
		{
			client.fetchAuthToken();
		}
		assertNotNull(client.getAccessToken());
		assertTrue(client.getAccessToken().length()>10);
	}
	@AfterClass
	public static void tearDown()
	{
		for(String title:pages)
		{
			try {
				client.deletePageByTitle(title, true);
			} catch (Exception e) {
				e=null; //ignore
			}
		}
	}
	private void rememberToDelete(String title){
		pages.add(title);
	}
	private void openPage(URI uri) throws Exception
	{
		Desktop.getDesktop().browse(uri);
	}
	private String addPage(String parentID, String title, String content) throws Exception
	{
		return addPage(parentID, title, content, true);
	}
	private String addPage(String parentID, String title, String content, boolean showPage) throws Exception
	{
		rememberToDelete(title);
		String id = client.addPage(parentID, title, content);
		if(showPage) openPage(client.getPageURI(title));
		return id;
	}
	private String addPage(String parentID, PageBuilder b) throws Exception
	{
		return addPage(parentID, b.getTitle(), b.toString());
	}
	private String addPageWithAttachment(String parentID, PageBuilder b, String attFileName) throws Exception{
		String id=addPage(parentID, b.getTitle(), "",false);
		assertThat(id,not(isEmptyOrNullString()));
		String attid=client.makeAttachment(id, attFileName, "", true);
		assertThat(attid,not(isEmptyOrNullString()));
		int v=client.updatePage(id, 2, b.getTitle(), b.toString());
		assertThat(v, is(equalTo(2)));
		openPage(client.getPageURI(b.getTitle()));
		return id;
	}
	@Test
	public void testGetPageId() throws Exception {
		assertEquals(rootPage, client.getPageId("TEST Home"));
	}
	

	@Test
	public void testAddPage() throws Exception {
		String id=addPage(rootPage, "Hello world Page", "<p>Hello world</p>",false);
		assertThat(id,not(isEmptyOrNullString()));
		
		String page=client.getPageContentById(id);
		assertThat(page,not(isEmptyOrNullString()));
		
		int v=client.updatePage(id, 2, "Hello world 2", "<p>Hello world 23</p>");
		rememberToDelete("Hello world 2");
		assertThat(v,is(2));
		
		String attId=client.makeAttachment(id, "testData/anImg.jpeg", "added a new image", true);
		assertThat(attId,not(isEmptyOrNullString()));
		
	}
	
	@Test
	public void testMakeAttachment() throws Exception {
		String id=addPage(rootPage, "AttachmentTestPage", "<p>Hello world</p>",false);
		String attId=client.makeAttachment(id, "testData/anImg.jpeg", "added a new image", true);
		assertThat(attId,not(isEmptyOrNullString()));
		
	}
	
	@Test
	public void testMakeMultipleAttachments() throws Exception {
		
		String title = "Page with two attachments";
		String id=addPage(rootPage, title
				, "<p>refer other page <ac:link><ri:page ri:content-title='Hello world Page' /></ac:link></p><p><br /></p><p>link to attachment&nbsp;<ac:link><ri:attachment ri:filename='bImg.jpeg' /></ac:link></p><p><br /></p><p><ac:link><ri:attachment ri:filename='anImg.jpeg' /><ac:plain-text-link-body><![CDATA[link]]></ac:plain-text-link-body></ac:link> to att</p><p><br /></p><p>inlined image</p><p><ac:image><ri:attachment ri:filename='bImg.jpeg' /></ac:image></p><p><br /></p>"
				,false);
		assertThat(id,not(isEmptyOrNullString()));
		
		String page=client.getPageContentById(id);
		assertThat(page,not(isEmptyOrNullString()));
		
		HashMap<String, String> atts= new HashMap<String, String>();
		atts.put("testData/anImg.jpeg", "image #1");
		atts.put("testData/bImg.jpeg", "image #2");
		
		Collection<String> attIds=client.makeMultipleAttachments(id, atts.entrySet(), true);
		assertThat(attIds.size(), is(2));
		openPage(client.getPageURI(title));
	}
	@Test
	public void testComplexPage() throws Exception {
		String id=addPage(rootPage, "How to update old Ubuntu Packages 2",FileUtils.readFileToString(new File("testData/test3.txt"),"UTF-8"));
		assertThat(id,not(isEmptyOrNullString()));
	}
	
	@Test
	public void testPageDelete() throws Exception {
		{
			String id=addPage(rootPage, "DeleteTest","",false);
			assertThat(id,not(isEmptyOrNullString()));
			client.deletePage(id, false); //should be trashed
			client.deletePage(id, true);  //should be deleted
		}
		{
			String id=addPage(rootPage, "DeleteTest2","",false);
			assertThat(id,not(isEmptyOrNullString()));
			client.deletePage(id, true);  //should be trashed and deleted
		}
		
	}
	
	@Test
	public void testPageConstruction() throws Exception {
		String id=addPage(rootPage,PageBuilderTest.makeSimplePage());
		assertThat(id,not(isEmptyOrNullString()));
	}
	@Test
	public void testTablePageConstruction() throws Exception {
		String id=addPage(rootPage,PageBuilderTest.makeTablePage());
		assertThat(id,not(isEmptyOrNullString()));
	}
	@Test
	public void testLinkPageConstruction() throws Exception {
		
		String otherid=addPage(rootPage, "Other Page", "");
		client.makeAttachment(otherid, "testData/bImg.jpeg", "",true);
		String id=addPageWithAttachment(rootPage,PageBuilderTest.makeLinksPage(),"testData/anImg.jpeg");
		assertThat(id,not(isEmptyOrNullString()));
	}
	
}
