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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import javafx.scene.input.KeyCode;



public class App extends Application {

   

    private TextArea outputArea;
    private TextField inputField;



    private long[] oldTicks;



    private Thread cpuMonitorThread;
    
    //booleans
    private boolean buttonClick = false;

    @Override
    public void start(Stage primaryStage) {



        
                // Image // Load the image
    Image image = new Image(getClass().getResourceAsStream("/images/space.jpg"));

    // Create a BackgroundImage with appropriate scaling
        BackgroundImage backgroundImage = new BackgroundImage(image, 
        BackgroundRepeat.NO_REPEAT,        
        BackgroundRepeat.NO_REPEAT,        
        BackgroundPosition.CENTER,        
        new BackgroundSize(100.0, 100.0, true, true, true, false) 
            );




        // Initialize the TextArea
        outputArea = new TextArea();
        outputArea.setEditable(false); 
        //outputArea.setStyle("-fx-background-color: transparent; ");
        outputArea.setStyle("-fx-control-inner-background: black; -fx-text-fill: lightgreen; -fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        outputArea.setBackground(new Background(backgroundImage));

         // Initialize the input field (TextField)
        inputField = new TextField();
        inputField.setPromptText("Enter command here...");
        //inputField.setStyle("-fx-text-fill: white");
        inputField.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-family: 'Consolas'; -fx-font-size: 14px; -fx-border-color: green; -fx-border-radius: 5;");

        inputField.setBackground(new Background(backgroundImage));

       
        // button to start cpu usage method

        Button CpuShowCase = new Button("Cpu Usage");
        CpuShowCase.setOnAction(event -> startCpuUsageMonitoring());

        // button to stop Cpu monitoring
        //Button StopCpushowCase = new Button("Stop");
       // CpuShowCase.setOnAction(event -> stopCpuUsageMonitoring());


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
        buttonLayout.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-background-color: black; -fx-text-fill-white");
        buttonLayout.getChildren().addAll(CpuShowCase, directoryButton);

        VBox root = new VBox(20);       
        root.setBackground(new Background(backgroundImage));
        root.setStyle("");

        root.getChildren().addAll(outputArea, inputField, buttonLayout, CpuShowCase);

       
        

      

        // Set up the Scene 
        //Scene scene = new Scene(root, 800, 600); 
        //scene.setBackground(new Background(backgroundImage));

        StackPane backgroundPane = new StackPane();
        backgroundPane.setBackground(new Background(backgroundImage));
        backgroundPane.setPrefSize(800, 600); 
        root.getChildren().add(backgroundPane);
              
        // Set up the Scene
        Scene scene = new Scene(root, 800, 600); 










       


        // Set the title of the stage (window)
        primaryStage.setTitle("Terminal Application");

        // Set the scene on the primary stage (window)
        primaryStage.setScene(scene);

        // Show the stage (this opens the window)
        primaryStage.show();
    }

    // showcase CPU usage
    // Periodically show CPU usage
    public void startCpuUsageMonitoring() {
        // Start a new thread to update CPU usage periodically
        new Thread(() -> {
            while (true) {
                try {
                    ShowCpuUsage();
                    Thread.sleep(1000); // Delay 1 second between updates
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    


        

    

    // Show CPU usage
    public void ShowCpuUsage() {
        try {
            SystemInfo si = new SystemInfo();
            CentralProcessor processor = si.getHardware().getProcessor();

            // Initialize oldTicks if it's the first call
            if (oldTicks == null) {
                oldTicks = processor.getSystemCpuLoadTicks();
            }

            // Get the current CPU load between old and new ticks
            double cpuLoad = processor.getSystemCpuLoadBetweenTicks(oldTicks) * 100;

            // Update oldTicks to the latest CPU load ticks for the next call
            oldTicks = processor.getSystemCpuLoadTicks();

            // Display the CPU usage
            outputArea.appendText(String.format("Real-Time CPU Load: %.2f%%%n", cpuLoad));
        } catch (Exception e) {
            outputArea.appendText("Error while fetching CPU usage: " + e.getMessage() + "\n");
        }
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
      public void ShowCurrentDirectory(KeyEvent e) {
        Path currentDirectory = Path.of("").toAbsolutePath(); // Get the current working directory
        outputArea.appendText(currentDirectory.toString() + "\n");
        outputArea.setStyle("-fx-font-size: 15px; -fx-text-fill: pink; -fx-font-weight: bold;");

        if (e.getCode() == KeyCode.UP) {
            Path parentDirectory = currentDirectory.getParent();
            if (parentDirectory != null) {
                currentDirectory = parentDirectory; // Update the current directory
            } 

            
            
        }
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





