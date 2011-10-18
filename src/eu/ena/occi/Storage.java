package eu.ena.occi;

import java.util.Map;

import eu.ena.req.RestManager;

public class Storage extends Resource {

	private String filePath;
	private String storageAttribute;

	private String dev_prefix, bus, imgPublic, persistent, type, size;

	public Storage() {
	}

	/*
	 * Create Request
	 */
	public Storage(String filePath, String title, String summary) {
		super(title, summary);
		this.setFilePath(filePath);
		this.setCategory("storage");
	}

	/*
	 * GET Request
	 */
	public Storage(String dev_prefix, String summary, String title,
			String state, String bus, String imgPublic, String id,
			String persistent, String type, String size) {
		super(title, summary);
		this.setDev_prefix(dev_prefix);
		this.setBus(bus);
		this.setImgPublic(imgPublic);
		this.setId(id);
		this.setPersistent(persistent);
		this.setSize(size);
		this.setType(type);

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStorageAttribute() {
		return storageAttribute;
	}

	public void setStorageAttribute() {
		this.setOcciAttribute("");
	}

	public String getDev_prefix() {
		return dev_prefix;
	}

	public void setDev_prefix(String dev_prefix) {
		this.dev_prefix = dev_prefix;
	}

	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getImgPublic() {
		return imgPublic;
	}

	public void setImgPublic(String imgPublic) {
		this.imgPublic = imgPublic;
	}

	public String getPersistent() {
		return persistent;
	}

	public void setPersistent(String persistent) {
		this.persistent = persistent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	Map<String, String> headers;

	public String putOnline() {
		try {
			headers = this
					.prepareHeader(
							"online; scheme=\"http://schemas.ogf.org/occi/infrastructure/storage/action#\";class=\"action\"",
							"");
			return RestManager.postResource("/storage/" + this.getId()
					+ "?action='online'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error activating the storage");
			return null;
		}
	}

	public String putOffline() {
		try {
			headers = this
					.prepareHeader(
							"offline; scheme=\"http://schemas.ogf.org/occi/infrastructure/storage/action#\";class=\"action\"",
							"");
			return RestManager.postResource("/storage/" + this.getId()
					+ "?action='offline'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error desactivating the storage");
			return null;
		}
	}

	public String resizeStorage(String newSize) {
		try {
			headers = this
					.prepareHeader(
							"online; scheme=\"http://schemas.ogf.org/occi/infrastructure/storage/action#\";class=\"action\"",
							"size =" + newSize);
			return RestManager.postResource("/storage/" + this.getId()
					+ "?action='online'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error resizing the storage");
			return null;
		}
	}

	public String backupStorage() {
		try {
			headers = this
					.prepareHeader(
							"backup; scheme=\"http://schemas.ogf.org/occi/infrastructure/storage/action#\";class=\"action\"",
							"");
			return RestManager.postResource("/storage/" + this.getId()
					+ "?action='backup'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error backing up the storage");
			return null;
		}
	}

	public String snapshotStorage() {
		try {
			headers = this
					.prepareHeader(
							"snapshot; scheme=\"http://schemas.ogf.org/occi/infrastructure/storage/action#\";class=\"action\"",
							"");
			return RestManager.postResource("/storage/" + this.getId()
					+ "?action='snapshot'", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error snapshotting the storage");
			return null;
		}
	}

}

/*
 * curl -X POST -F "file=@/home/ena/Downloads/ttylinux.img" --header 'Category:
 * storage;scheme="http://schemas.ogf.org/occi/infrastructure#";class="kind";'
 * --header 'X-OCCI-Attribute:
 * occi.core.title="My Storage",occi.core.summary="A short summary"'
 * http://localhost:3000/storage/
 * 
 * 
 * Location: http://localhost:3000/storage/d09036bc-d472-11e0-ad3f-525400227607
 * 
 * 
 * STOR_CATEGORY=
 * 'storage;scheme="http://schemas.ogf.org/occi/infrastructure#";class="kind";'
 * STOR_ATTRIBUTE='occi.core.title="My Storage",'
 * STOR_ATTRIBUTE+='occi.core.summary="A short summary"'
 */