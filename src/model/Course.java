package model;

import visualization.CourseList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class Course implements Serializable {
// there should be one unique course per course ID, the same pre-reqs and same co-reqs

/** is obj that is saved
 * includes all information to generate a CourseNode*/

// node info, todo is init when obj is saved
        private double sceneX;
        private double sceneY;

//  data
    private String id = "ID";
    private String notes = "Notes";
    int credits = 0;
    private CourseList prereq;
    private CourseList coreq;

    public Course(String code, String description, int credits, LinkedList<String> prereq, LinkedList<String> coreq) {
        this.id = code;
        this.notes = description;
        this.credits = credits;
        this.prereq = new CourseList(prereq, CourseList.PRE_REQ);
        this.coreq = new CourseList(coreq, CourseList.CO_REQ);
    }

    //        dummy course for comparison
    public Course(String code) {
        this.id = code;
        this.prereq = new CourseList(CourseList.PRE_REQ);
        this.coreq = new CourseList(CourseList.CO_REQ);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ": " + notes + '\'' +
                ", credits=" + credits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String description) {
        this.notes = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public CourseList getPrereqs() {
        return prereq;
    }

    public CourseList getCoreq() {
        return coreq;
    }

    public String getAllPrereqDisplayString() {
        return prereq.toDisplayString();
    }

    public String getAllCoreqDisplayString() {
        return coreq.toDisplayString();
    }

    public void setPreReq(LinkedList<String> newPrereq) {
        prereq = new CourseList(newPrereq, CourseList.PRE_REQ);
    }

    public void setCoReq(LinkedList<String> newCoreq) {
        coreq = new CourseList(newCoreq, CourseList.CO_REQ);
    }
}
