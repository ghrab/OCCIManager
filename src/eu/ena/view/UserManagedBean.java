package eu.ena.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
			
			FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
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

	public String logout() {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(true)).invalidate();
		return "home.xhtml?faces-redirect=true";
	}

}