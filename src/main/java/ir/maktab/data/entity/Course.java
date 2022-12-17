package ir.maktab.data.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "course")
    private List<StudentCourseRating> ratingList;

    @Override
    public String toString() {
        return name;
    }
}
