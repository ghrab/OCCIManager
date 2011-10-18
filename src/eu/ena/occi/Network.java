package eu.ena.occi;

import java.util.Map;

import eu.ena.req.RestManager;

public class Network extends Resource {

	private String address;
	private String allocation;
	private String vlan;

	private String netAttribute;

	public Network() {
	}

	/*
	 * For create requests
	 */
	public Network(String title, String summary, String address,
			String allocation, String vlan) {
		super(title, summary);
		this.setCategory("network");
		this.setAddress(address);
		this.setAllocation(allocation);
		this.setVlan(vlan);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getVlan() {
		return vlan;
	}

	public void setVlan(String vlan) {
		this.vlan = vlan;
	}

	public void setNetAttribute() {
		netAttribute = "\",occi.network.address=\"" + this.getAddress()
				+ "\",occi.network.allocation=\"" + this.getAllocation()
				+ "\",occi.network.vlan=" + this.getVlan();
		this.setOcciAttribute(netAttribute);
	}

	public String getNetAttribute() {
		return netAttribute;
	}

	public boolean netUp() {
		throw new UnsupportedOperationException();
	}

	public boolean netDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "Network [address=" + address + ", allocation="
				+ this.getAllocation() + ", summary=" + this.getSummary()
				+ ", title=" + this.getTitle() + "]";
	}

	public static boolean delNets() { // deletes a single network
		try {

			return RestManager.deleteResource("/network/");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting all nets");
			return false;
		}
	}

	Map<String, String> headers;

	public String activateNet() {
		try {
			headers = this
					.prepareHeader(
							"up; scheme=\"http://schemas.ogf.org/occi/infrastructure/network/action#\";class=\"action\"",
							"");
			return RestManager.postResource("/network/" + this.getId()
					+ "?action='up'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error activating the network");
			return null;
		}
	}

	public String desactivateNet() {
		try {
			headers = this
					.prepareHeader(
							"down; scheme=\"http://schemas.ogf.org/occi/infrastructure/network/action#\";class=\"action\"",
							"");
			return RestManager.postResource("/network/" + this.getId()
					+ "?action='down'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error desactivating the network");
			return null;
		}
	}

}
/*
 * curl -v -X POST --header
 * 'Category:network;scheme="http://schemas.ogf.org/occi/infrastructure#";class="kind";,ipnetwork;scheme="http://schemas.ogf.org/occi/infrastructure/network#";class="kind";'
 * 
 * --header ' X-OCCI-Attribute:occi.core.title="My Second Net",
 * occi.core.summary="Summary of the Net", occi.network.address="10.10.10.0/24",
 * occi.network.allocation="dynamic", occi.network.vlan=1'
 * 
 * 
 * http://192.168.122.179:3000/network/
 * 
 * 
 * 
 * curl -X GET
 * http://192.168.122.179:3000/network/a7e7734e-d529-11e0-8891-525400227607
 * 
 * X-OCCI-Attribute: occi.network.allocation="dynamic" X-OCCI-Attribute:
 * occi.core.summary="Summary of the Net" X-OCCI-Attribute:
 * occi.core.title="My Second Net" X-OCCI-Attribute:
 * occi.network.address="10.10.10.0/24" X-OCCI-Attribute:
 * occi.core.id="a7e7734e-d529-11e0-8891-525400227607" X-OCCI-Attribute:
 * occi.network.state="inactive"
 * 
 * NET_CATEGORY=
 * 'network;scheme="http://schemas.ogf.org/occi/infrastructure#";class="kind";,'
 * NET_CATEGORY+=
 * 'ipnetwork;scheme="http://schemas.ogf.org/occi/infrastructure/network#";class="kind";'
 * 
 * NET_ATTRIBUTE='occi.core.title="My Network",'
 * NET_ATTRIBUTE+='occi.core.summary="A short summary",'
 * NET_ATTRIBUTE+='occi.network.address="192.168.0.0/24",'
 * NET_ATTRIBUTE+='occi.network.allocation="dynamic",'
 * NET_ATTRIBUTE+='occi.network.vlan=1'
 */