package net.studentmanagementsystem.service.impl;

import java.util.List;

import net.studentmanagementsystem.entity.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.studentmanagementsystem.repository.StudentRepository;
import net.studentmanagementsystem.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudentById(Long id) {
		studentRepository.deleteById(id);	
	}

}
