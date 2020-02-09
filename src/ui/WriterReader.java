package ui;

public class WriterReader {
//    todo make object that is serializable to store all component info (javafx node info) to be able to restore layout

    private BoardManager boardManager;

    public WriterReader(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /** generate list of all courses
     * inits x, y inside each course
     * SHOULD CONNECTIONS BE REDRAWN EACH TIME OR SAVED??????*/
    public boolean write() {
//        todo extracts all data from boardManager

        return false;
    }

    /** searches in from root directory */
    public boolean read(String filepath) {


        return false;
    }

}
