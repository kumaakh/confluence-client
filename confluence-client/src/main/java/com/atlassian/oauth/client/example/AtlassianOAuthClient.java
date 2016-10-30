package com.atlassian.oauth.client.example;

import static net.oauth.OAuth.OAUTH_VERIFIER;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;
import net.oauth.signature.RSA_SHA1;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.common.collect.ImmutableList;

/**
 * @since v1.0
 */
public class AtlassianOAuthClient
{
    protected static final String SERVLET_BASE_URL = "/plugins/servlet";

    private final String consumerKey;
    private final String privateKey;
    private final String baseUrl;
    private final String callback;
    private OAuthAccessor accessor;
    

    public AtlassianOAuthClient(String consumerKey, String privateKey, String baseUrl, String callback,String accessToken)
    {
        this.consumerKey = consumerKey;
        this.privateKey = privateKey;
        this.baseUrl = baseUrl;
        this.callback = callback;
        setAccessToken(accessToken);

    }
	public void setAccessToken(String accessToken) {
		if(accessToken!=null){
        	getAccessor();
        	accessor.accessToken=accessToken;
        }
	}
    public AtlassianOAuthClient(String consumerKey, String privateKey, String baseUrl, String callback)
    {
        this(consumerKey,privateKey,baseUrl,callback,null);
    }

    public TokenSecretVerifierHolder getRequestToken()
    {
        try
        {
            OAuthAccessor accessor = getAccessor();
            OAuthClient oAuthClient = new OAuthClient(new HttpClient4());
            List<OAuth.Parameter> callBack;
            if (callback == null || "".equals(callback))
            {
                callBack = Collections.<OAuth.Parameter>emptyList();
            }
            else
            {
                callBack = ImmutableList.of(new OAuth.Parameter(OAuth.OAUTH_CALLBACK, callback));
            }

            OAuthMessage message = oAuthClient.getRequestTokenResponse(accessor, "POST", callBack);
            TokenSecretVerifierHolder tokenSecretVerifier = new TokenSecretVerifierHolder();
            tokenSecretVerifier.token = accessor.requestToken;
            tokenSecretVerifier.secret = accessor.tokenSecret;
            tokenSecretVerifier.verifier = message.getParameter(OAUTH_VERIFIER);
            return tokenSecretVerifier;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to obtain request token", e);
        }
    }

    public String swapRequestTokenForAccessToken(TokenSecretVerifierHolder holder)
    {
    	return swapRequestTokenForAccessToken(holder.token,holder.secret, holder.verifier);
    }
    public String swapRequestTokenForAccessToken(String requestToken, String tokenSecret, String oauthVerifier)
    {
        try
        {
            OAuthAccessor accessor = getAccessor();
            OAuthClient client = new OAuthClient(new HttpClient4());
            accessor.requestToken = requestToken;
            accessor.tokenSecret = tokenSecret;
            OAuthMessage message = client.getAccessToken(accessor, "POST",
                    ImmutableList.of(new OAuth.Parameter(OAuth.OAUTH_VERIFIER, oauthVerifier)));
            return message.getToken();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to swap request token with access token", e);
        }
    }

    public String makeAuthenticatedRequest(String url)
    {
        try
		{
		    OAuthClient client = new OAuthClient(new HttpClient4());
		    OAuthMessage response = client.invoke(accessor,null,url, Collections.<Map.Entry<?, ?>>emptySet());
		    return response.readBodyAsString();
		}
		catch (Exception e)
		{
		    throw new RuntimeException("Failed to make an authenticated request.", e);
		}
    }
    public String makeAuthenticatedPost(String url, String body)
    {
    	StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
    	return makeAuthenticatedPost("POST",url,entity,false);
    }
    
