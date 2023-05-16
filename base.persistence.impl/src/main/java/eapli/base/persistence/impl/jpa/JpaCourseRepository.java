package eapli.base.persistence.impl.jpa;

import eapli.base.coursemanagement.domain.Course;
import eapli.base.coursemanagement.repositories.CourseRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaCourseRepository extends JpaAutoTxRepository<Course, Long, Long> implements CourseRepository {

    public JpaCourseRepository(TransactionalContext autoTx) {
        super(autoTx, "id");
    }
    public JpaCourseRepository(String puname) {
        super(puname, "eapli.base.course");
    }
    @Override
    public Optional<Course> findByCode(String code) {
        final Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        final TypedQuery<Course> query = createQuery("SELECT c FROM Course c WHERE c.code = :code", Course.class);
        query.setParameter("code", code);
        return query.getResultStream().findFirst();
    }
    @Override
    public Iterable<Course> findAllActive() {
        final TypedQuery<Course> query = createQuery("SELECT c FROM Course c WHERE c.state = 'ACTIVE'", Course.class);
        return query.getResultList();
    }
}
