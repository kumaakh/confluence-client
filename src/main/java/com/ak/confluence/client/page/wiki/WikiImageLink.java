package com.ak.confluence.client.page.wiki;

import java.net.URI;

import com.ak.confluence.client.page.AbsElement;


public class WikiImageLink extends WikiElement {
	
	class RemoteLink extends AbsElement {

		public RemoteLink(URI uri) {
			super("ri:url");
			addAttrib("ri:value", uri.toASCIIString());
		}
	}

	boolean remoteLink=false;
	boolean effects=false;
	public enum ImageEffects{
		None(""),
		Taped("effects=border-simple,blur-border,tape"),
		InstantCamera("effects=border-polaroid,blur-border"),
		CurlShadow("effects=border-simple,shadow-kn"),
		Snapshot("effects=border-simple,blur-border"),
		DropShadow("effects=drop-shadow");
		String qPars;
		ImageEffects(String q){
			this.qPars=q;
		}
	}

	public WikiImageLink() {
		super("ac:image");
	}
	public WikiImageLink withHeight(int px)
	{
		addAttrib("ac:height", ""+px);
		return this;
	}
	public WikiImageLink withBorder()
	{
		addAttrib("ac:border", "true");
		return this;
	}
	public WikiImageLink withEffects(ImageEffects e)
	{
		if(remoteLink)
			throw new IllegalArgumentException("Effects can not be added to external image");
		
		if(ImageEffects.None!=e)
		{
			addAttrib("ac:queryparams", e.qPars);
			effects=true;
		}
		return this;
	}
	public WikiImageLink withTitle(String title)
	{
		addAttrib("ac:title", title);
		return this;
	}
	public WikiImageLink withAltText(String altText)
	{
		addAttrib("ac:alt", altText);
		return this;
	}
	public WikiImageLink withRemoteLink(URI imageURI)
	{
		if(effects)
			throw new IllegalArgumentException("Effects can not be added to external image");
		
		RemoteLink r = new RemoteLink(imageURI);
		with(r);
		remoteLink=true;
		return this;
	}

}
