package terminalone;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

   

    private TextArea outputArea;
    private TextField inputField;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the TextArea
        outputArea = new TextArea();
        outputArea.setEditable(false); 

         // Initialize the input field (TextField)
        inputField = new TextField();
        inputField.setPromptText("Enter command here...");

        

          // Button to show the current directory
          Button directoryButton = new Button("Show Directory");
          directoryButton.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                  ShowCurrentDirectory();  // Show the current directory when button is clicked
              }
          });

        // Create a layout 
        StackPane root = new StackPane();
        root.getChildren().addAll(outputArea, inputField, directoryButton); // Add the TextArea 

        // Set up the Scene 
        Scene scene = new Scene(root, 800, 600); 


        // import image
       //Image image = new Image("F:\\APPS\\Terminal\\terminaljava\\src\\main\\resources\\images\\space.jpg");


          // adding BackgroundImage through javafx scene


       //BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, 
        //BackgroundRepeat.NO_REPEAT,  BackgroundPosition.CENTER,  BackgroundSize.DEFAULT);


       

        // Set the title of the stage (window)
        primaryStage.setTitle("Terminal Application");

        // Set the scene on the primary stage (window)
        primaryStage.setScene(scene);

        // Show the stage (this opens the window)
        primaryStage.show();
    }


    // get the user input with Scanner 

    public void GetUserInput(){
        // get the input with from the TextField
        String userInput = inputField.getText();
        //show the input from the TextField
        outputArea.appendText("User entered: " + userInput + "\n");

        CreateDirectory(userInput);
        inputField.clear();
    }

    
      // Method to show the current directory
      public void ShowCurrentDirectory() {
        Path currentDirectory = Path.of("").toAbsolutePath(); // Get the current working directory
        outputArea.appendText("Current Directory: " + currentDirectory.toString() + "\n");
    }

    //method to create a directory

    public void CreateDirectory(String directoryName){
        try {
            // create directory
            var path = Path.of(directoryName);
            Files.createDirectories(path);
            
        } catch (Exception e) {
            outputArea.appendText("failed to create" + directoryName);
        }
       

    }



    public static void main(String[] args) {
     
        launch(args);
    }


}





