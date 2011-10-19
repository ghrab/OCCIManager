package eu.ena.view;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.FlowEvent;

import eu.ena.occi.Network;
import eu.ena.req.OcciResManager;

@ManagedBean
@ViewScoped
public class NetworkBean {

	private ArrayList<Network> fullNetList = new ArrayList<Network>();
	private Network selectedNet;

	public NetworkBean() {
		populateNets();
	}

	public void populateNets() {
		fullNetList = OcciResManager.getNetList();
		//System.out.println(fullNetList.toString());
	}

	public void updateList(CloseEvent event) {

		fullNetList = OcciResManager.getNetList();
		//System.out.println("List to be updated Now");
	}

	public Network getSelectedNet() {
		return selectedNet;
	}

	public void setSelectedNet(Network selectedNet) {
		this.selectedNet = selectedNet;
	}

	public ArrayList<Network> getFullNetList() {
		return fullNetList;
	}

	private boolean skip;

	private Network net = new Network();

	public Network getNet() {
		return net;
	}

	public void setNet(Network net) {
		this.net = net;
	}

	public void deleteNet(ActionEvent actionEvent) {
		if (selectedNet != null) { // System.out.println("NetId is : "+
									// getSelectedNet().getId());
			OcciResManager.delNetwork(getSelectedNet().getId());
			populateNets();
		} else
			System.out.println("404 NOT FOUND");

	}

	public void save(ActionEvent actionEvent) {
		OcciResManager.createNetwork(net);
		populateNets();
		FacesMessage msg = new FacesMessage("Successful", "Created Network:"
				+ net.getTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {

		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public void upNet() {
		this.selectedNet.activateNet();
	}

	public void downNet() {
		this.selectedNet.desactivateNet();
	}

}