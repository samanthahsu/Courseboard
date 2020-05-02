package model;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/** data version of subjects and total credits in each subject*/
public class Stats {
    private List<Record> records; // i want to keep the order

    public Stats() {
        records = new LinkedList<>();
    }

    /**
     * post: new record added to records list if it is not a duplicate*/
    public void addNewRecord(CourseID courseID, int credits, Color color) {
        Record newR = new Record(courseID, credits, color);
        if (!records.contains(newR)) {
            records.add(newR);
        }
    }

    /** removes record matching course ID from the records list*/
    public void removeRecord(CourseID cID){
        records.remove(new Record(cID, 0, null));
    }

    /** todo increases total credits in record list for a particular subject*/
    public void addToCredit(String subject, int addAmount){
    }

    /** todo changes color coding of a particular subject*/
    public void changeColor(String subject, Color color){
    }

    /** clears all records from the records list
     * should only be called when the board is cleared to maintain consistency*/
    public void clearAll(){records = new LinkedList<>();}

    /** returns list of records*/
    public List<Record> getRecords() {
        return records;
    }

    /** private class for storing statistic records*/
    private static class Record {
        String subject;
        int credits;
        Color color;

        public Record(CourseID courseID, int credits, Color color) {
            this.subject = courseID.subject;
            this.credits = credits;
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Record)) return false;
            Record record = (Record) o;
            return Objects.equals(subject, record.subject);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subject);
        }
    }
}
