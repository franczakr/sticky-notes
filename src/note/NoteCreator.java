package note;

import controllers.NoteController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class NoteCreator
{
    public void createNote(Stage stage)
    {
        createNote(stage, "");
    }

    public void createNote(Stage stage, String text)
    {
        try
        {
            ClassLoader loader = getClass().getClassLoader();
            URL layout = loader.getResource("layouts/note.fxml");
            Objects.requireNonNull(layout, "Could not find file layouts/note.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(layout);
            Parent root = fxmlLoader.load();
            NoteController controller = fxmlLoader.getController();
            controller.setText(text);
            stage.setOnCloseRequest((e) -> {controller.onExit();});
            stage.setTitle("Sticky notes");
            stage.setScene(new Scene(root));
            Image i1 = loadImage("icons/icon16x16.png");
            Image i2 = loadImage("icons/icon32x32.png");
            Image i3 = loadImage("icons/icon64x64.png");
            stage.getIcons().addAll(i1, i2, i3);
            stage.show();
        }
        catch(Exception e)
        {
            showErrorDialog(e.getMessage());
            Platform.exit();
            System.exit(0);
        }
    }

    private Image loadImage(String url)
    {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(url);
        Objects.requireNonNull(stream, "Could not find file " + url);
        return new Image(stream);
    }

    public void showErrorDialog(String error)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("");
        alert.setContentText(error);
        alert.showAndWait();
    }
}
