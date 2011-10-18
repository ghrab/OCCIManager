package eu.ena.occi;

/*
 * The highest abstraction level in an OCCI representation
 */
public class Entity {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String id;
	private String title;

}