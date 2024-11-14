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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;


public class App extends Application {

   

    private TextArea outputArea;
    private TextField inputField;
    
    //booleans
    private boolean buttonClick = false;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the TextArea
        outputArea = new TextArea();
        outputArea.setEditable(false); 
        outputArea.setStyle("-fx-background-color: transparent;");

         // Initialize the input field (TextField)
        inputField = new TextField();
        inputField.setPromptText("Enter command here...");

       
        // button to start cpu usage method

        Button CpuShowCase = new Button("Cpu Usage");
        CpuShowCase.setOnAction(event -> ShowCpuUsage());

        //call welcome method
        Welcome();
        

          // Button to show the current directory
          Button directoryButton = new Button("Show Directory");
          directoryButton.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                outputArea.clear();
                    
                
                    for(int i = 0; i < 8; i++){
                       
                            outputArea.appendText("...");
                           

                        }
                            ShowCurrentDirectory();  // Show the current directory when button is clicked
                         
                            

                        
                        
                        
                       

                    }
                    
                  
                  
                 
              }
          );
        //button layout 
        VBox buttonLayout = new VBox(10);  
        buttonLayout.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-background-color: #f0f0f0;");
        buttonLayout.getChildren().addAll(CpuShowCase, directoryButton);

        VBox root = new VBox(20);  
        root.setStyle("-fx-background-color: transparent;");
        root.getChildren().addAll(outputArea, inputField, buttonLayout, CpuShowCase);

       
        

      

        // Set up the Scene 
        Scene scene = new Scene(root, 800, 600); 


        // Image 
        Image image = new Image(getClass().getResourceAsStream("/images/space.jpg"));

        

       


          // adding BackgroundImage through javafx scene


          BackgroundImage backgroundImage = new BackgroundImage(image, 

          BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, 
          BackgroundPosition.CENTER, 
          new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false)
      );

       // Set the background of the StackPane
        root.setBackground(new Background(backgroundImage));


       

        // Set the title of the stage (window)
        primaryStage.setTitle("Terminal Application");

        // Set the scene on the primary stage (window)
        primaryStage.setScene(scene);

        // Show the stage (this opens the window)
        primaryStage.show();
    }

    // showcase CPU usage
    public void ShowCpuUsage() {
        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(null) * 100;
        System.out.printf("Real-Time System CPU Load: %.2f%%%n", cpuLoad);



            
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
        outputArea.appendText(currentDirectory.toString() + "\n");
        outputArea.setStyle("-fx-font-size: 15px; -fx-text-fill: pink; -fx-font-weight: bold;");
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

    //welcome method
    public void Welcome(){
        outputArea.appendText("Welcome to TermialX");
        outputArea.setStyle("-fx-font-size: 25px; -fx-text-fill: pink; -fx-font-weight: bold;");

    }



    public static void main(String[] args) {
     
        launch(args);
    }


}





