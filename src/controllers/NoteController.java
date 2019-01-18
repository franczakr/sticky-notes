package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import note.NoteCreator;
import note.NotesStorage;

import java.io.IOException;
import java.util.Optional;


public class NoteController
{
    private NotesStorage storage;
    private NoteCreator creator;

    @FXML
    private HTMLEditor editor;

    @FXML
    private ColorPicker backgroundColor;

    @FXML
    private void initialize()
    {
        storage = new NotesStorage();
        creator = new NoteCreator();
    }

    @FXML
    private void newClick(final ActionEvent event)
    {
        Stage stage = new Stage();
        creator.createNote(stage);
    }

    @FXML
    private void saveClick(final ActionEvent event) throws IOException
    {
        storage.save(editor.getHtmlText());
    }

    @FXML
    private void loadClick(final ActionEvent event) throws IOException
    {
        String note = storage.chooseNote();
        if(!note.equals(""))
        {
            String text = storage.load(note);
            if(!text.equals(""))
            {
                Stage stage = new Stage();
                creator.createNote(stage, text);
            }
        }
    }

    @FXML
    private void changeBackgroundClick(final ActionEvent event)
    {
        editor.setHtmlText(
            editor.getHtmlText()
            .replaceFirst("<body([^<>]*?)(?:style=\"background-color:[A-F0-9]{6}\")?>",
                    "<body$1 style=\"background-color:" + toRGBCode(backgroundColor.getValue()) + "\">" ));
    }

    private String toRGBCode( Color color )
    {
        return String.format( "%02X%02X%02X",
                (int) ( color.getRed() * 255 ),
                (int) ( color.getGreen() * 255 ),
                (int) ( color.getBlue() * 255 ) );
    }

    public void setText(String text)
    {
        editor.setHtmlText(text);
    }

}
