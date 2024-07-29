package net.studentmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.studentmanagementsystem.entity.Course;
import net.studentmanagementsystem.entity.Teacher;
import net.studentmanagementsystem.service.TeacherService;


@Controller
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;


	
	// handler method to handle list students and return mode and view
	@GetMapping("/teachers")
	public String listTeachers(Model model) {
		model.addAttribute("teachers", teacherService.getAllTeachers());
		return "teachers";
	}
	
	@GetMapping("/teachers/new")
	public String createStudentForm(Model model) {
		
		// create student object to hold student form data
		Teacher teacher = new Teacher();
		model.addAttribute("teacher", teacher);
		return "create_teacher";
		
	}
	
	@PostMapping("/teachers")
	public String saveStudent(@ModelAttribute("teacher") Teacher teacher) {
		teacherService.saveTeacher(teacher);
		return "redirect:/teachers";
	}
	
	@GetMapping("/teachers/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("teacher", teacherService.getTeacherById(id));
		return "edit_teacher";
	}

	@PostMapping("/teachers/{id}")
	public String updateTeacher(@PathVariable Long id, @ModelAttribute("teacher") Teacher teacher,	Model model) {
		
		// get student from database by id
		Teacher existingTeacher = teacherService.getTeacherById(id);
		existingTeacher.setId(id);
		existingTeacher.setFirstName(teacher.getFirstName());
		existingTeacher.setLastName(teacher.getLastName());
		existingTeacher.setAge(teacher.getAge());
		
		// save updated student object
		teacherService.updateTeacher(existingTeacher);
		return "redirect:/teachers";		
	}
	
	// handler method to handle delete student request	
	@GetMapping("/teachers/{id}")
	public String deleteStudent(@PathVariable Long id) {
		teacherService.deleteTeacherById(id);
		return "redirect:/teachers";
	}
		
	@GetMapping("/teachers/{id}/courses")
	public String showTeacherCourses(@PathVariable("id") Long id, Model theModel) {
		Teacher teacher = teacherService.getTeacherById(id);
		List<Course> courses = teacher.getCourses();
		
		theModel.addAttribute("teacher", teacher);
		theModel.addAttribute("courses", courses);		
		return "teacher/teacher-courses";
	}
}