package com.letoconsulting.app.TwitterRESTClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TwitterRequest {
    private String consumerKey;
    private String consumerSecret;
    private String applicationName;
    private String endPointAuthUrl;
    private String endPointUrl;
    private String bearerToken;
	
	//constructor
	public TwitterRequest(String consumerKey, String consumerSecret, String applicationName, String endPointAuthUrl) throws IOException
	{
	    this.setConsumerKey(consumerKey);
	    this.setConsumerSecret(consumerSecret);
	    this.setApplicationName(applicationName);
	    this.setEndPointAuthUrl(endPointAuthUrl);
	    this.setEndPointUrl("https://api.twitter.com/1.1/search/tweets.json?q=");
	    
	    String token = RESTHelper.getBearerToken(this.consumerKey, this.consumerSecret, this.applicationName, this.endPointAuthUrl);
	    this.setBearerToken(token);	    	    
	}
	
	public String Search(String query)
	{
		String response = null;
		String encoded_query = null;
		
		try {
			encoded_query = URLEncoder.encode(query, "ISO-8859-1");
			response = RESTHelper.submit(this.getBearerToken(), this.getApplicationName(), endPointUrl + encoded_query);
			return response;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String Search(String endPointUrl, String query)
	{
		String response = null;
		String encoded_query = null;
		
		try {
			encoded_query = URLEncoder.encode(query, "ISO-8859-1");
			response = RESTHelper.submit(this.getBearerToken(), this.getApplicationName(), endPointUrl + encoded_query);
			return response;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	/* Getters and Setters */
	
	private String getBearerToken() {
		return bearerToken;
	}	

	private void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}

	@SuppressWarnings("unused")
	private String getEndPointUrl() {
		return endPointUrl;
	}	

	private void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}
	
	@SuppressWarnings("unused")
	private String getEndPointAuthUrl() {
		return endPointAuthUrl;
	}

	private void setEndPointAuthUrl(String endPointAuthUrl) {
		this.endPointAuthUrl = endPointAuthUrl;
	}

	private String getApplicationName() {
		return applicationName;
	}

	private void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@SuppressWarnings("unused")
	private String getConsumerSecret() {
		return consumerSecret;
	}

	private void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	@SuppressWarnings("unused")
	private String getConsumerKey() {
		return consumerKey;
	}

	private void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
		
}