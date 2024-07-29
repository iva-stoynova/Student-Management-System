package net.studentmanagementsystem.entity;

import java.util.List;


public class MyModel {

	private List<GroupCategory> categoryList;
	private List<Course> courseSearched;
	private List<TypeCourse> typeList;
	
	private String age;
	
	public MyModel() {
		
	}

	public MyModel(List<GroupCategory> categoryList, List<TypeCourse> typeList, List<Course> courseSearched, String age) {
		this.categoryList = categoryList;
		this.typeList = typeList;
		this.courseSearched = courseSearched;
		this.age = age;
	}


	public List<GroupCategory> getCategoryList() {
		return categoryList;
	}
	
	public void setCategoryList(List<GroupCategory> categoryList) {
		this.categoryList = categoryList;
	}
		
	public List<TypeCourse> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TypeCourse> typeList) {
		this.typeList = typeList;
	}

	public List<Course> getCourseSearched() {
		return courseSearched;
	}

	public void setCourseSearched(List<Course> courseSearched) {
		this.courseSearched = courseSearched;
	}
		
	@Override
	public String toString() {
		return "MyModel [categoryList=" + categoryList + ", courseSearched=" + courseSearched + ", typeList=" + typeList
				+ ", age=" + (age == null ? "<null>" : age.isEmpty() ? "<empty>" : age) + "]";
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
}
