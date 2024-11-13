package terminalone;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the TextArea
        outputArea = new TextArea();
        outputArea.setEditable(false); 
        
        // Create a layout 
        StackPane root = new StackPane();
        root.getChildren().add(outputArea); // Add the TextArea 

        // Set up the Scene 
        Scene scene = new Scene(root, 800, 600); 

        // Set the title of the stage (window)
        primaryStage.setTitle("Terminal Application");

        // Set the scene on the primary stage (window)
        primaryStage.setScene(scene);

        // Show the stage (this opens the window)
        primaryStage.show();
    }

    public static void main(String[] args) {
     
        launch(args);
    }
}





