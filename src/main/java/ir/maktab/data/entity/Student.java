package ir.maktab.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "student")
    @EqualsAndHashCode.Exclude
    private List<StudentCourseRating> ratingList = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
