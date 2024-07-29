package net.studentmanagementsystem.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.studentmanagementsystem.entity.Course;
import net.studentmanagementsystem.repository.CourseRepository;
import net.studentmanagementsystem.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseRepository courseRepository;
	
	
	
	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	
	
	@Override
	public Course getCourseById(Long id) {
		return courseRepository.findById(id).get();
	}

	@Override
	public Course updateCourse(Course course) {
		return courseRepository.save(course);
	}

	
	@Override
	public void deleteCourseById(Long id) {
		courseRepository.deleteById(id);		
	}


	@Override
	public Course saveCourse(Course course) {		
		return courseRepository.save(course);
	}
		
}
