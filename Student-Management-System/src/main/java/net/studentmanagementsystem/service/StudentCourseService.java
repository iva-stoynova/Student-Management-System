package net.studentmanagementsystem.service;

import java.util.List;

import net.studentmanagementsystem.entity.StudentCourse;

public interface StudentCourseService {
	
	public List<StudentCourse> findByStudentId(Long id);
	
	public StudentCourse findByStudentAndCourseId(Long studentId, Long courseId);
	
	public void deleteByStudentId(Long id);
	
	public void deleteByStudentAndCourseId(Long studentId, Long courseId);
	
	public void save(StudentCourse studentCourseDetails);
}


