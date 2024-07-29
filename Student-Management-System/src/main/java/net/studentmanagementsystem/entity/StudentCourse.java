package net.studentmanagementsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name="student_courses")
public class StudentCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="student_id")
	private long studentId;
	
	@Column(name="course_id")
	private long courseId;
	
	
	public StudentCourse() {
		
	}


	public StudentCourse(long studentId, long courseId) { 
		this.studentId = studentId;
		this.courseId = courseId;		
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	
}

	