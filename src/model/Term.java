package model;

import javafx.scene.layout.HBox;

import java.util.HashSet;
import java.util.Set;

//holds up to 20 courses
public class Term extends HBox {

    static final int MAX = 20;
    private String name;
    Set<Course> courses;

    public Term(String name) {
        this.name = name;
        courses = new HashSet<>();
    }

    public boolean addCourse(Course c) {
        return courses.add(c);
    }

    public boolean removeCourse(Course c) {
        if (c == null) return false;
        return courses.remove(c);
    }

    public Course findCourse(String code) {
        for (Course c : courses) {
            if (c.code.equals(code)) return c;
        }
        return null;
    }

    public void moveCourse(Course c, Term dest) {
        dest.addCourse(c);
        removeCourse(c);
    }

    public String printCourses() {
        StringBuilder ret = new StringBuilder();
        for (Course c : courses) {
            ret.append(c.toString()).append("\n");
        }
        return ret.toString();
    }

    public String getName() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }
}
