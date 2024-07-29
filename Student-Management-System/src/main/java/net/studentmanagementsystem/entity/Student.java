package net.studentmanagementsystem.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "academic_year")
	private String academicYear;
	
	@Column(name = "academic_division")
	private String academicDivision;
	
		
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="student_courses",
				joinColumns = @JoinColumn(name="student_id"),
				inverseJoinColumns = @JoinColumn(name="course_id"))	
	private List<Course> courses;
	
	
	public Student() {
		
	}
	
	public Student(String firstName, String lastName, int age, String academicYear, String academicDivision) { //String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;	
		this.age = age;
		this.academicYear = academicYear;
		this.academicDivision = academicDivision;
	}
	
	public Student(String firstName, String lastName, int age, String academicYear, String academicDivision, List<Course> courses) { //String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.academicYear = academicYear;
		this.academicDivision = academicDivision;
		this.courses = courses;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getAcademicDivision() {
		return academicDivision;
	}

	public void setAcademicDivision(String academicDivision) {
		this.academicDivision = academicDivision;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	
	
	public void addCourse(Course course) {
		if(courses == null) {
			courses = new ArrayList<>();
		}
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		courses.remove(course);
	}
	
	public boolean equals(Object comparedObject) {
	    if (this == comparedObject) {
	        return true;
	    }

	   if (!(comparedObject instanceof Student)) {
	        return false;
	    }

	    Student comparedStudent = (Student) comparedObject;

	    if (this.id == comparedStudent.id) {
	        return true;
	    }

	    return false;
	}
	
	
}
