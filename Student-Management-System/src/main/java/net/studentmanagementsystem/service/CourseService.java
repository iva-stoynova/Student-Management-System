package net.studentmanagementsystem.service;

import java.util.List;

import net.studentmanagementsystem.entity.Course;

public interface CourseService {

	
	List<Course> getAllCourses();

	Course saveCourse(Course course);
	
	Course getCourseById(Long id);
	
	Course updateCourse(Course student);
	
	void deleteCourseById(Long id);
	
	
}
