package ir.maktab.data.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentCourseRatingId implements Serializable {
    Course course;
    Student student;
}
