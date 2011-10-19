package eu.ena.occi;

import java.io.Serializable;

public class OcciUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;	

	private String password;
	
	private String email;

	private String serverURL;
	private String port;
	
	
	public OcciUser(String username, String pass, String serverURL, String port){
		this.name=username;
		this.serverURL=serverURL;
		this.port=port;
		this.password=pass;
	}
	
	
	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String firstname) {
		this.name = firstname;
	}

	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
