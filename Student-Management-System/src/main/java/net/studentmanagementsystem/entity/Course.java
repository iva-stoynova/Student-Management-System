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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;

	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	private String type;
	
	@Column(name = "academic_year")
	private String academicYear;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinTable(name="student_courses",
				joinColumns = @JoinColumn(name="course_id"),
				inverseJoinColumns = @JoinColumn(name="student_id"))			
	private List<Student> students;
	
	

	public Course() {
		
	}

	public Course(String code, String name, String type, String academicYear, Teacher teacher, List<Student> students) {		
		this.code = code;
		this.name = name;
		this.type = type;
		this.academicYear = academicYear;
		this.teacher = teacher;
		this.students = students;
	}

		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		teacher.addCourse(this);
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public int studentListSize() {
		return students.size();
	}
	
	public void addStudent(Student student) {
		if(students == null) {
			students = new ArrayList<>();
		}
		students.add(student);
	}
	
	public void removeStudent(Student student) {
		if(students.contains(student)) {
			students.remove(student);
		}
	}
	
	public boolean equals(Object comparedObject) {
	    if (this == comparedObject) {
	        return true;
	    }

	   if (!(comparedObject instanceof Course)) {
	        return false;
	    }

	    Course comparedCourse = (Course) comparedObject;

	    if (this.id == comparedCourse.id) {
	        return true;
	    }

	    return false;
	}		
}