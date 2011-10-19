package eu.ena.req;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.http.*;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;

import eu.ena.occi.OcciUser;

/**
 * End point of the Management of REST requests
 */
public class RestManager {

	protected static String SERVER_URL = "http://192.168.122.179";
	protected static String PORT = "3000";
	public static final int HTTP_OK = 200;

	/**
	 * Get Requests Returns the incoming answer
	 * 
	 * @param url
	 *            the url of the resource to be discovered
	 */
	
	static FacesContext context = FacesContext.getCurrentInstance();
	static HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	
	public static String getResource(String url) throws IOException {
		
		OcciUser user=(OcciUser)session.getAttribute("user");
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000);

		HttpGet httpGet = new HttpGet( user.getServerURL()+":"+user.getPort() + url);
		
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user.getName(), user.getPassword());		
		try {
		httpGet.addHeader(new BasicScheme().authenticate(creds, httpGet));		
		
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream instream = entity.getContent();
		return read(instream);
		} catch (AuthenticationException e) {
			System.out.println("login problem");
			e.printStackTrace();
		}
		 catch (Exception e) {
		System.out.println("Verify url and port problem");
		e.printStackTrace();
		}
		return "login error";
	}

	/**
	 * POST Requests
	 * 
	 * @param url
	 *            : specific url, compute/storage etc
	 * @param postHeaders
	 *            : a map of mainly Category and X-OCCI-Attribute , may add
	 *            -FilePath for storage
	 */
	public static String postResource(final String url,
			Map<String, String> postHeaders) throws IOException {

		OcciUser user=(OcciUser)session.getAttribute("user");
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000);

		HttpPost httpPost = new HttpPost(user.getServerURL()+":"+user.getPort()+ url);

		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user.getName(), user.getPassword());		

		try {		
		httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost));
		
		if (!postHeaders.get("Category").contains("?action")) // Verification
																// are added
																// only on CRUD
																// Requests not
																// actions
		{
			if (postHeaders.get("Category").startsWith("storage"))

			{
				File file = new File(postHeaders.get("PATH"));

				MultipartEntity mpEntity = new MultipartEntity();
				ContentBody cbFile = new FileBody(file, "bin");
				mpEntity.addPart("userfile", cbFile);

				httpPost.setEntity(mpEntity);
			}

			else if (postHeaders.get("Link") != null)
				httpPost.addHeader("Link", postHeaders.get("Link"));

		}

		httpPost.addHeader("Category", postHeaders.get("Category"));
		httpPost.addHeader("X-OCCI-Attribute",
				postHeaders.get("X-OCCI-Attribute"));

		
		
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream instream = entity.getContent();
		return read(instream);
		} catch (AuthenticationException e) {
			System.out.println("login problem");
			e.printStackTrace();
		}
		 catch (Exception e) {
		System.out.println("Verify url and port problem");
		e.printStackTrace();
		}
		return "login error";
	}

	public static boolean putResource(final String url, final String putHeaders)
			throws IOException {

		OcciUser user=(OcciUser)session.getAttribute("user");
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000);

		HttpPut httpPut = new HttpPut(user.getServerURL()+":"+user.getPort()+ url);		
		httpPut.addHeader("Accept", "text/plain");
		httpPut.addHeader("Content-Type", "text/plain");
		StringEntity entity = new StringEntity(putHeaders, "UTF-8");
		entity.setContentType("text/plain");
		httpPut.setEntity(entity);
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user.getName(), user.getPassword());		

		try {		
		httpPut.addHeader(new BasicScheme().authenticate(creds, httpPut));

		HttpResponse response = httpClient.execute(httpPut);
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode == HTTP_OK ? true : false;
	} catch (AuthenticationException e) {
		System.out.println("login problem");
		e.printStackTrace();
	}
	 catch (Exception e) {
	System.out.println("Verify url and port problem");
	e.printStackTrace();
	}
	return false;
	}

	/**
	 * Returns a boolean to tell if delete operation succeeded Delete requests
	 */
	public static boolean deleteResource(final String url) throws IOException {
			
		OcciUser user=(OcciUser)session.getAttribute("user");	
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000);

		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user.getName(), user.getPassword());		
		HttpDelete httpDelete = new HttpDelete(user.getServerURL()+":"+user.getPort()  + url);
		try {		
		httpDelete.addHeader(new BasicScheme().authenticate(creds, httpDelete));		
		
		httpDelete.addHeader("Accept", "text/plain");
		HttpResponse response = httpClient.execute(httpDelete);
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode == HTTP_OK ? true : false;
		
		} catch (AuthenticationException e) {
			System.out.println("login problem");
			e.printStackTrace();
		}
		 catch (Exception e) {
		System.out.println("Verify url and port problem");
		e.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns a String that contains the incoming data
	 * 
	 */

	private static String read(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}

}