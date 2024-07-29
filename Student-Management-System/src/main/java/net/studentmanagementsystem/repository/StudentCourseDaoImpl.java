package net.studentmanagementsystem.repository;


import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.studentmanagementsystem.entity.StudentCourse;

@Repository
public class StudentCourseDaoImpl implements StudentCourseDao {
	



	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<StudentCourse> findByStudentId(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query<StudentCourse> query = session.createQuery("from student_courses where studentId=:id", StudentCourse.class);
		query.setParameter("id", id);
		
		try {
			return query.getResultList();
		} catch (Exception exc) {
			return null;
		}
	}

	@Override
	public StudentCourse findByStudentAndCourseId(Long studentId, Long courseId) {
		Session session = entityManager.unwrap(Session.class);
		Query<StudentCourse> query = session
				.createQuery("from student_courses where studentId=:studentId and courseId=:courseId", StudentCourse.class);
		query.setParameter("studentId", studentId);
		query.setParameter("courseId", courseId);
		
		try {
			return query.getSingleResult();
		} catch (Exception exc) {
			return null;
		}
	}

	@Override
	public void deleteByStudentAndCourseId(Long studentId, Long courseId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("delete student_courses where studentId=:studentId and courseId=:courseId");
		query.setParameter("studentId", studentId);
		query.setParameter("courseId", courseId);
		query.executeUpdate();
		
	}

	@Override
	public void save(StudentCourse studentCourseDetails) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(studentCourseDetails);
	}

	@Override
	public void deleteByStudentId(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("delete student_courses where studentId=:studentId");
		query.setParameter("studentId", id);
		query.executeUpdate();
	}

}
