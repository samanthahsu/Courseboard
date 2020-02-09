package ui;

public interface Subject {
    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void notifyObservers(boolean isDraw);
}
