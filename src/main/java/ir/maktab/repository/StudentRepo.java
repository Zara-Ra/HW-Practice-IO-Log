package ir.maktab.repository;

import ir.maktab.data.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

public class StudentRepo {
    private static final StudentRepo courseRepo = new StudentRepo();

    private StudentRepo() {
    }

    public static StudentRepo getInstance() {
        return courseRepo;
    }

    public void save(Student student) {
        EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }

    public void save(EntityManager em, Student student) {
        em.persist(student);
    }

    public Optional<Student> findByName(String name) {
        Student result;
        try {
            EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("FROM Student s WHERE s.name=:name");
            query.setParameter("name", name);
            result = (Student) query.getSingleResult();
            em.getTransaction().commit();
            em.close();
        } catch (
                NoResultException e) {
            result = null;
        }
        return Optional.ofNullable(result);
    }
}
