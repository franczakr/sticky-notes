package note;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage)
    {
        NoteCreator noteCreator = new NoteCreator();
        String lastActive;
        try
        {
            lastActive = NotesStorage.getLastActive();
            if(lastActive != null)
            {
                String text = NotesStorage.load(lastActive);
                noteCreator.createNote(stage, text);
            }
            else
            {
                noteCreator.createNote(stage);
            }
        }
        catch(IOException e)
        {
            noteCreator.createNote(stage);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
