package net.studentmanagementsystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.studentmanagementsystem.entity.Course;
import net.studentmanagementsystem.entity.Teacher;
import net.studentmanagementsystem.service.CourseService;
import net.studentmanagementsystem.service.TeacherService;

@Controller
@RequestMapping("/courses")
public class CourseController {

	
	    @Autowired
	    private CourseService courseService;
	    
	    @Autowired
	    private TeacherService teacherService;
	    
	    @GetMapping()
		public String listCourses(Model model) {
			model.addAttribute("courses", courseService.getAllCourses());
			return "courses";
		}

	    @GetMapping("/new")
		public String addCourse(Model theModel) {
			//add course form has a select teacher field where all teachers registered are showed as drop-down list
			List<Teacher> teachers = teacherService.getAllTeachers(); 
			
			theModel.addAttribute("course", new Course());
			theModel.addAttribute("teachers", teachers);
			
			return "admin/course-form";
		}
		
		@PostMapping("/saveCourse")
		public String saveCourse(@Valid @ModelAttribute("course") Course theCourse, 
				BindingResult theBindingResult, @RequestParam("teacherId") Long teacherId, Model theModel) {
			
			if (theBindingResult.hasErrors()) { //course form has data validation rules. If fields are not properly filled out, form is showed again
				List<Teacher> teachers = teacherService.getAllTeachers();
				theModel.addAttribute("teachers", teachers);
				return "admin/course-form";
			}
			
			theCourse.setTeacher(teacherService.getTeacherById(teacherId)); //setTeacher method also sets the teacher's course as this	
			courseService.saveCourse(theCourse);
		
			return "redirect:/courses";
		}
		
	    @GetMapping("/{id}")
	    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
	        Course course = courseService.getCourseById(id);
	        if (course != null) {
	            return ResponseEntity.ok(course);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @GetMapping("/edit/{id}")
		public String editCourseForm(@PathVariable Long id, Model model) {
	    	List<Teacher> teachers = teacherService.getAllTeachers();
	    	model.addAttribute("teachers", teachers);
	    	
	    	model.addAttribute("course",courseService.getCourseById(id));
			return "edit_course";
		}
		
	    @PostMapping("/{id}")
		public String updateTeacher(@PathVariable Long id, @ModelAttribute("course") Course theCourse, @RequestParam("teacherId") Long teacherId, Model model) {
	    	List<Teacher> teachers = teacherService.getAllTeachers(); 
	    		    	
			// get student from database by id
			Course existingCourse = courseService.getCourseById(id);
			existingCourse.setId(id);
			existingCourse.setCode(theCourse.getCode());
			existingCourse.setName(theCourse.getName());
			existingCourse.setType(theCourse.getType());
			existingCourse.setAcademicYear(theCourse.getAcademicYear());
			
			Teacher teacher = teacherService.getTeacherById(teacherId);
			existingCourse.setTeacher(teacher);
		
			courseService.updateCourse(existingCourse);
			return "redirect:/courses";		
		}			 
}
