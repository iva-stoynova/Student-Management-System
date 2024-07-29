package net.studentmanagementsystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.studentmanagementsystem.entity.Teacher;
import net.studentmanagementsystem.repository.TeacherRepository;
import net.studentmanagementsystem.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	private TeacherRepository teacherRepository;
	

	@Override
	public List<Teacher> getAllTeachers() {
		return teacherRepository.findAll();
	}

	@Override
	public Teacher saveTeacher(Teacher teacher) {
		return teacherRepository.save(teacher);
	}

	@Override
	public Teacher getTeacherById(Long id) {
		return teacherRepository.findById(id).get();
	}

	@Override
	public Teacher updateTeacher(Teacher teacher) {
		return teacherRepository.save(teacher);
	}

	@Override
	public void deleteTeacherById(Long id) {
		teacherRepository.deleteById(id);	
	}
}