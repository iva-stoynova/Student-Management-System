package net.studentmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.studentmanagementsystem.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

}

