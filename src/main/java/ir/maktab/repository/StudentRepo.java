package ir.maktab.repository;

import ir.maktab.data.entity.Course;
import ir.maktab.data.entity.Student;

import javax.persistence.EntityManager;

public class StudentRepo {
    private static final StudentRepo courseRepo = new StudentRepo();
    private StudentRepo(){}
    public static StudentRepo getInstance(){
        return courseRepo;
    }

    public void save(Student student){
        EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }
}
