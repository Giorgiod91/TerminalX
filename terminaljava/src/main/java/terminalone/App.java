package terminalone;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.application.Application;
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
    private TextArea cpuUsageArea; // Declared at the class level
    private long[] oldTicks;
    private Thread cpuMonitorThread;

    //
    private Path currentDirectory = Path.of("").toAbsolutePath();

    @Override
    public void start(Stage primaryStage) {

        // Load the background image
        Image image = new Image(getClass().getResourceAsStream("/images/space.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100.0, 100.0, true, true, true, false));

        // Initialize the TextArea
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-control-inner-background: black; -fx-text-fill: lightgreen; -fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        // Initialize the input field (TextField)
        inputField = new TextField();
        inputField.setPromptText("Enter command here...");
        inputField.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-family: 'Consolas'; -fx-font-size: 14px; -fx-border-color: green; -fx-border-radius: 5;");
        inputField.setBackground(new Background(backgroundImage));

        // Initialize the CPU usage TextArea
        cpuUsageArea = new TextArea();
        cpuUsageArea.setEditable(false);
        
       
        cpuUsageArea.setStyle("-fx-control-inner-background: black; -fx-text-fill: yellow; -fx-font-family: 'Consolas'; -fx-font-size: 14px;");


        //Buttons to go through directory
        Button ArrowUp = new Button("↑");
        ArrowUp.setOnAction(event -> goUp());

        Button ArrowDown = new Button("");
        ArrowDown.setOnAction(event -> goBack());


        // Button to start CPU usage
        Button CpuShowCase = new Button("Cpu Usage");
        CpuShowCase.setOnAction(event -> startCpuUsageMonitoring(cpuUsageArea)); 

        // Button to show the current directory
        Button directoryButton = new Button("Show Directory");
        directoryButton.setOnAction(event -> {
            outputArea.clear();
            outputArea.appendText("...\n");
            ShowCurrentDirectory();
        });

        // Button layout
        VBox buttonLayout = new VBox(10);
        buttonLayout.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-background-color: black; -fx-text-fill: white");
        buttonLayout.getChildren().addAll(CpuShowCase, directoryButton, ArrowUp);

        // Root layout
        VBox root = new VBox(20);
        root.getChildren().addAll(outputArea, inputField, buttonLayout, cpuUsageArea);

        // Set up the Scene
        Scene scene = new Scene(root, 800, 600);

        // Set the title
        primaryStage.setTitle("Terminal Application");

        // Set the scene on the primary stage 
        primaryStage.setScene(scene);

        // Show the stage 
        primaryStage.show();

        // Call welcome
        Welcome();
    }
    // method to move through the drirectory when the arrow is clicked

    public void goUp() {
        if (currentDirectory.getParent() != null) { // Check if there's a parent directory
            currentDirectory = currentDirectory.getParent(); // Move to the parent directory
            outputArea.appendText("Moved up to: " + currentDirectory.toString() + "\n");
        } else {
            outputArea.appendText("Already at the root directory.\n");
        }
    }

    // method to move through the drirectory when the arrow is clicked backwards!

  public void goBack(String subdirectoryName) {
    Path newDirectory = currentDirectory.resolve(subdirectoryName); // Resolve the new subdirectory path
    if (Files.isDirectory(newDirectory)) { // Check if the path exists and is a directory
        currentDirectory = newDirectory; // Navigate into the subdirectory
        outputArea.appendText("Moved into: " + currentDirectory.toString() + "\n");
    } else {
        outputArea.appendText("Subdirectory '" + subdirectoryName + "' does not exist.\n");
    }
}




    // Showcase CPU usage
    public void startCpuUsageMonitoring(TextArea cpuUsageArea) {
        // Start a new thread to update CPU usage periodically
        new Thread(() -> {
            while (true) {
                try {
                    ShowCpuUsage(cpuUsageArea);
                    Thread.sleep(1000); // Delay 1 second between updates
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Show CPU usage
    public void ShowCpuUsage(TextArea cpuUsageArea) {
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
            cpuUsageArea.appendText(String.format("Real-Time CPU Load: %.2f%%%n", cpuLoad));
        } catch (Exception e) {
            cpuUsageArea.appendText("Error while fetching CPU usage: " + e.getMessage() + "\n");
        }
    }

    // Method to show the current directory
    public void ShowCurrentDirectory() {
        outputArea.appendText("Current Directory: " + currentDirectory.toString() + "\n");
    }
    
    // Method to display a welcome message
    public void Welcome() {
        outputArea.appendText("Welcome to TerminalX\n");
        outputArea.setStyle("-fx-font-size: 25px; -fx-text-fill: pink; -fx-font-weight: bold;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
