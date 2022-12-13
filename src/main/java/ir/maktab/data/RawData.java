package ir.maktab.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RawData {
    String courseName;
    String studentName;
    Date timeStamp;
    float rating;
    String comment;
}