    public String makeAuthenticatedPut(String url, String body)
    {
    	StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
    	return makeAuthenticatedPost("PUT",url,entity,false);
    }
    /*
     * does not return any JSON !
     */
    public StatusLine makeAuthenticatedDelete(String url)
    {
    	try
		{
		    OAuthMessage request = accessor.newRequestMessage(HttpDelete.METHOD_NAME, url, Collections.<Map.Entry<?, ?>>emptySet());
		    String authHeader=request.getAuthorizationHeader(baseUrl);
		    HttpRequestBase postReq = makeHttpRequest(HttpDelete.METHOD_NAME, url,null);
		    postReq.addHeader("Authorization", authHeader);
		    CloseableHttpClient httpClient= HttpClients.createDefault();
		    return httpClient.execute(postReq).getStatusLine();
		}
		catch (Exception e)
		{
		    throw new RuntimeException("Failed to make an authenticated request.", e);
		}
    }
    
    public String makeAuthenticatedPostWithMultiPart(String url, HttpEntity multipartEntity)
    {
    	return makeAuthenticatedPost("POST",url,multipartEntity,true);
    }
    
    
    private String makeAuthenticatedPost(String method, String url, HttpEntity entity, boolean disableXSRFCheck)
    {
        try
        {
            OAuthMessage request = accessor.newRequestMessage(method, url, Collections.<Map.Entry<?, ?>>emptySet());
            String authHeader=request.getAuthorizationHeader(baseUrl);
            HttpRequestBase postReq = makeHttpRequest(method, url,entity);
            postReq.addHeader("Authorization", authHeader);
            if(disableXSRFCheck) postReq.addHeader("X-Atlassian-Token", "nocheck");
            CloseableHttpClient httpClient= HttpClients.createDefault();
            HttpResponse resp=httpClient.execute(postReq);
            if(resp.getEntity()!=null)
            	return IOUtils.toString(resp.getEntity().getContent(),"UTF-8");
            else
            	return resp.getStatusLine().toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to make an authenticated request.", e);
        }
    }
    /**
     * 
     * @param method
     * @param url
     * @param entity Null allowed for delete only
     * @return
     * @throws Exception
     */
	protected HttpRequestBase makeHttpRequest(String method, String url, HttpEntity entity) throws Exception {
		HttpEntityEnclosingRequestBase postReq=null;
		if(HttpPost.METHOD_NAME.equalsIgnoreCase(method)){
			postReq= new HttpPost(url);
			postReq.setEntity(entity);
			return postReq;
		}
		else if(HttpPut.METHOD_NAME.equalsIgnoreCase(method)){
			postReq= new HttpPut(url);
			postReq.setEntity(entity);
			return postReq;
		}
		else if(HttpDelete.METHOD_NAME.equalsIgnoreCase(method)){
			return new HttpDelete(url);
		}
		else {
			throw new Exception("unsupported method "+method);
		}
	}
    

    private final OAuthAccessor getAccessor()
    {
        if (accessor == null)
        {
            OAuthServiceProvider serviceProvider = new OAuthServiceProvider(getRequestTokenUrl(), getAuthorizeUrl(), getAccessTokenUrl());
            OAuthConsumer consumer = new OAuthConsumer(callback, consumerKey, null, serviceProvider);
            consumer.setProperty(RSA_SHA1.PRIVATE_KEY, privateKey);
            consumer.setProperty(OAuth.OAUTH_SIGNATURE_METHOD, OAuth.RSA_SHA1);
            accessor = new OAuthAccessor(consumer);
        }
        return accessor;
    }

    private String getAccessTokenUrl()
    {
        return baseUrl + SERVLET_BASE_URL + "/oauth/access-token";
    }

    private String getRequestTokenUrl()
    {
        return  baseUrl + SERVLET_BASE_URL + "/oauth/request-token";
    }

    public String getAuthorizeUrlForToken(String token)
    {
        return getAuthorizeUrl() + "?oauth_token=" + token;
    }
    public String getAuthorizeUrlForToken(String token, String callback) throws UnsupportedEncodingException
    {
        return getAuthorizeUrl() + "?oauth_token=" + token+"&oath_callback="+URLEncoder.encode(callback,"UTF-8");
    }
    private String getAuthorizeUrl() {return baseUrl + SERVLET_BASE_URL + "/oauth/authorize";}
}
