package note;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
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
    public static String save(String note) throws IOException
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
            return result.get();
        }
        return null;
    }

    public static String chooseNote() throws IOException
    {
        List<String> notes = new ArrayList<>();
        Files.find(
            Paths.get("notes/"), 1,
                (path, attr)-> attr.isRegularFile() && path.getFileName().toString().endsWith(".not"))
            .map(Path::toFile).forEach((file) -> notes.add(file.getName()));
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", notes);
        dialog.setTitle("Sticky notes");
        dialog.setHeaderText("");
        dialog.setContentText("Choose note to load:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
            return result.get();
        return "";
    }

    public static String load(String name) throws IOException
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

    public static void saveLastActive(String name) throws IOException
    {
        if(!Files.exists(Paths.get("notes")))
            Files.createDirectory(Paths.get("notes"));

        FileWriter writer = new FileWriter("notes/last");
        writer.append(name).append(".not");
        writer.close();
    }

    public static String getLastActive() throws IOException
    {
        if(!Files.exists(Paths.get("notes")) || !Files.exists(Paths.get("notes", "last")))
            return null;

        BufferedReader reader = new BufferedReader(new FileReader("notes/last"));
        String name = reader.readLine();
        reader.close();

        return name;
    }

}