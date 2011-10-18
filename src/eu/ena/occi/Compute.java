package eu.ena.occi;

import java.util.Map;

import eu.ena.req.RestManager;

public class Compute extends Resource {

	private String arch;
	private String memo;
	private int cores;

	private String storageID;
	private String networkID;
	private String computeAttribute;
	private String linkHeader;

	public Compute() {
	}

	public Compute(String title, String summary, String arch, String cores,
			String memo, String storage, String net) {
		super(title, summary);
		this.setCategory("compute");
		this.setArch(arch);
		this.setCores(Integer.parseInt(cores));
		this.setMemo(memo);
		this.setStorage(storage);
		this.setNet(net);
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStorage() {
		return storageID;
	}

	public void setStorage(String storage) {
		this.storageID = storage;
	}

	public String getNet() {
		return networkID;
	}

	public void setNet(String link) {
		this.networkID = link;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public String getComputeAttribute() {
		return computeAttribute;
	}

	public void setComputeAttribute() {
		computeAttribute = "\",occi.compute.architecture=\"" + this.getArch()
				+ "\",occi.compute.cores=" + this.getCores()
				+ "\",occi.compute.memory=" + this.getMemo();
		this.setOcciAttribute(computeAttribute);
	}

	public String getLinkHeader() {
		return linkHeader;
	}

	public void setLinkHeader(String linkHeader) {
		this.linkHeader = "</network/"
				+ this.getNet()
				+ ">;rel=\"http://schemas.ogf.org/occi/infrastructure#network\";category=\"http://schemas.ogf.org/occi/core#link\";,"
				+ "</storage/"
				+ this.getStorage()
				+ ">;rel=\"http://schemas.ogf.org/occi/infrastructure#storage\";category=\"http://schemas.ogf.org/occi/core#link\";";
	}

	Map<String, String> headers;

	public String startCompute() {
		try {
			headers = this
					.prepareHeader(
							"start; scheme=\"http://schemas.ogf.org/occi/infrastructure/compute/action#\";class=\"action\"",
							"");
			return RestManager.postResource("/compute/" + this.getId()
					+ "?action='start'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error starting the virtual machine");
			return null;
		}
	}

	public String suspendCompute() {
		try {
			headers = this
					.prepareHeader(
							"suspend; scheme=\"http://schemas.ogf.org/occi/infrastructure/compute/action#\";class=\"action\"",
							"method=\"suspend\"");
			return RestManager.postResource("/compute/" + this.getId()
					+ "?action='suspend'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error suspending the virtual machine");
			return null;
		}
	}

	public String restartCompute() {
		try {
			headers = this
					.prepareHeader(
							"restart; scheme=\"http://schemas.ogf.org/occi/infrastructure/compute/action#\";class=\"action\"",
							"method=\"cold\"");
			return RestManager.postResource("/compute/" + this.getId()
					+ "?action='restart'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error restarting the virtual machine");
			return null;
		}

	}

	public String stopCompute() {
		try {
			headers = this
					.prepareHeader(
							"stop; scheme=\"http://schemas.ogf.org/occi/infrastructure/compute/action#\";class=\"action\"",
							"method=\"poweroff\"");
			return RestManager.postResource("/compute/" + this.getId()
					+ "?action='stop'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error stopping the virtual machine");
			return null;
		}
	}

}

/*
 * 
 * COM_CATEGORY='compute;
 * scheme="http://schemas.ogf.org/occi/infrastructure#";class="kind";'
 * COM_ATTRIBUTE='occi.core.title="My VM",' COM_ATTRIBUTE+='occi.core.summary="A
 * short summary",' COM_ATTRIBUTE+='occi.compute.architecture="x64",'
 * COM_ATTRIBUTE+='occi.compute.cores=1,' COM_ATTRIBUTE+='occi.compute.memory=4'
 * COM_LINK="<${NETWORK_LOCATION#*$URI}>"
 * ';rel="http://schemas.ogf.org/occi/infrastructure#network";category="http://schemas.ogf.org/occi/core#link";,'
 * COM_LINK+="<${STORAGE_LOCATION#*$URI}>"
 * ';rel="http://schemas.ogf.org/occi/infrastructure#storage";category="http://schemas.ogf.org/occi/core#link";'
 * 
 * COM_ACTION='stop' COM_ACTION_CATEGORY='stop;
 * scheme="http://schemas.ogf.org/occi/infrastructure/compute/action#"
 * ;class="action";' COM_ACTION_ATTRIBUTE='method="poweroff"'
 * 
 * COM_ACTION='start' COM_ACTION_CATEGORY='start;
 * scheme="http://schemas.ogf.org/occi/infrastructure/compute/action#"
 * ;class="action"'
 * 
 * curl -X POST --header "Category: $COM_ACTION_CATEGORY" --header
 * "X-OCCI-Attribute: $COM_ACTION_ATTRIBUTE" ${COMPUTE_LOCATION#*
 * }?action=$COM_ACTION
 */