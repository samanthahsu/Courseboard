package model;

import java.util.LinkedList;

public class CrsReqs {

    LinkedList<String> prereq;
    LinkedList<String> coreq;

    CrsReqs(LinkedList<String> prereq, LinkedList<String> coreq) {
        this.prereq = prereq;
        this.coreq = coreq;
    }

    public LinkedList<String> getPrereq() {
        return prereq;
    }

    public void setPrereq(LinkedList<String> prereq) {
        this.prereq = prereq;
    }

    public LinkedList<String> getCoreq() {
        return coreq;
    }

    public void setCoreq(LinkedList<String> coreq) {
        this.coreq = coreq;
    }
}
