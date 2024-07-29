package net.studentmanagementsystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.studentmanagementsystem.entity.StudentCourse;
import net.studentmanagementsystem.repository.StudentCourseDao;
import net.studentmanagementsystem.repository.StudentCourseRepository;
import net.studentmanagementsystem.service.StudentCourseService;

@Service
public class StudentCourseServiceImpl implements StudentCourseService{

	
	
	@Autowired
	private StudentCourseDao studentCourseDao;
	
	@Override
	@Transactional
	public List<StudentCourse> findByStudentId(Long id) {
		return studentCourseDao.findByStudentId(id);
	}

	@Override
	@Transactional
	public StudentCourse findByStudentAndCourseId(Long studentId, Long courseId) {
		return studentCourseDao.findByStudentAndCourseId(studentId, courseId);
	}

	@Override
	@Transactional
	public void deleteByStudentAndCourseId(Long studentId, Long courseId) {
		studentCourseDao.deleteByStudentAndCourseId(studentId, courseId);
	}

	@Override
	@Transactional
	public void save(StudentCourse studentCourseDetails) {
		studentCourseDao.save(studentCourseDetails);
		
	}

	@Override
	@Transactional
	public void deleteByStudentId(Long id) {
		studentCourseDao.deleteByStudentId(id);
	}
}
