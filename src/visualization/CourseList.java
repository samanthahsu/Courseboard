package visualization;

import model.Course;
import model.Link;

import java.util.LinkedList;
import java.util.List;

public class CourseList extends LinkedList<String> {

    public final static char PRE_REQ = 'p';
    public final static char CO_REQ = 'c';

    char type;

    public CourseList(char type) {
        this.type = type;
    }

    public CourseList(List<String> list, char type) {
        addAll(list);
    }

    private String toString(LinkedList<String> stringLinkedList) {
        StringBuilder ret = new StringBuilder();
        for (String s : stringLinkedList) {
            ret.append(s += ", ");
        }
        if (ret.length() == 0) return "";
        return ret.substring(0, ret.length()-2);
    }

    public char getType() {
        return type;
    }
}
