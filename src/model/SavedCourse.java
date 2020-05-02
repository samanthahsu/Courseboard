package model;

import ui.CourseList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

/** the savable version of courses*/
public class SavedCourse implements Serializable {
// there should be one unique course per course ID, the same pre-reqs and same co-reqs

//  data
    private CourseID cID;
    private String notes = "Notes";
    int credits = 0;
    private CourseList prereq;
    private CourseList coreq;
    private double posX;
    private double posY;

    public SavedCourse(CourseID courseID, String description, int credits, LinkedList<String> prereq, LinkedList<String> coreq, double x, double y) {
        cID = courseID;
        this.notes = description;
        this.credits = credits;
        this.prereq = new CourseList(prereq, CourseList.PRE_REQ);
        this.coreq = new CourseList(coreq, CourseList.CO_REQ);
        this.posX = x;
        this.posY = y;
    }

    //        dummy course for comparison
    public SavedCourse(String subject, int code) {
        this.cID = new CourseID(subject, code);
        this.prereq = new CourseList(CourseList.PRE_REQ);
        this.coreq = new CourseList(CourseList.CO_REQ);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavedCourse)) return false;
        SavedCourse savedCourse = (SavedCourse) o;
        return Objects.equals(cID, savedCourse.cID);
    }

    public String getCourseIDAsString() {
        return cID.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cID);
    }

    public CourseID getcID() {
        return cID;
    }

    public String getSubject() {return this.cID.subject;}

    public void setcIDNumber(int number) {
        this.cID.number = number;
    }

    public void setcIDSubject(String subject) {
        this.cID.subject = subject;
    }

    public CourseList getPrereq() {
        return prereq;
    }

    public void setPrereq(CourseList prereq) {
        this.prereq = prereq;
    }

    public void setCoreq(CourseList coreq) {
        this.coreq = coreq;
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
