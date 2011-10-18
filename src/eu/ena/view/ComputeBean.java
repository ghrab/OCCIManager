package eu.ena.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.HashMap;
import java.util.Map;
import org.primefaces.event.FlowEvent;

import eu.ena.occi.Compute;
import eu.ena.occi.Network;
import eu.ena.occi.Storage;
import eu.ena.req.OcciResManager;

@ManagedBean
@ViewScoped
public class ComputeBean {

	private ArrayList<Compute> fullCmpList = new ArrayList<Compute>();
	private Compute selectedCmp;
	List<String> listOfArch,listOfStr,listOfNet;
	private String str,net;
	private Compute cmp = new Compute();

	public ComputeBean() {
		populateCmps();
	}

	private void populateCmps() {
		fullCmpList = OcciResManager.getCmpList();
		System.out.println(fullCmpList.toString());
	}

	public Compute getSelectedCmp() {
		return selectedCmp;
	}

	public void setSelectedCmp(Compute selectedCmp) {
		this.selectedCmp = selectedCmp;
	}

	public ArrayList<Compute> getFullCmpList() {
		return fullCmpList;
	}
	

	public Compute getCmp() {
		return cmp;
	}

	public void setCmp(Compute cmp) {
		this.cmp = cmp;
	}

	public void deleteCmp(ActionEvent actionEvent) {
		if (selectedCmp != null) { 									
			OcciResManager.delCompute(getSelectedCmp().getId());
			populateCmps();
		} else
			System.out.println("404 NOT FOUND");

	}
	
	public List<String> getListOfArch() {
        List<String> archs = new ArrayList<String>();
        archs.add("x86");
        archs.add("x64");
        return archs;
    }

	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	Map <String,String> strList = new HashMap<String, String>();
	
	public List<String> getListOfStr() {
		StorageBean str=new StorageBean();
		listOfStr=new ArrayList<String>();
        for (Storage it : str.getFullStrList()) {
            strList.put(it.getTitle(),it.getId());
            listOfStr.add(it.getTitle());
        }
        
        return listOfStr;
	}
	
	public String getNet() {
		StorageBean st=new StorageBean();
		st.getFullStrList();
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	Map <String,String> netList = new HashMap<String, String>();
	
	public List<String> getListOfNet() {
		NetworkBean net=new NetworkBean();        
		listOfNet=new ArrayList<String>();
		for (Network it : net.getFullNetList()) {
            netList.put(it.getTitle(),it.getId());
            listOfNet.add(it.getTitle());
        }
        return listOfNet;
	}
	
	public void save(ActionEvent actionEvent) {		
		cmp.setNet(netList.get(net));
		cmp.setStorage(strList.get(str));
		OcciResManager.createCompute(cmp);
		// populateCmps();
		FacesMessage msg = new FacesMessage("Successful", "Created Compute:"
				+ cmp.getTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
	
	private boolean skip;
	
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

	public void startVM() {
		this.selectedCmp.startCompute();

	}

	public void restartVM() {
		this.selectedCmp.restartCompute();

	}

	public void susupendVM() {
		this.selectedCmp.suspendCompute();

	}

	public void stopVM() {
		this.selectedCmp.stopCompute();

	}
}
