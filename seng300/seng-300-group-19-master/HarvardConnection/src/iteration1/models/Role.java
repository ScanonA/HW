package iteration1.models;

public class Role {
	private Integer id;
	private String name;

	public Role(Integer id) {
		this.id = id;
		
		if (id == 1) {			
			this.name = "admin";
		} else if (id == 2) {
			this.name = "reviewer";
		} else {
			this.name = "author";
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
