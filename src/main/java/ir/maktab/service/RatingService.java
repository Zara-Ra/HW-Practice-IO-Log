package ir.maktab.service;

import ir.maktab.data.entity.StudentCourseRating;
import ir.maktab.repository.CourseRepo;
import ir.maktab.repository.StudentCourseRatingRepo;
import ir.maktab.repository.StudentRepo;

public class RatingService {
    private RatingService ratingService = new RatingService();
    private  RatingService(){}
    public RatingService getInstance(){
        return ratingService;
    }
    private StudentRepo studentRepo = StudentRepo.getInstance();
    private CourseRepo courseRepo = CourseRepo.getInstance();
    private StudentCourseRatingRepo studentCourseRatingRepo = StudentCourseRatingRepo.getInstance();
    public void addRating(StudentCourseRating rating){//todo should i open an em here? because if student not saved dont save other, what about unique constraint
        studentRepo.save(rating.getStudent());
        courseRepo.save(rating.getCourse());
        studentCourseRatingRepo.save(rating);
    }
    public void readRecords(){//todo main args: pass a file name

    }
}
