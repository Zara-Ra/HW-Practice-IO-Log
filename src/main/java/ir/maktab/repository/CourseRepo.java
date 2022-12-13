package ir.maktab.repository;

import ir.maktab.data.entity.Course;

import javax.persistence.EntityManager;

public class CourseRepo {
    private static final CourseRepo courseRepo = new CourseRepo();
    private CourseRepo(){}
    public static CourseRepo getInstance(){
        return courseRepo;
    }

    public void save(Course course){
        EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
        em.close();
    }
}
