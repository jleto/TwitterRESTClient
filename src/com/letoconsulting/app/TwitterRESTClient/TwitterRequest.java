package com.letoconsulting.app.TwitterRESTClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class TwitterRequest {
	private HttpsURLConnection connection = null;
    private String consumerKey = null;
    private String consumerSecret = null;
    private String applicationName = null;
    private String endPointAuthUrl = null;
    private String endPointUrl = null;
    private String bearerToken = null;
	 
	//constructor
	public TwitterRequest()
	{  	    
	}
	
	public String Search(String query)
	{
		String response = null;
		String encoded_query = null;
		
		try {
			encoded_query = URLEncoder.encode(query, "ISO-8859-1");
			response = RESTHelper.submit(this.connection, this.getBearerToken(), this.getApplicationName(), endPointUrl + encoded_query);
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
			response = RESTHelper.submit(this.connection, this.getBearerToken(), this.getApplicationName(), endPointUrl + encoded_query);
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
	
	public void open(String consumerKey, String consumerSecret, String applicationName, String endPointAuthUrl) throws IOException
	{
	    this.setConsumerKey(consumerKey);
	    this.setConsumerSecret(consumerSecret);
	    this.setApplicationName(applicationName);
	    this.setEndPointAuthUrl(endPointAuthUrl);
	    this.setEndPointUrl("https://api.twitter.com/1.1/search/tweets.json?q=");
	    
        URL url = new URL(endPointAuthUrl);
        this.connection = (HttpsURLConnection) url.openConnection();
	    String token = RESTHelper.getBearerToken(this.connection, this.consumerKey, this.consumerSecret, this.applicationName, this.endPointAuthUrl);
	    this.setBearerToken(token);	  
	}

	public void close()
	{
		this.connection.disconnect();
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