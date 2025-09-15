package rakan.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import rakan.Rakan;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Rakan rakan;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Cat1.jpg"));
    private Image rakanImage = new Image(this.getClass().getResourceAsStream("/images/Cat2.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Rakan instance */
    public void setRakan(Rakan r) {
        rakan = r;
        String greeting = rakan.getUi().greet();
        dialogContainer.getChildren().add(
                DialogBox.getRakanDialog(greeting, rakanImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Rakan's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = rakan.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getRakanDialog(response, rakanImage)
        );
        userInput.clear();

        if (rakan.shouldExit()) {
            // Delay a bit so the user can see the exit message
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> {
                Stage stage = (Stage) dialogContainer.getScene().getWindow();
                stage.close();
                Platform.exit();
                System.exit(0);
            });
            delay.play();
        }
    }
}

