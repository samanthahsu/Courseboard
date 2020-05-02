package model;

/** courseID consists of the course subject: usually 3 to 4 characters
 * and a course number
 * this is to help to identify courses of the same discipline quicker, and to ensure more correct*/
public class CourseID {
    String subject;
    int number;

    public CourseID(String subject, int number) {
        this.subject = subject;
        this.number = number;
    }

    @Override
    public String toString() {
        return subject + number;
    }
}
