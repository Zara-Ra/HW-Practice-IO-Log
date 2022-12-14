package ir.maktab.repository;

import ir.maktab.data.entity.Course;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

public class CourseRepo {
    private static final CourseRepo courseRepo = new CourseRepo();

    private CourseRepo() {
    }

    public static CourseRepo getInstance() {
        return courseRepo;
    }

    public void save(Course course) {
        EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
        em.close();
    }

    public void save(EntityManager em, Course course) {
        em.persist(course);
    }

    public Optional<Course> findByName(String name) {
        Course result;
        try {
            EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("FROM Course c WHERE c.name=:name");
            query.setParameter("name", name);
            result = (Course) query.getSingleResult();
            em.getTransaction().commit();
            em.close();
        } catch (
                NoResultException e) {
            result = null;
        }
        return Optional.ofNullable(result);
    }
}
