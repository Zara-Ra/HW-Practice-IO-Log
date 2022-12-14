package ir.maktab.repository;

import ir.maktab.data.entity.StudentCourseRating;

import javax.persistence.EntityManager;

public class StudentCourseRatingRepo {
    private static final StudentCourseRatingRepo courseRepo = new StudentCourseRatingRepo();

    private StudentCourseRatingRepo() {
    }

    public static StudentCourseRatingRepo getInstance() {
        return courseRepo;
    }

    public void save(EntityManager em, StudentCourseRating rating) {
        em.merge(rating);
    }
}
