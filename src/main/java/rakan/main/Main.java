package rakan.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rakan.Rakan;
import rakan.gui.MainWindow;

public class Main extends Application {

    private Rakan rakan = new Rakan("./data/rakan.txt");
    private Image icon = new Image(this.getClass().getResourceAsStream("/images/Cat3-3.png"));

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("Rakan - Task Manager");
            stage.getIcons().add(icon);
            fxmlLoader.<MainWindow>getController().setRakan(rakan);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

