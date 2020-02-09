package model;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/** data version of helpful stats of what is actually on the board*/
public class Stats {
    private List<Record> records; // i want to keep the order

    public Stats() {
        records = new LinkedList<>();
    }

//    todo change this to work with cid
    public void addNewRecord(String subject, int credits, Color color) {
        Record newR = new Record(subject, credits, color);
        if (!records.contains(newR)) {
            records.add(newR);
        }
    }

    public void removeRecord(CourseID cID){
        records.remove(new Record(cID.subject, 0, null));
    }

    public void addToCredit(String subject, int additor){}

    public void changeColor(String subject, Color color){}

    public List<Record> getRecords() {
        return records;
    }

    private class Record {
        String subject;
        int credits;
        Color color;

        public Record(String subject, int credits, Color color) {
            this.subject = subject;
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
