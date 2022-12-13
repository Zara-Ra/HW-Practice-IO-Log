package ir.maktab.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StudentCourseRatingId.class)
@Entity
public class StudentCourseRating {
    @Id
    @ManyToOne
    private Student student;
    @Id
    @ManyToOne
    private Course course;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;
    private float rating;
    private String comment;
}
