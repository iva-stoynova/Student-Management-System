package net.studentmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.studentmanagementsystem.entity.StudentCourse;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long>{

	/*public List<StudentCourse> findByStudentId(Long id);
	
	public StudentCourse findByStudentAndCourseId(Long studentId, Long courseId);
	
	public void deleteByStudentId(Long id);
	
	public void deleteByStudentAndCourseId(Long studentId, Long courseId);
	
	public StudentCourse saveStudentCourse(StudentCourse studentCourse);*/
	
}
