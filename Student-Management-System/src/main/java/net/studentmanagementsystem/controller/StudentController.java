package net.studentmanagementsystem.controller;

import net.studentmanagementsystem.entity.Course;
import net.studentmanagementsystem.entity.Student;
import net.studentmanagementsystem.service.CourseService;
import net.studentmanagementsystem.service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;

	
	// handler method to handle list students and return mode and view
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		
		// create student object to hold student form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
		
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
			@ModelAttribute("student") Student student,
			Model model) {
		
		// get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setAge(student.getAge());
		existingStudent.setAcademicYear(student.getAcademicYear());
		existingStudent.setAcademicDivision(student.getAcademicDivision());
		
		// save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/students";		
	}
	
	// handler method to handle delete student request	
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}	
	
	@GetMapping("/students/{studentId}/courses")
	public String showStudentPanel(@PathVariable("studentId") long studentId, Model theModel) {
		Student student = studentService.getStudentById(studentId); //accessing student logged in
		List<Course> courses = student.getCourses();
		
		theModel.addAttribute("student", student);
		theModel.addAttribute("courses", courses);
		return "student/student-courses";
	}
		
	@GetMapping("/{studentId}/courses/{courseId}")
	public String showStudentCourse(@PathVariable("studentId") long studentId, @PathVariable("courseId") Long courseId, Model theModel) {
		Student student = studentService.getStudentById(studentId);
		List<Course> courses = student.getCourses();
		Course course = courseService.getCourseById(courseId);
		
	
		theModel.addAttribute("course", course);
		theModel.addAttribute("courses", courses);
		theModel.addAttribute("student", student);
		
		return "student/student-course-detail";
	}
}
