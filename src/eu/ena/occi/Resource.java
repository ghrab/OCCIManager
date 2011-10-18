package eu.ena.occi;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.ena.req.RestManager;

/**
 * Inherit from Entity and and gather common operations of
 * compute/storage/network resources
 */
public class Resource extends Entity {

	static Logger logger = Logger.getLogger(RestManager.class);

	private String category;
	private String occiAttribute;

	private String state;

	private String summary;
	private String baseCat = ";scheme=\"http://schemas.ogf.org/occi/infrastructure#\";class=\"kind\";";

	Map<String, String> postHeaders = new HashMap<String, String>();

	public Resource() {
	}

	public Resource(String title, String summary) {
		this.setTitle(title);
		this.setSummary(summary);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category + baseCat;
		if (category.equals("network"))
			this.category += ",ipnetwork;scheme=\"http://schemas.ogf.org/occi/infrastructure/network#\";class=\"kind\";";
	}

	public String getOcciAttribute() {
		return occiAttribute;
	}

	public void setOcciAttribute(String attribute) {
		this.occiAttribute = "occi.core.title=\"" + this.getTitle()
				+ "\",occi.core.summary=\"" + this.getSummary() + attribute;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Map<String, String> prepareHeader(String category, String attribute) {
		postHeaders.clear();
		postHeaders.put("Category", category);
		postHeaders.put("X-OCCI-Attribute", attribute);
		return postHeaders;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/*
	 * Get All resources : "" Get X-Type resourceS : "/X/" X=
	 * compute;storage;link;network Get the resource : "/X/id" "-": full
	 * representation
	 */

	public String getRepresentation() { // Retrieves the Representation of
										// Resources
		try {
			return RestManager.getResource("/-/");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Representation not found");
			return null;
		}

	}

	public String getAllResources() { // Retrieves the pool of Resources
		try {
			return RestManager.getResource("/");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No Resources found !!");
			return null;
		}
	}

	public boolean delAllResources() { // deletes all resources
		try {
			return RestManager.deleteResource("/");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting all resources");
			return false;
		}
	}

}
/*
 * ena@amine-worker:~$ curl -X GET http://192.168.122.179:3000/-/ Category:
 * entity; scheme="http://schemas.ogf.org/occi/core#";
 * class="kind";title="Entity"
 * ;attributes="occi.core.title occi.core.id";actions=""; Category: resource;
 * scheme="http://schemas.ogf.org/occi/core#";
 * class="kind";title="Resource";rel=
 * "http://schemas.ogf.org/occi/core#entity";attributes
 * ="occi.core.summary links";actions=""; Category: link;
 * scheme="http://schemas.ogf.org/occi/core#";
 * class="kind";title="Link";rel="http://schemas.ogf.org/occi/core#entity"
 * ;location=/link/;attributes="occi.core.source occi.core.target";actions="";
 * 
 * Category: compute; scheme="http://schemas.ogf.org/occi/infrastructure#";
 * class
 * ="kind";title="Compute Resource";rel="http://schemas.ogf.org/occi/core#resource"
 * ;location=/compute/;attributes=
 * "occi.compute.cores occi.compute.memory occi.compute.state occi.compute.speed occi.compute.hostname occi.compute.architecture"
 * ;actions=
 * "http://schemas.ogf.org/occi/infrastructure/compute/action#restart http://schemas.ogf.org/occi/infrastructure/compute/action#start http://schemas.ogf.org/occi/infrastructure/compute/action#stop http://schemas.ogf.org/occi/infrastructure/compute/action#suspend"
 * ; Category: network; scheme="http://schemas.ogf.org/occi/infrastructure#";
 * class
 * ="kind";title="Network Resource";rel="http://schemas.ogf.org/occi/core#resource"
 * ;location=/network/;attributes=
 * "occi.network.label occi.network.vlan occi.network.state";actions=
 * "http://schemas.ogf.org/occi/infrastructure/network/action#down http://schemas.ogf.org/occi/infrastructure/network/action#up"
 * ; Category: storage; scheme="http://schemas.ogf.org/occi/infrastructure#";
 * class
 * ="kind";title="Storage Resource";rel="http://schemas.ogf.org/occi/core#resource"
 * ;
 * location=/storage/;attributes="occi.storage.state occi.storage.size";actions=
 * "http://schemas.ogf.org/occi/infrastructure/storage/action#backup http://schemas.ogf.org/occi/infrastructure/storage/action#offline http://schemas.ogf.org/occi/infrastructure/storage/action#online http://schemas.ogf.org/occi/infrastructure/storage/action#resize http://schemas.ogf.org/occi/infrastructure/storage/action#snapshot"
 * ; Category: storagelink;
 * scheme="http://schemas.ogf.org/occi/infrastructure#";
 * class="kind";title="StorageLink"
 * ;rel="http://schemas.ogf.org/occi/core#link";location
 * =/storagelink/;attributes
 * ="occi.storagelink.state occi.storagelink.mountpoint occi.storagelink.deviceid"
 * ;actions=""; Category: networkinterface;
 * scheme="http://schemas.ogf.org/occi/infrastructure#";
 * class="kind";title="Networkinterface"
 * ;rel="http://schemas.ogf.org/occi/core#link"
 * ;location=/networkinterface/;attributes=
 * "occi.networkinterface.mac occi.networkinterface.state occi.networkinterface.interface"
 * ;actions="";
 * 
 * Category: virtualmachine;
 * scheme="http://schemas.opennebula.org/occi/infrastructure#";
 * class="mixin";title="OpenNebula Virtual Machine Mixin";attributes=
 * "opennebula.vm.vnc_url opennebula.vm.boot opennebula.vm.vcpu";actions="";
 * Category: virtualnetwork;
 * scheme="http://schemas.opennebula.org/occi/infrastructure#";
 * class="mixin";title="OpenNebula Virtual Network Mixin";attributes=
 * "opennebula.network.public opennebula.network.leases";actions=""; Category:
 * image; scheme="http://schemas.opennebula.org/occi/infrastructure#";
 * class="mixin";title="OpenNebula Image Mixin";attributes=
 * "opennebula.image.fstype opennebula.image.source opennebula.image.dev_prefix opennebula.image.bus opennebula.image.public opennebula.image.path opennebula.image.persistent opennebula.image.type"
 * ;actions="";
 * 
 * Category: ipnetwork;
 * scheme="http://schemas.ogf.org/occi/infrastructure/network#";
 * class="mixin";title="IP Network Mixin";location=/ipnetworking/;attributes=
 * "occi.network.allocation occi.network.address occi.network.gateway"
 * ;actions="";
 * 
 * 
 * Category: reservation; scheme="http://schemas.ogf.org/occi/#";
 * class="mixin";title="Reservation";attributes=
 * "occi.reservation.preemptible occi.reservation.start occi.reservation.duration"
 * ;actions="";
 * 
 * 
 * Category: start;
 * scheme="http://schemas.ogf.org/occi/infrastructure/compute/action#";
 * class="action"; Category: suspend;
 * scheme="http://schemas.ogf.org/occi/infrastructure/compute/action#";
 * class="action"; Category: restart;
 * scheme="http://schemas.ogf.org/occi/infrastructure/compute/action#";
 * class="action"; Category: stop;
 * scheme="http://schemas.ogf.org/occi/infrastructure/compute/action#";
 * class="action";
 * 
 * Category: snapshot;
 * scheme="http://schemas.ogf.org/occi/infrastructure/storage/action#";
 * class="action"; Category: backup;
 * scheme="http://schemas.ogf.org/occi/infrastructure/storage/action#";
 * class="action"; Category: resize;
 * scheme="http://schemas.ogf.org/occi/infrastructure/storage/action#";
 * class="action"; Category: offline;
 * scheme="http://schemas.ogf.org/occi/infrastructure/storage/action#";
 * class="action"; Category: online;
 * scheme="http://schemas.ogf.org/occi/infrastructure/storage/action#";
 * class="action";
 * 
 * Category: up;
 * scheme="http://schemas.ogf.org/occi/infrastructure/network/action#";
 * class="action"; Category: down;
 * scheme="http://schemas.ogf.org/occi/infrastructure/network/action#";
 * class="action";
 */