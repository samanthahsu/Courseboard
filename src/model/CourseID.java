package model;

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
