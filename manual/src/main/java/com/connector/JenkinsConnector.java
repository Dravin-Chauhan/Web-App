package com.connector;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class JenkinsConnector {

	public static void main(String[] args) {
		authenticateJenkins();
	}
	
	static void authenticateJenkins() {
		String userName = "admin";
		String jenkinsUrl = "http://localhost:8080";
		String token = "11530ef4380652a9a1c1e9111478a4ccd2"; 	//Right Token
//		String token = "11530ef4380652a9a1c1e9111478a4cce5"; 	//Wrong Token
		
		try {
			URI jenkinsUri = new URI(jenkinsUrl);
			HttpHost httpHost = new HttpHost(jenkinsUri.getHost(), jenkinsUri.getPort());
			CredentialsProvider credentials = new BasicCredentialsProvider();
			credentials.setCredentials(new AuthScope(jenkinsUri.getHost(), jenkinsUri.getPort()), new UsernamePasswordCredentials(userName, token));
			BasicScheme scheme = new BasicScheme();
			AuthCache authCatch = new BasicAuthCache();
			authCatch.put(httpHost, scheme);
			CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentials).build();
			HttpGet httpGet = new HttpGet(jenkinsUri);
			HttpClientContext httpContext = HttpClientContext.create();
			httpContext.setAuthCache(authCatch);
			
			System.out.println("Host Name : "+jenkinsUri.getHost()+", Port Number : "+jenkinsUri.getPort());
			System.out.println("Scheme Name : "+jenkinsUri.getScheme()+", Protocol Version : "+httpGet.getProtocolVersion());
			
			HttpResponse httpResponse = httpClient.execute(httpHost, httpGet, httpContext);
			HttpEntity httpEntity = httpResponse.getEntity();
			String httpStringResponse = EntityUtils.toString(httpEntity);
			
			System.out.println("Response Status : "+httpResponse.getStatusLine().getStatusCode());
			System.out.println("Response Reason : "+httpResponse.getStatusLine().getReasonPhrase());
			//System.out.println("Response Data : "+httpStringResponse);
		} 
		catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
	}
}
