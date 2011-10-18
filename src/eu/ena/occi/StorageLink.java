package eu.ena.occi;

public class StorageLink extends eu.ena.occi.Link {
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	private String state;
	private String mountPoint;
	private String deviceId;
}