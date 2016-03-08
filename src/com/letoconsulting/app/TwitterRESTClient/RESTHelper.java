package com.letoconsulting.app.TwitterRESTClient;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.http.HTTPException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class RESTHelper {
	
	public static String getBearerToken(String consumerKey, String consumerSecret, String applicationName, String endPointAuthUrl) throws IOException
	{
		String tokenType = null;
		String token = null;
		JSONObject obj = null;
        HttpsURLConnection connection = null;
        
        //encode the credentials according to OAuth2 spec
		String encodedCredentials = encodeKeys(consumerKey, consumerSecret);
		
		try {
	        URL url = new URL(endPointAuthUrl);
	        connection = (HttpsURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Host", "api.twitter.com");
	        connection.setRequestProperty("User-Agent", applicationName);
	        connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	        connection.setRequestProperty("Content-Length", "29");
	        connection.setUseCaches(false);
	        
	        //Write request to stream
	        writeRequest(connection, "grant_type=client_credentials");
	        
	        // Read response into string
	        String response = readResponse(connection);
	        
            try {
                obj = new JSONObject(response);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            if (obj != null) {
            	
                try {
                    tokenType = obj.getString("token_type");
                    token = obj.getString("access_token");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                return ((tokenType.equals("bearer")) && (token != null)) ? token : "";
            }   	       
		}
		catch (HTTPException e)
		{
			e.printStackTrace();
		}
        return null;
	}
	
	public static String submit(String bearerToken, String applicationName, String endPointUrl) throws IOException
	{
		String response = null;
        HttpsURLConnection connection = null;
        
		URL url = new URL(endPointUrl);
        try {
			connection = (HttpsURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
			connection.setRequestMethod("GET");
	        connection.setRequestProperty("Host", "api.twitter.com");
	        connection.setRequestProperty("User-Agent", applicationName);
	        connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
	        connection.setUseCaches(false);

	        // Read the response
	        response = readResponse(connection);
	        return response;	        
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
        catch (IOException e)
        {
        	e.printStackTrace();
        }		
		return null;
	}
	
    // Encodes the consumer key and secret to create the basic authorization key
    private static String encodeKeys(String consumerKey, String consumerSecret) {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
            return new String(encodedBytes);
        } catch (UnsupportedEncodingException e) {
            return new String();
        }
    }
    
    // Writes a request to a connection
    private static boolean writeRequest(HttpsURLConnection connection, String textBody) {
        try {
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            wr.write(textBody);
            wr.flush();
            wr.close();
            return true;
        }
        catch (IOException e) {
        	e.printStackTrace();
        	return false;
        }
    }

    // Reads a response for a given connection and returns it as a string.
    public static String readResponse(HttpsURLConnection connection) {
        try {
            StringBuilder str = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null) {
                str.append(line + System.getProperty("line.separator"));
            }
            return str.toString();
        }
        catch (IOException e) {
        	e.printStackTrace();
        	return null;
        }
    }
}
