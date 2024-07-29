package net.studentmanagementsystem;

import net.studentmanagementsystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.studentmanagementsystem.repository.StudentRepository;

@SpringBootApplication
public class StudentManagementSystemApplication { //implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}

}
