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
    private NoteCreator creator;
    private String name;

    @FXML
    private HTMLEditor editor;

    @FXML
    private ColorPicker backgroundColor;

    @FXML
    private void initialize()
    {
        creator = new NoteCreator();
    }

    @FXML
    private void newClick(final ActionEvent event)
    {
        Stage stage = new Stage();
        creator.createNote(stage);
    }

    @FXML
    private void saveClick(final ActionEvent event)
    {
        try
        {
            name = NotesStorage.save(editor.getHtmlText());
        }
        catch(IOException e)
        {
            creator.showErrorDialog(e.getMessage());
        }
    }

    @FXML
    private void loadClick(final ActionEvent event)
    {
        try
        {
            String note = NotesStorage.chooseNote();
            if(!note.equals(""))
            {
                String text = NotesStorage.load(note);
                if(!text.equals(""))
                {
                    Stage stage = new Stage();
                    creator.createNote(stage, text);
                }
            }
        }
        catch(IOException e)
        {
            creator.showErrorDialog(e.getMessage());
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

    public void onExit()
    {
        try
        {
            if(name != null)
                NotesStorage.saveLastActive(name);
        }
        catch(IOException e)
        {
            creator.showErrorDialog(e.getMessage());
        }
    }

}
