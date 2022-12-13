package ir.maktab.service;

import ir.maktab.data.entity.Course;
import ir.maktab.data.entity.Student;
import ir.maktab.data.entity.StudentCourseRating;
import ir.maktab.repository.CourseRepo;
import ir.maktab.repository.StudentCourseRatingRepo;
import ir.maktab.repository.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class RatingService {
    private static final RatingService ratingService = new RatingService();

    private RatingService() {
    }

    public static RatingService getInstance() {
        return ratingService;
    }

    private final StudentRepo studentRepo = StudentRepo.getInstance();
    private final CourseRepo courseRepo = CourseRepo.getInstance();
    private final StudentCourseRatingRepo studentCourseRatingRepo = StudentCourseRatingRepo.getInstance();
    private Logger fileLogger = LoggerFactory.getLogger(RatingService.class);

    public void addStudentCourseRating(StudentCourseRating rating) {//todo should i open an em here? because if student not saved dont save other
        Optional<Student> optionalStudent = studentRepo.findByName(rating.getStudent().getName());
        optionalStudent.ifPresentOrElse(rating::setStudent, () -> studentRepo.save(rating.getStudent()));
        fileLogger.info("Student Added To Database");

        Optional<Course> optionalCourse = courseRepo.findByName(rating.getCourse().getName());
        optionalCourse.ifPresentOrElse(rating::setCourse, () -> courseRepo.save(rating.getCourse()));
        fileLogger.info("Course Added To Database");

        studentCourseRatingRepo.save(rating);
    }

    public void readRecords() {//todo main args: pass a file name
        try (RandomAccessFile input = new RandomAccessFile("rating.txt", "r")) {
            fileLogger.info("Input File Opened Successfully");
            String line = input.readLine();
            int lineNumber = 1;
            while (line != null) {
                fileLogger.debug("Line number {} Read",lineNumber);
                String[] split = line.split(",");

                Course course = new Course();
                course.setName(split[0]);

                Student student = new Student();
                student.setName(split[1]);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(split[2]);
                float rateNum = Float.parseFloat(split[3]);
                String comment = split[4];
                StudentCourseRating rating = new StudentCourseRating(student, course, date, rateNum, comment);
                fileLogger.debug("Line number {} Parsed",lineNumber);

                addStudentCourseRating(rating);
                fileLogger.info("Record Number {} Added to Database Successfully",lineNumber);
                line = input.readLine();
                lineNumber++;
            }
            fileLogger.info("Reached End Of File");
        } catch (IOException e) {
            fileLogger.error("IO Exception");
        } catch (ParseException e) {
            fileLogger.error("Parse Exception, Invalid Data Format");
        }
    }
}
