package net.studentmanagementsystem.entity;

public class GroupCategory {

	private String id;
	private String name;
	
	
	
	public GroupCategory() {
		
	}
	public GroupCategory(String id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Catgory [id=" + id + ", name=" + name + "]";
	}	
}
