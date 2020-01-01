package model;

import java.util.LinkedList;
import java.util.Objects;

public class Course {

    String code;
    String description;
    int credits;

    CrsReqs req;

    public Course(String code, String description, int credits, LinkedList<String> prereq, LinkedList<String> coreq) {
        this.code = code;
        this.description = description;
        this.credits = credits;
        req = new CrsReqs(prereq, coreq);
    }

    public Course(String code) {
        this.code = code;
        description = "Description here";
        req = new CrsReqs(new LinkedList<>(), new LinkedList<>());
//        dummy course for comparison
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return code + ": " + description + '\'' +
                ", credits=" + credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public LinkedList<String> getPreReq() {
        return req.getPrereq();
    }

    public void setPreReq(LinkedList<String> preReq) {
        this.req.prereq = preReq;
    }

    public LinkedList<String> getCoReq() {
        return req.coreq;
    }

    public void setCoReq(LinkedList<String> coReq) {
        this.req.coreq = coReq;
    }

}
