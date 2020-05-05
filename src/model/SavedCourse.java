package model;

import ui.CourseList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

/** the savable version of courses,
 * holds all data needed to rebuild one completely*/
public class SavedCourse implements Serializable {
// there should be one unique course per course ID, the same pre-reqs and same co-reqs

//  data
    private CourseID cID;
    private String notes = "Notes";
    int credits = 0;

    private CourseList preReq;
    private CourseList coReq;
    private double posX = 0;
    private double posY = 0;

    public SavedCourse(CourseID cID, String description, int credits,
                       LinkedList<String> preReq, LinkedList<String> coReq,
                       double x, double y) {
        this.cID = cID;
        this.notes = description;
        this.credits = credits;
        this.preReq = new CourseList(preReq, CourseList.PRE_REQ);
        this.coReq = new CourseList(coReq, CourseList.CO_REQ);
        this.posX = x;
        this.posY = y;
    }

    //        dummy course for comparison
    public SavedCourse(String subject, int code) {
        this.cID = new CourseID(subject, code);
        this.preReq = new CourseList(CourseList.PRE_REQ);
        this.coReq = new CourseList(CourseList.CO_REQ);
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

    public CourseList getPreReq() {
        return preReq;
    }

    public void setPreReq(CourseList preReq) {
        this.preReq = preReq;
    }

    public void setCoReq(CourseList coReq) {
        this.coReq = coReq;
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

    /** credits is less than 1, will set to 1*/
    public void setCredits(int credits) {
        if (credits <= 0) credits = 1;
        this.credits = credits;
    }

    public CourseList getPrereqs() {
        return preReq;
    }

    public CourseList getCoReq() {
        return coReq;
    }

    public String getAllPreReqDisplayString() {
        return preReq.toDisplayString();
    }

    public String getAllCoReqDisplayString() {
        return coReq.toDisplayString();
    }

    public void setPreReq(LinkedList<String> newPrereq) {
        preReq = new CourseList(newPrereq, CourseList.PRE_REQ);
    }

    public void setCoReq(LinkedList<String> newCoreq) {
        coReq = new CourseList(newCoreq, CourseList.CO_REQ);
    }

    /**saves the x and y position of the course on the board*/
    public void setPosition(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
}
