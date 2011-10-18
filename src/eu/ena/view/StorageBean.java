package eu.ena.view;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.FlowEvent;

import eu.ena.occi.Storage;
import eu.ena.req.OcciResManager;

@ManagedBean
@ViewScoped
public class StorageBean {

	private ArrayList<Storage> fullStrList = new ArrayList<Storage>();
	// private Storage;
	private Storage selectedStr;

	public StorageBean() {
		populateStrs();
	}

	private void populateStrs() {
		fullStrList = OcciResManager.getStrList();
		System.out.println(fullStrList.toString());
	}

	public void updateList(CloseEvent event) {

		fullStrList = OcciResManager.getStrList();
		System.out.println("List to be updated Now");
	}

	public Storage getSelectedStr() {
		return selectedStr;
	}

	public void setSelectedStr(Storage selectedStr) {
		this.selectedStr = selectedStr;
	}

	public ArrayList<Storage> getFullStrList() {
		return fullStrList;
	}

	private boolean skip;

	private Storage str = new Storage();

	public Storage getStr() {
		return str;
	}

	public void setStr(Storage str) {
		this.str = str;
	}

	public void deleteStr(ActionEvent actionEvent) {
		if (selectedStr != null) { // System.out.println("NetId is : "+
									// getSelectedNet().getId());
			OcciResManager.delNetwork(getSelectedStr().getId());
			populateStrs();
		} else
			System.out.println("404 NOT FOUND");

	}

	public void save(ActionEvent actionEvent) {
		OcciResManager.createStorage(str);
		// populateStrs();
		FacesMessage msg = new FacesMessage("Successful", "Created Storage:"
				+ str.getTitle());
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

	String size;
	
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}


	public void resizeImg() {
		this.selectedStr.setSize(size);
	}

	public void snapshotImg() {
		this.selectedStr.snapshotStorage();

	}

	public void backupImg() {
		this.selectedStr.backupStorage();

	}

	public void onImg() {
		this.selectedStr.putOnline();

	}

	public void offImg() {
		this.selectedStr.putOffline();

	}

}