package net.studentmanagementsystem.repository;

import java.util.List;

import net.studentmanagementsystem.entity.StudentCourse;

public interface StudentCourseDao {

public List<StudentCourse> findByStudentId(Long id);
	
	public StudentCourse findByStudentAndCourseId(Long studentId, Long courseId);
	
	public void deleteByStudentId(Long id);
	
	public void deleteByStudentAndCourseId(Long studentId, Long courseId);
	
	public void save(StudentCourse studentCourse);
}
