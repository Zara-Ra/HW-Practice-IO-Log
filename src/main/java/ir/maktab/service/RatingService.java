package ir.maktab.service;

import ir.maktab.data.entity.Course;
import ir.maktab.data.entity.Student;
import ir.maktab.data.entity.StudentCourseRating;
import ir.maktab.repository.CourseRepo;
import ir.maktab.repository.StudentCourseRatingRepo;
import ir.maktab.repository.StudentRepo;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RatingService {
    private static final RatingService ratingService = new RatingService();

    private RatingService() {
    }

    public static RatingService getInstance() {
        return ratingService;
    }

    private StudentRepo studentRepo = StudentRepo.getInstance();
    private CourseRepo courseRepo = CourseRepo.getInstance();
    private StudentCourseRatingRepo studentCourseRatingRepo = StudentCourseRatingRepo.getInstance();

    public void addRating(StudentCourseRating rating) {//todo should i open an em here? because if student not saved dont save other, what about unique constraint
        studentRepo.save(rating.getStudent());
        courseRepo.save(rating.getCourse());
        studentCourseRatingRepo.save(rating);
    }

    public void readRecords() {//todo main args: pass a file name
        try(RandomAccessFile input = new RandomAccessFile("rating.txt","r")){
            String line = input.readLine();
            while (line != null){
                System.out.println(line);
                String[] split = line.split(",");

                Course course = new Course();
                course.setName(split[0]);

                Student student = new Student();
                student.setName(split[1]);

                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(split[2]);
                float rateNum = Float.parseFloat(split[3]);
                String comment = split[4];
                StudentCourseRating rating = new StudentCourseRating(student,course,date,rateNum,comment) ;
                addRating(rating);

                line = input.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
