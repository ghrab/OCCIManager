package eu.ena.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.ena.occi.Compute;
import eu.ena.occi.Network;
import eu.ena.occi.Storage;

/**
 * Entry point to manage the available resources using CRUD operations
 */
public class OcciResManager {
	static Logger logger = Logger.getLogger(RestManager.class);
	static Map<String, String> headers;


	/**
	 * Returns the ID of the created Network Create A network : 1-Get the
	 * Network object 2-Prepare the Category part of the header 2-Prepare the
	 * OCCI Attribute part of the header Prepare the full headers Fire the HTTP
	 * Post Request with these headers to the network URI
	 * 
	 * @param newNet
	 *            : the network to be created
	 * 
	 */
	public static String createNetwork(Network newNet) {
		try {
			newNet.setCategory("network");
			newNet.setNetAttribute();
			headers = newNet.prepareHeader(newNet.getCategory(),
					newNet.getOcciAttribute());
			return RestManager.postResource("/network/", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error creating the net");
			return null;
		}
	}

	/**
	 * Returns the ID of the created storage
	 * 
	 * Create A storage : 1-Get the Storage object 2-Prepare the Category part
	 * of the header 3-Prepare the OCCI Attribute part of the header 4-Prepare
	 * the FilePath Option 5-Prepare the full headers 6-Fire the HTTP Post
	 * Request with these headers to the storage URI
	 * 
	 * @param newStor
	 *            : The storage to be created
	 */
	public static String createStorage(Storage newStor) {
		try {
			newStor.setCategory("storage");
			newStor.setStorageAttribute();
			headers = newStor.prepareHeader(newStor.getCategory(),
					newStor.getOcciAttribute());
			headers.put("PATH", newStor.getFilePath());
			return RestManager.postResource("/storage/", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting the storage");
			return null;
		}
	}

	/**
	 * Returns the ID of the created Vmachine
	 * 
	 * Create A network : 1-Get the Network object 2-Prepare the Category part
	 * of the header 3-Prepare the OCCI Attribute part of the header 4-Prepare
	 * the full headers 5-Add the Links to Storage and Network 6-Fire the HTTP
	 * Post Request with these headers to the network URI
	 * 
	 * @param newComp
	 *            :the compute to be created
	 */
	public static String createCompute(Compute newComp) {
		try {

			newComp.setCategory("compute");
			newComp.setComputeAttribute();
			headers = newComp.prepareHeader(newComp.getCategory(),
					newComp.getOcciAttribute());
			headers.put("Link", newComp.getLinkHeader());
			return RestManager.postResource("/compute/", headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error creating the VM");
			return null;
		}
	}

	/****************************************************************************/

	/**
	 * Retrieves the pool of IDs of Virtual Computes
	 * 
	 */
	public static ArrayList<Compute> getCmpList() {
		try {

			ArrayList<String> cmpListString = OcciParser.parseList(
					RestManager.getResource("/compute/"), "compute");
			// System.out.println(cmpListString.toString());
			ArrayList<Compute> cmpList = new ArrayList<Compute>();
			for (int i = 0; i < cmpListString.size(); i++) {
				cmpList.add(OcciResManager.getComputeElement(cmpListString
						.get(i)));
			}

			return cmpList;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" No Computes found");
			return null;
		}
	}

	/**
	 * Retrieve a compute object using its ID
	 * 
	 * @param id
	 *            : the ID of the resource to be treated
	 */
	public static Compute getComputeElement(String id) {
		Map<String, String> element = new HashMap<String, String>();
		try {
			element = OcciParser.parseElement(RestManager
					.getResource("/compute/" + id));

			System.out.println(element.toString());
			Compute cmp = new Compute();
			cmp.setCategory("compute");
			cmp.setId(element.get("occi.core.id").replace("\"", ""));
			cmp.setTitle(element.get("occi.core.title").replace("\"", ""));
			cmp.setSummary(element.get("occi.core.summary").replace("\"", ""));
			try{
			cmp.setCores(Integer.parseInt(element.get("occi.compute.core")));
			}
			catch(NumberFormatException ne){
				cmp.setCores(0);
			}
			cmp.setMemo(element.get("occi.compute.memo"));
			cmp.setState(element.get("occi.compute.state"));

			// System.out.println(cmp.getId());
			return cmp;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" A Compute Not found");
			return null;
		}

	}

	/**
	 * deletes a single VM
	 * 
	 * @param id
	 *            : the ID of the resource to be treated
	 */
	public static boolean delCompute(String vmId) {
		try {
			return RestManager.deleteResource("/compute/" + vmId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting the VM");
			return false;
		}
	}

	/**
	 * deletes all VMs
	 */
	public boolean delComputes() {
		try {
			return RestManager.deleteResource("/compute/");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting all VMs");
			return false;
		}
	}

	/**
	 * deletes a single link
	 */
	public boolean delLink(String lnId) {
		try {
			return RestManager.deleteResource("/link/" + lnId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting the link");
			return false;
		}

	}

	/**
	 * Retrieves the pool of Virtual Networks IDs
	 */
	public static ArrayList<Network> getNetList() {

		try {

			ArrayList<String> netListString = OcciParser.parseList(
					RestManager.getResource("/network/"), "network");
			// System.out.println(netListString.toString());
			ArrayList<Network> netList = new ArrayList<Network>();
			for (int i = 0; i < netListString.size(); i++) {
				netList.add(OcciResManager.getNetworkElement(netListString
						.get(i)));
			}

			return netList;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" No Nets found");
			return null;
		}
	}

	/**
	 * Retrieve a network object
	 */
	public static Network getNetworkElement(String id) {
		Map<String, String> element = new HashMap<String, String>();
		try {
			element = OcciParser.parseElement(RestManager
					.getResource("/network/" + id));

			System.out.println(element.toString());
			Network net = new Network();
			net.setCategory("network");
			net.setId(element.get("occi.core.id"));
			net.setTitle(element.get("occi.core.title"));
			net.setSummary(element.get("occi.core.summary"));
			net.setAddress(element.get("occi.network.address"));
			net.setAllocation(element.get("occi.network.allocation"));
			net.setState(element.get("occi.network.state"));

			// System.out.println(net.getId());
			return net;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" A Net Not found");
			return null;
		}

	}

	/**
	 * deletes a single network
	 */
	public static boolean delNetwork(String netId) {
		try {
			return RestManager.deleteResource("/network/" + netId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting the net : " + netId);
			return false;
		}
	}

	/**
	 * deletes all network
	 */
	public static boolean delNetworks(String netId) {
		try {
			return RestManager.deleteResource("/network/");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting all net");
			return false;
		}
	}

	/****************************************************************************/
	/**
	 * Retrieves the pool of Storage IDs
	 */
	public static ArrayList<Storage> getStrList() {
		try {

			ArrayList<String> strListString = OcciParser.parseList(
					RestManager.getResource("/storage/"), "storage");
			ArrayList<Storage> strList = new ArrayList<Storage>();
			for (int i = 0; i < strListString.size(); i++) {
				strList.add(OcciResManager.getStorageElement(strListString
						.get(i)));
			}

			return strList;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" No Storage found");
			return null;
		}
	}

	/**
	 * Retrieves a single Storage ID
	 */

	public static Storage getStorageElement(String id) {

		Map<String, String> element = new HashMap<String, String>();
		try {
			element = OcciParser.parseElement(RestManager
					.getResource("/storage/" + id));
			// System.out.println(element.get("occi.network.allocation").replace("\"",
			// ""));

			Storage str = new Storage();
			str.setCategory("storage");
			str.setId(element.get("occi.core.id").replace("\"", ""));
			str.setTitle(element.get("occi.core.title").replace("\"", ""));
			str.setSummary(element.get("occi.core.summary").replace("\"", ""));
			str.setBus(element.get("opennebula.image.bus").replace("\"", ""));
			str.setDev_prefix(element.get("opennebula.image.dev_prefix")
					.replace("\"", ""));
			str.setImgPublic(element.get("opennebula.image.public"));
			str.setType(element.get("opennebula.image.type"));
			str.setPersistent(element.get("opennebula.image.persistent"));
			str.setState(element.get("occi.storage.state"));
			// System.out.println(net.getId());
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" A Storage Not found");
			return null;
		}
	}

	/**
	 * delete a single storage
	 */
	public static boolean delStorage(String stId) {
		try {
			return RestManager.deleteResource("/storage/" + stId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting the image " + stId);
			return false;
		}
	}

	/**
	 * delete all storages
	 */
	public static boolean delStorages() {
		try {
			return RestManager.deleteResource("/storage/");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error deleting all the images");
			return false;
		}
	}

}
