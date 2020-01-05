package visualization;

public class SaveAndLoad {
//    todo make object that is serializable to store all component info (javafx node info) to be able to restore layout

    private BoardManager boardManager;

    public SaveAndLoad(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public boolean save() {
//        todo extracts all data from boardManager
//        all positions of stuff saved as a map?
        return false;
    }

    public boolean load() {
        return false;
    }

}
