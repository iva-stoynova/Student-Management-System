package net.studentmanagementsystem.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import net.studentmanagementsystem.entity.GroupCategory;
import net.studentmanagementsystem.entity.Course;
import net.studentmanagementsystem.entity.MyModel;
import net.studentmanagementsystem.entity.Student;
import net.studentmanagementsystem.entity.StudentCourse;
import net.studentmanagementsystem.entity.Teacher;
import net.studentmanagementsystem.entity.TypeCourse;
import net.studentmanagementsystem.service.CourseService;
import net.studentmanagementsystem.service.StudentCourseService;
import net.studentmanagementsystem.service.StudentService;
import net.studentmanagementsystem.service.TeacherService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentService studentService;
		
	@Autowired
	private StudentCourseService studentCourseService;
		
	private int teacherDeleteErrorValue; //used for deleting teacher, 0 means the teacher has not any assigned courses, 1 means it has
	
	
	
	
	
	@GetMapping("/adminPanel")
	public String showAdminPanel() {
		
		return "admin/admin-panel";
	}
	
	@GetMapping()
	public String listAdminPanel() {
		
		return "admin";
	}
		
	@GetMapping("/adminInfo")
	public String showAdminInfo(Model theModel) {
		List<Course> allCourses = courseService.getAllCourses();	
		String[] types = {"Main", "Secondary"};
		
		List<String> primes = allCourses.stream().map(Course::getType).collect(Collectors.toList());		
		Stream<String> primeStream = primes.stream(); 		 
		
		Predicate<String> test1 = k->k.equals(types[0]);  
		long count1 = primeStream.filter(test1).count(); 
		 
		Predicate<String> test2 = l->l.equals(types[1]);  
		long count2 = primes.stream().filter(test2).count();  
		
		
		int courseSize = allCourses.size();
		theModel.addAttribute("courseSize", courseSize);
		int studentSize = studentService.getAllStudents().size();
		theModel.addAttribute("studentSize", studentSize);
		int teacherSize = teacherService.getAllTeachers().size();
		theModel.addAttribute("teacherSize", teacherSize);
		

		theModel.addAttribute("mainTypeCount", count1);
		theModel.addAttribute("secondaryTypeCount", count2);
		
		return "admin/admin-info";
	}
		
	@GetMapping("/courses")
	public String showCourses(Model theModel) {
		theModel.addAttribute("courses", courseService.getAllCourses());	
		
		return "admin/course-list";
	}
	
	@GetMapping("/addCourse")
	public String addCourse(Model theModel) {
		//add course form has a select teacher field where all teachers registered are showed as drop-down list
		List<Teacher> teachers = teacherService.getAllTeachers(); 
		
		theModel.addAttribute("course", new Course());
		theModel.addAttribute("teachers", teachers);
		
		return "admin/course-form";
	}
	
	@PostMapping("/saveCourse")
	public String saveCourse(@Valid @ModelAttribute("course") Course theCourse,	BindingResult theBindingResult, @RequestParam("teacherId") Long teacherId, @RequestParam("acadYear") String acadYear, Model theModel) {
		
		if (theBindingResult.hasErrors()) { //course form has data validation rules. If fields are not properly filled out, form is showed again
			List<Teacher> teachers = teacherService.getAllTeachers();
			theModel.addAttribute("teachers", teachers);
			return "admin/course-form";
		}
		theCourse.setAcademicYear(acadYear);
		theCourse.setTeacher(teacherService.getTeacherById(teacherId)); //setTeacher method also sets the teacher's course as this	
		courseService.saveCourse(theCourse);
		
		return "redirect:/admin/adminPanel"; 
	}
	
	@GetMapping("/courses/delete")
	public String deleteCourse(@RequestParam("courseId") Long courseId) {		
		Course course = courseService.getCourseById(courseId);
		List<Student> students = course.getStudents();
		
		for(Student student : students) {
			StudentCourse scd = studentCourseService.findByStudentAndCourseId(student.getId(), courseId);
			studentCourseService.deleteByStudentAndCourseId(student.getId(), courseId);
		}
		
		courseService.deleteCourseById(courseId);
		return "redirect:/admin/courses";
	}
	
	
	//2
	@GetMapping("/students")
	public String showStudentList(Model theModel) {
		theModel.addAttribute("students", studentService.getAllStudents());
		
		return "admin/student-list"; 
	}
	
	@RequestMapping("/students/delete")
	public String deleteStudent(@RequestParam("studentId") Long studentId) {
		List<StudentCourse> list = studentCourseService.findByStudentId(studentId);
		for(StudentCourse scd : list) { //deleting the student before deleting the student			
			studentCourseService.deleteByStudentId(studentId);			
		}
		studentService.deleteStudentById(studentId);
		
		return "redirect:/admin/students";
	}
	
	@GetMapping("/students/{studentId}/courses")
	public String editCoursesForStudent(@PathVariable("studentId") Long studentId, Model theModel) {
		Student student = studentService.getStudentById(studentId);
		List<Course> courses = student.getCourses();
		
		theModel.addAttribute("student", student);
		theModel.addAttribute("courses", courses);
		
		return "admin/student-course-list";
	}
	
	@GetMapping("/students/{studentId}/addCourse")
	public String addCourseToStudent(@PathVariable("studentId") Long studentId, Model theModel) {
		Student student = studentService.getStudentById(studentId);
		
		String year = student.getAcademicYear();
		
		List<Course> allCourses = courseService.getAllCourses();
		
		List<Course> courses =allCourses.stream().filter(e -> e.getAcademicYear().contains(year)).collect(Collectors.toList());
				
		for(int i = 0; i < courses.size(); i++) { //finding the courses that the current student has not enrolled yet
			if(student.getCourses().contains(courses.get(i))) {
				courses.remove(i);
				i--;
			}
		}
		theModel.addAttribute("student", student);
		theModel.addAttribute("courses", courses); //unenrolled courses are displayed as drop-down list
		theModel.addAttribute("listSize", courses.size());
		return "admin/add-course";
	}
	
	@RequestMapping("/students/{studentId}/addCourse/save")
	public String saveCourseToStudent(@PathVariable("studentId") Long studentId, @RequestParam("courseId") Long courseId) {
		
		StudentCourse sc = new StudentCourse(studentId, courseId);
		studentCourseService.save(sc);
			
		return "redirect:/admin/students/" + studentId + "/courses";
	}
	
	
	@GetMapping("/students/{studentId}/courses/delete/{courseId}")
	public String deleteCourseFromStudent(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
		StudentCourse scd = studentCourseService.findByStudentAndCourseId(studentId, courseId);
	
		//operations for removing the student from the course
		studentCourseService.deleteByStudentAndCourseId(studentId, courseId);
		
		return "redirect:/admin/students/" + studentId + "/courses";
	}	
	
	
	//3
	@GetMapping("/courses/{courseId}/students")
	public String showSudents(@PathVariable("courseId") Long courseId, Model theModel) {		
		Course course = courseService.getCourseById(courseId);
		List<Student> students = course.getStudents();
		Teacher teacher = course.getTeacher();
		theModel.addAttribute("students", students);
		theModel.addAttribute("course", course);
		theModel.addAttribute("teacher", teacher);
		return "admin/course-student-list";
	}
	
	@GetMapping("/courses/{courseId}/students/addStudent")
	public String addStudentToCourse(@PathVariable("courseId") Long courseId, Model theModel) {
		Course course = courseService.getCourseById(courseId);
		List<Student> students = studentService.getAllStudents();
		
		for(int i = 0; i < students.size(); i++) { 
			if(course.getStudents().contains(students.get(i))) {
				students.remove(students.get(i));
				i--;
			}
		}
		theModel.addAttribute("students", students); //all students who are not enrolled to the current course yet
		theModel.addAttribute("course", course);
		theModel.addAttribute("listSize", students.size());
		return "admin/add-student";
		
	}
	
	@RequestMapping("/courses/{courseId}/students/addStudent/save")
	public String saveStudentToCourse(@RequestParam("studentId") Long studentId, @PathVariable("courseId") int courseId) {
		
		StudentCourse sc = new StudentCourse(studentId, courseId); 
		studentCourseService.save(sc);
		
		return "redirect:/admin/courses/" + courseId + "/students";
	}
	
	@GetMapping("/courses/{courseId}/students/delete")
	public String deleteStudentFromCourse(@PathVariable("courseId") Long courseId, @RequestParam("studentId") Long studentId) {
		StudentCourse scd = studentCourseService.findByStudentAndCourseId(studentId, courseId);
		
		studentCourseService.deleteByStudentAndCourseId(studentId, courseId);
		
		return "redirect:/admin/courses/" + courseId + "/students";
	}
	
	
	//4
	@GetMapping("/teachers")
	public String showTeacherList(Model theModel) {
		theModel.addAttribute("teachers", teacherService.getAllTeachers());
		theModel.addAttribute("error", teacherDeleteErrorValue); 
		teacherDeleteErrorValue = 0; //0 means the teacher has not any assigned courses, 1 means it has
		return "admin/teacher-list";
	}
	
	@GetMapping("/teachers/delete")
	public String deleteTeacher(@RequestParam("teacherId") Long teacherId) {
		Teacher teacher = teacherService.getTeacherById(teacherId);
		if(teacher.getCourses().size() == 0) { //if the teacher has courses assigned, the teacher cannot be deleted
			teacherService.deleteTeacherById(teacherId);
			teacherDeleteErrorValue = 0;
		} else {
			teacherDeleteErrorValue = 1; 
		}
		
		return "redirect:/admin/teachers";
	}
	
	
	//5 •	which students participate in specific course
	@RequestMapping("/showSearchPage")
	public String reportPage(Model model, @ModelAttribute("task") Course selectedOption) {
	   
		List<Course> allCourses = courseService.getAllCourses();
		
		model.addAttribute("tasks", allCourses);
	    return "report/reportPage";
	}

	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String search(@Valid   @ModelAttribute("task") Course course, Model model) {	
		if(course != null && course.getStudents() != null) {
			List<Student> students = course.getStudents();
			model.addAttribute("students", students);
			model.addAttribute("course", course);
			model.addAttribute("courseId", course.getId());
			Teacher teacher = course.getTeacher();
			model.addAttribute("teacher", teacher);
			
			return "report/course-student-list-report";
		} else {			
			return "redirect:/admin/showSearchPage"; 
		}
	}
	
	
	//6 •	which students participate in specific group
	@RequestMapping("/showSearchPage2")	
	public String reportPage2(Model model, @ModelAttribute("myModel") MyModel myModel) {

		GroupCategory c1 = new GroupCategory("1", "");
		GroupCategory c2 = new GroupCategory("1", "A");
		GroupCategory c3 = new GroupCategory("1", "B");
		GroupCategory c4 = new GroupCategory("1", "C");
		
		GroupCategory c5 = new GroupCategory("2", "");
		GroupCategory c6 = new GroupCategory("2", "A");
		GroupCategory c7 = new GroupCategory("2", "B");
		GroupCategory c8 = new GroupCategory("2", "C");
		
		GroupCategory c9 = new GroupCategory("3", "");
		GroupCategory c10 = new GroupCategory("3", "A");
		GroupCategory c11 = new GroupCategory("3", "B");
		GroupCategory c12 = new GroupCategory("3", "C");
		
		GroupCategory c13 = new GroupCategory("4", "");
		GroupCategory c14 = new GroupCategory("4", "A");
		GroupCategory c15 = new GroupCategory("4", "B");
		GroupCategory c16 = new GroupCategory("4", "C");
		
		GroupCategory c17 = new GroupCategory("5", "");
		GroupCategory c18 = new GroupCategory("5", "A");
		GroupCategory c19 = new GroupCategory("5", "B");
		GroupCategory c20 = new GroupCategory("5", "C");
	
		
		List<GroupCategory> categories = new ArrayList<GroupCategory>();
		categories.add(c1);
		categories.add(c2);
		categories.add(c3);
		categories.add(c4);
		categories.add(c5);
		categories.add(c6);
		categories.add(c7);
		categories.add(c8);
		categories.add(c9);
		categories.add(c10);
		categories.add(c11);
		categories.add(c12);
		categories.add(c13);
		categories.add(c14);
		categories.add(c15);
		categories.add(c16);
		categories.add(c17);
		categories.add(c18);
		categories.add(c19);
		categories.add(c20);
		
		myModel.setCategoryList(categories);
	    model.addAttribute("myModel", myModel);
		
			    
	    return "report/reportPage2"; 
	}
	
	@RequestMapping(value="/search2", method = RequestMethod.POST)	
	public String search2(@RequestParam("name") String name,@Valid @ModelAttribute("myModel") MyModel myModel, Model model) {//@RequestParam("task") Course course) {
				
		List<Student> students = new ArrayList<Student>();
			
		String paramName = name;
		
		String[] parts = paramName.split(":");		
		final String partYear = parts[0];
		
		List<Student> allStudent = studentService.getAllStudents();
		if(parts.length > 1) {
			final String partGroup = parts[1];
			List<Student> coursesByYear = allStudent.stream().filter(e -> e.getAcademicYear().contains(partYear)).collect(Collectors.toList());
			students = coursesByYear.stream().filter(e -> e.getAcademicDivision().contains(partGroup)).collect(Collectors.toList());
		} else {
			students = allStudent.stream().filter(e -> e.getAcademicYear().contains(partYear)).collect(Collectors.toList());
		}
		
		if(students != null && students.size() > 0) {
			model.addAttribute("students", students);			
			return "report/group-student-list-report";
		} else {
			
			return "redirect:/admin/showSearchPage2"; 
		}	 
	}
	
	//7 •	find all teachers and students for specific group and course
	@RequestMapping("/showSearchPage3")
	public String reportPage3(Model model, @ModelAttribute("myModel") MyModel myModel) {
	   
		List<Course> allCourses = courseService.getAllCourses();
		
		GroupCategory c1 = new GroupCategory("1", "");
		GroupCategory c2 = new GroupCategory("1", "A");
		GroupCategory c3 = new GroupCategory("1", "B");
		GroupCategory c4 = new GroupCategory("1", "C");
		
		GroupCategory c5 = new GroupCategory("2", "");
		GroupCategory c6 = new GroupCategory("2", "A");
		GroupCategory c7 = new GroupCategory("2", "B");
		GroupCategory c8 = new GroupCategory("2", "C");
		
		GroupCategory c9  = new GroupCategory("3", "");
		GroupCategory c10 = new GroupCategory("3", "A");
		GroupCategory c11 = new GroupCategory("3", "B");
		GroupCategory c12 = new GroupCategory("3", "C");
		
		GroupCategory c13 = new GroupCategory("4", "");
		GroupCategory c14 = new GroupCategory("4", "A");
		GroupCategory c15 = new GroupCategory("4", "B");
		GroupCategory c16 = new GroupCategory("4", "C");
		
		GroupCategory c17 = new GroupCategory("5", "");
		GroupCategory c18 = new GroupCategory("5", "A");
		GroupCategory c19 = new GroupCategory("5", "B");
		GroupCategory c20 = new GroupCategory("5", "C");
	
		
		List<GroupCategory> categories = new ArrayList<GroupCategory>();
		categories.add(c1);
		categories.add(c2);
		categories.add(c3);
		categories.add(c4);
		categories.add(c5);
		categories.add(c6);
		categories.add(c7);
		categories.add(c8);
		categories.add(c9);
		categories.add(c10);
		categories.add(c11);
		categories.add(c12);
		categories.add(c13);
		categories.add(c14);
		categories.add(c15);
		categories.add(c16);
		categories.add(c17);
		categories.add(c18);
		categories.add(c19);
		categories.add(c20);
		
		List<TypeCourse> typeCourses = new ArrayList<TypeCourse>();
		TypeCourse t1 = new TypeCourse("1", "Main");
		TypeCourse t2 = new TypeCourse("2", "Secondary");
		typeCourses.add(t1);
		typeCourses.add(t2);
		
		myModel.setCategoryList(categories);		
		myModel.setTypeList(typeCourses);
		
		
		model.addAttribute("myModel", myModel);
	    return "report/reportPage3";
	}
	
	@RequestMapping(value="/search3", method = RequestMethod.POST)	
	public String search3(@RequestParam("name") String name, @RequestParam("courseType") String courseType, @Valid @ModelAttribute("myModel") MyModel myModel, Model model) {
				
		List<Student> students = new ArrayList<Student>();
		List<Course> course = new ArrayList<Course>();
				
		String groupCategoryName = name;
		
		List<Course> allCourse = courseService.getAllCourses();
		if(allCourse != null && allCourse.size() > 0) {
			if(groupCategoryName == null || groupCategoryName.equals("")) {
				if(courseType != null && !courseType.equals("")) {
					course = allCourse.stream().filter(e -> e.getType().contains(courseType)).collect(Collectors.toList());
				}else {
					course = allCourse;
				}
			} else {			
				String[] parts = groupCategoryName.split(":");	
				final String partYear = parts[0];			
					
				if(courseType != null && !courseType.equals("")) {					
					List<Course> courseByType = allCourse.stream().filter(e -> e.getType().contains(courseType)).collect(Collectors.toList());
					course = courseByType.stream().filter(e -> e.getAcademicYear().contains(partYear)).collect(Collectors.toList());
							
				} else {				
					course = allCourse.stream().filter(e -> e.getAcademicYear().contains(partYear)).collect(Collectors.toList());			
				}	
			}
		}
		List<Student> allStudent = studentService.getAllStudents();
		for(int i = 0;i<course.size();i++) {
			Course c1 = course.get(i);
			for(int j = 0; j<allStudent.size();j++) {
				List<Course> listStCourse = allStudent.get(j).getCourses();
				
				if(listStCourse.contains(c1)) {
					students.add(allStudent.get(j));
				}
				
			}
		}
		
		if(groupCategoryName != null && !groupCategoryName.equals("")) {
			String[] parts = groupCategoryName.split(":");
			final String partYear = parts[0];			
			if(parts.length > 1) {			
				final String partGroup = parts[1];
				List<Student> coursesByYear = students.stream().filter(e -> e.getAcademicYear().contains(partYear)).collect(Collectors.toList());
				students = coursesByYear.stream().filter(e -> e.getAcademicDivision().contains(partGroup)).collect(Collectors.toList());
			} else {
				students = students.stream().filter(e -> e.getAcademicYear().contains(partYear)).collect(Collectors.toList());
			}
		}
				
		if(students != null && students.size() > 0) {
			List<Student> personsWithoutDuplicates = students.stream()
					 .distinct()
					 .collect(Collectors.toList());
			
		
			model.addAttribute("students", personsWithoutDuplicates);
			model.addAttribute("course", course);					
			return "report/teacher-course-group-student-report";
		} else {
			
			return "redirect:/admin/showSearchPage3"; 
		}	
	}
	
	//8 •	find all students older than specific age and participate in specific course
	@RequestMapping("/showSearchPage4")
	public String reportPage4(Model model, @ModelAttribute("myModel") MyModel myModel) {
	   
		List<Course> allCourses = courseService.getAllCourses();
		myModel.setCourseSearched(allCourses);
		model.addAttribute("myModel", myModel);
	    return "report/reportPage4";
	}
	
	
	@RequestMapping(value="/search4", method = RequestMethod.POST)
	public String search4(@RequestParam("age") String age, @Valid   @ModelAttribute("myModel") MyModel myModel, Model model) {	
		
		String paramAge = age;		
		
		if(paramAge != null && !paramAge.equals("")) {
			paramAge = paramAge.replaceAll("\\D", "");
		} else {
			paramAge = "0";
		}
		int ageReg = paramAge == "0" ? 0 : Integer.parseInt(age);
		
		
		List<Course> course = courseService.getAllCourses();
		List<Student> students = new ArrayList<Student>();	
			
		for(int i = 0;i<course.size();i++) {
			List<Student> stList = course.get(i).getStudents();
			for(int j = 0; j<stList.size();j++) {
				if(stList.get(j).getAge() > ageReg) {
					students.add(stList.get(j));
				}
				
			}
		}
		
		if(students != null && students.size() > 0) {
			model.addAttribute("students", students);
			model.addAttribute("course", course);
			
			return "report/age-course-student-list-report";
		} else {			
			return "redirect:/admin/showSearchPage4"; 
		}
	}
	
}
