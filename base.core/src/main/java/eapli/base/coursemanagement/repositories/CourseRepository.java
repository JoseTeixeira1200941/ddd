package eapli.base.coursemanagement.repositories;

import eapli.base.coursemanagement.domain.Course;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface CourseRepository extends DomainRepository<Long, Course> {

    Optional<Course> findByCode(String code);

    Iterable<Course> findAllActive();
}
