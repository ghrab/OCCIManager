package eu.ena.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Parsing of text plain responses received from the OCCI server
 */
public class OcciParser {

	Logger logger = Logger.getLogger(RestManager.class);
	static Map<String, String> entity = new HashMap<String, String>();

	/**
	 * Returns a list of Strings Parsing of a set of elements to be then viewed
	 * within the DataTable
	 * 
	 * @param resourceList
	 *            : a String containing the representation of the resource as
	 *            received from the server
	 * @param resType
	 *            : the type of the resource (compute etc)
	 */
	public static ArrayList<String> parseList(String resourceList,
			String resType) {

		ArrayList<String> list = new ArrayList<String>();
		String[] temp = resourceList
				.split("X-OCCI-Location:[\\s]http://localhost/:3000" + "/"
						+ resType + "/");
		for (int i = 1; i < temp.length; i++)
			list.add(temp[i]);
		return list;
	}

	/**
	 * Returns a map of the separated attributes of the resource Parsing of a
	 * single element
	 * 
	 * @param fullResource
	 *            : a String containing the representation of the resource as
	 *            received from the server
	 */
	public static Map<String, String> parseElement(String fullResource) {

		try {
			String[] temp = fullResource.split("X-OCCI-Attribute:[\\s]|=|Link");
			int i = 1;
			while (i < temp.length) {
				if (temp[i].startsWith("occi")
						|| temp[i].startsWith("opennebula.image")) {
					// System.out.println(temp[i]+temp[i+1]);
					entity.put(temp[i], temp[i + 1]);
					i++;
				}

				i++;
			}
			// System.out.println(entity.toString());
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}