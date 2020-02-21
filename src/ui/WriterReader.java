package ui;

import javafx.scene.control.Alert;

import java.io.*;


public class WriterReader {
//    todo make object that is serializable to store all component info (javafx node info) to be able to restore layout
    public static final String FILE_ROOT = System.getProperty("user.dir");
    private BoardManager boardManager;

    public WriterReader(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /** generate list of all courses
     * inits x, y inside each course
     * SHOULD CONNECTIONS BE REDRAWN EACH TIME OR SAVED??????
     * @param savedFile*/
    public void write(File savedFile) {
        String filePath = FILE_ROOT + "\\" + savedFile;
        //        so saves as a set of courses with their positions intact
//        this object holds everything needed to reconstruct the whole board
        SavedBoard savedBoard = new SavedBoard(boardManager);
        FileOutputStream outputStream;
        ObjectOutputStream objectOutputStream;

        try {
//            create the file
            if (savedFile.createNewFile()) {
                System.out.println("File already exists!");
            }
//            output to the file
            outputStream = new FileOutputStream(savedFile);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(savedBoard);
            objectOutputStream.close();
        } catch (IOException e) {
            showAlertWarning("write problems");
            e.printStackTrace();
        }
    }

    /** searches in from root directory
     * todo pop up window for entering
     * @param file*/
    public void read(File file) {

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SavedBoard savedBoard = (SavedBoard) objectInputStream.readObject();
            savedBoard.populate(boardManager);

            System.out.println("file read successful!");
        } catch (IOException | ClassNotFoundException e) {
            showAlertWarning("read problems");
            e.printStackTrace();
        }
    }

    private void showAlertWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
