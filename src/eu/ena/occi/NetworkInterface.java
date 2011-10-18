package eu.ena.occi;

public class NetworkInterface extends eu.ena.occi.Link {
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNetInterface() {
		return netInterface;
	}

	public void setNetInterface(String netInterface) {
		this.netInterface = netInterface;
	}

	private String mac;
	private String state;
	private String netInterface;
}