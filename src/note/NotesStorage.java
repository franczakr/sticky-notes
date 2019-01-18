package note;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotesStorage
{
    public void save(String note) throws IOException
    {
        if(!Files.exists(Paths.get("notes")))
            Files.createDirectory(Paths.get("notes"));

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Sticky notes");
        dialog.setHeaderText("");
        dialog.setContentText("Please enter title for your note:");
        dialog.initStyle(StageStyle.UTILITY);
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
        {
            FileWriter writer = new FileWriter("notes/"+ result.get() +".not");
            writer.append(note);
            writer.close();
        }
    }

    public String chooseNote() throws IOException
    {
        List<String> notes = new ArrayList<>();
        File[] files = Files.walk(Paths.get("notes/")).map(Path::toFile).toArray(File[]::new);
        for(File file : files)
        {
            if(file.isFile() && file.getName().endsWith(".not"))
            {
                notes.add(file.getName());
            }
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", notes);
        dialog.setTitle("Sticky notes");
        dialog.setHeaderText("");
        dialog.setContentText("Choose note to load:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
            return result.get();
        return "";
    }

    public String load(String name) throws IOException
    {
        File note = new File("notes/" + name);
        if(!note.exists() || note.isDirectory())
        {
            return "";
        }
        BufferedReader reader = new BufferedReader(new FileReader(note));
        String text = reader.readLine();
        reader.close();
        return text;
    }

}