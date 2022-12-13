package ir.maktab.data.entity;

import javax.persistence.*;
import java.util.Date;

@IdClass(StudentCourseRatingId.class)
@Entity
public class StudentCourseRating {
    @Id
    @ManyToOne
    Student student;
    @Id
    @ManyToOne
    Course course;

    @Temporal(value = TemporalType.TIME)
    Date time;
    @Temporal(value = TemporalType.DATE)
    Date date;

    String comment;
}
