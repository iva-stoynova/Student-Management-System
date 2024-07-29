package net.studentmanagementsystem.service;

import java.util.List;
import net.studentmanagementsystem.entity.Teacher;

public interface TeacherService {

	List<Teacher> getAllTeachers();
	
	Teacher saveTeacher(Teacher student);
	
	Teacher getTeacherById(Long id);
	
	Teacher updateTeacher(Teacher student);
	
	void deleteTeacherById(Long id);
}
