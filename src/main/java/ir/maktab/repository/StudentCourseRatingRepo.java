package ir.maktab.repository;

import ir.maktab.data.entity.Course;
import ir.maktab.data.entity.StudentCourseRating;

import javax.persistence.EntityManager;

public class StudentCourseRatingRepo {
    private static final StudentCourseRatingRepo courseRepo = new StudentCourseRatingRepo();
    private StudentCourseRatingRepo(){}
    public static StudentCourseRatingRepo getInstance(){
        return courseRepo;
    }

    public void save(StudentCourseRating rating){
        EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(rating);
        em.getTransaction().commit();
        em.close();
    }
}
