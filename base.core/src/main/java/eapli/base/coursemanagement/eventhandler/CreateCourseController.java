package eapli.base.coursemanagement.eventhandler;

import eapli.base.coursemanagement.domain.Course;
import eapli.base.coursemanagement.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.coursemanagement.domain.*;

public class CreateCourseController {
    CourseRepository courseRepository = PersistenceContext.repositories().course();

    public void createCourse(Name name, Capacity capacity, Description description, Title title, Code code) {
        Course newCourse = new Course(name, capacity, description, title, code);
        courseRepository.save(newCourse);
    }

    public Iterable<Course> listCourses() {
        return courseRepository.findAll();
    }
}

