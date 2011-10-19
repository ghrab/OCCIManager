package eu.ena.view;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import eu.ena.occi.OcciUser;

@ManagedBean
@SessionScoped
public class UserManagedBean {

	private String username;
	private String password;

	private String serverURL;
	private String port;

	HttpSession session;
	ExternalContext ec;
	
	public UserManagedBean(){
	
	FacesContext context = FacesContext.getCurrentInstance();
	ec = context.getExternalContext();
	session = (HttpSession) ec.getSession(false); 
	
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String login() {
		if ("occi".equalsIgnoreCase(getUsername())
				&& "occi".equals(getPassword())) {

			OcciUser loggedUser = new OcciUser(username,serverURL,port);
		
            session.setAttribute("user", loggedUser);
            System.out.println(loggedUser.getServerURL());
			return "home";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("username", new FacesMessage(
					"Invalid UserName and Password"));
			return "login";
		}
	}

	public String logout()
	{
	 // invalidate session
		if (session != null) {		
			session.invalidate();			
			}
		
		try {
	       ec.redirect("index.jsf");
	   } catch (IOException e) {
	       System.out.println("Redirect to the login page failed");
	       throw new FacesException(e);
	   }	   
	   return null;
	}

}