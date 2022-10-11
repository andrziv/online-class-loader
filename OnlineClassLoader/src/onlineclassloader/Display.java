/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineclassloader;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The visual/ui components of the application
 * @author Andrej Zivkovic
 */
public class Display extends Application{
    Scene scene;
    
    // used for dragging the window around
    double xOffset, yOffset;
    
    // nodes of the application
    VBox root;
    HBox titleBar;
    Pane panel;
    ScrollPane scroll;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT); // Makes it so that the default title bar and scroll bars are transparent
        
        // Initialize variables
        root = new VBox();
        panel = new Pane();
        scroll = new ScrollPane(panel);
        titleBar = new HBox();
        
        // Sets the title bar's settings
        titleBar.setAlignment(Pos.CENTER_RIGHT);
        titleBar.setStyle("-fx-background-color: #202225");
        createTabButtons(titleBar, primaryStage); // creates the minimize and close buttons

        // Creates the window scene and sets the CSS style sheet
        scene = new Scene(root, 900, 750);
        scene.getStylesheets().add("/dataFiles/StyleSheet.css");
        
        // Sets the title bar (the top bar) so that it can be dragged
        setTitleBarMoving(primaryStage);
        
        // Adds the panel and title bar onto the root pane. Their content will
        //      now show up on the root window
        root.getChildren().addAll(titleBar, scroll);

        // Setting panel's settings
        panel.setPrefWidth(895);
        panel.setStyle("-fx-background-color: #595f69");
        
        // Setting the buttons of the courses onto panel
        loadButtons(panel);
       
        // Setting the scroll pane's settings
        scroll.setLayoutY(50);
        scroll.setVmax(550);
        scroll.setPrefSize(900, 2550);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 

        // Setting the main stage's settings (the window panel)
        primaryStage.setTitle("Online Class Dispatcher");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    
    /**
     * Sets the custom window bar to be able to drag the entire window
     * by the user.
     * @param stage the JavaFX window
     */
    public void setTitleBarMoving(Stage stage)
    {
        titleBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        titleBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    
    /**
     * Places all of the classes under a course tab group.
     * @param tab group for all classes of a course
     * @param primaryStage the JavaFX window
     */
    public void createTabButtons(Pane tab, Stage primaryStage)
    {
        Button closeButton = new Button();
        closeButton.getStyleClass().add("closeButton");
        closeButton.setLayoutX(300);
        closeButton.setText("X");
        
        closeButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                primaryStage.close();
                System.exit(0);
            }
        });
        
        Button minimizeButton = new Button();
        minimizeButton.getStyleClass().add("minimizeButton");
        minimizeButton.setAlignment(Pos.CENTER_RIGHT);
        minimizeButton.setText("-");
        
        minimizeButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                primaryStage.setIconified(true);
            }
        });

        tab.getChildren().addAll(minimizeButton, closeButton);
    }
    
    /**
     * Adjusts all of the courses and their corresponding classes' heights and adds them to
     * the root node of the scene.
     * @param root root node of the JavaFX scene
     */
    public void loadButtons(Pane root)
    {
        double y = 10;
        for (int i = 0; i < OnlineClassLoader.getInstance().courseList.size(); i++)
        {
            OnlineClassLoader.getInstance().courseList.get(i).createTab(50, y += 25, scene, root);
            y += OnlineClassLoader.getInstance().courseList.get(i).heightSize;
        }
    }

     /**
     * @param uri uri of a url
     * @return true if uri can be opened, false otherwise
     */
    public static boolean openWebpage(URI uri) {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * @param url link url
     * @return true if url can be opened, false otherwise
     */
    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}