package ir.maktab.service;

import ir.maktab.data.entity.Course;
import ir.maktab.data.entity.Student;
import ir.maktab.data.entity.StudentCourseRating;
import ir.maktab.repository.CourseRepo;
import ir.maktab.repository.EntityManagerFactoryProducer;
import ir.maktab.repository.StudentCourseRatingRepo;
import ir.maktab.repository.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingService.class);

    public void addStudentCourseRating(StudentCourseRating rating) {
        EntityManager em = EntityManagerFactoryProducer.emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Optional<Student> optionalStudent = studentRepo.findByName(rating.getStudent().getName());
            optionalStudent.ifPresentOrElse(rating::setStudent, () -> {
                studentRepo.save(em, rating.getStudent());
                LOGGER.debug("***New Student Persisted");
            });

            Optional<Course> optionalCourse = courseRepo.findByName(rating.getCourse().getName());
            optionalCourse.ifPresentOrElse(rating::setCourse, () -> {
                courseRepo.save(em, rating.getCourse());
                LOGGER.debug("***New Course Persisted");
            });

            studentCourseRatingRepo.save(em, rating);
            LOGGER.debug("***StudentCourseRating Persisted");
            em.getTransaction().commit();
            em.close();
            LOGGER.info("***New Record Added To Database Successfully");
        } catch (Exception e) {
            LOGGER.error("***Exception Occurred While Committing Record");
            em.getTransaction().rollback();
            LOGGER.info("***Record Rolled Back");
            em.close();
        }
    }

    public void readRecords() {
        try (RandomAccessFile input = new RandomAccessFile("rating.txt", "r")) {
            LOGGER.info("***Input File Opened Successfully");
            String line = input.readLine();
            int lineNumber = 1;
            while (line != null) {
                LOGGER.debug("***Line number {} Read", lineNumber);
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
                LOGGER.debug("***Line number {} Parsed", lineNumber);

                addStudentCourseRating(rating);
                line = input.readLine();
                lineNumber++;
            }
            LOGGER.info("***Reached End Of File");
        } catch (IOException e) {
            LOGGER.error("***IO Exception");
        } catch (ParseException e) {
            LOGGER.error("***Parse Exception, Invalid Data Format");
        }
    }
}
