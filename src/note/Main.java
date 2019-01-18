package note;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage)
    {
        NoteCreator noteCreator = new NoteCreator();
        noteCreator.createNote(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
