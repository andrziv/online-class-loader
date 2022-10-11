/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineclassloader;

import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * Class representation of a lecture
 * @author Andrej Zivkovic
 */
public final class Lecture extends CourseClass{
    private String professor;
    
    /**
     * Creates a lecture object
     * @param timeStartClass a string with the time in format "HH:MM" for the class starting time
     * @param timeEndClass a string with the time in format "HH:MM" for the class ending time
     * @param dayClass a string with the name of the day ie "Monday" that the class is on in the week
     * @param linkLecture a link to join the lecture, like a zoom link
     * @param professor the class professor 
     */
    public Lecture(String timeStartClass, String timeEndClass, String dayClass, String linkLecture, String professor){
        super(timeStartClass, timeEndClass, dayClass, linkLecture);
        type = "Lecture";
        
        this.professor = professor;
    }
 
    /**
     * Creates a button for "this" lecture used in the JavaFX application
     * @param scene JavaFX scene for window
     */
    @Override
    public void createButton(Scene scene)
    {
        scene.getStylesheets().add("/dataFiles/StyleSheet.css");
        button = new Button();
        updateButton();
        button.getStyleClass().add("buttonLabLec");
        button.setText(type + "\t\t\t\t\t\t\t\t\t" + dayOn + " " + to12HourTimeString(getHour(timeStart), getMinute(timeStart)) + " to " + to12HourTimeString(getHour(timeEnd), getMinute(timeEnd)) + "\n" + professor);
        button.setMinWidth(800);
        
        button.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
            Display.openWebpage(new URL(link));}
            catch (MalformedURLException e) {}
        }});
    }
    
    /**
     * Adds some styling to the lecture button depending on if it is occurring right now or is the next class.
     */
    public void updateButton()
    {
        if(isRightNow())
        {
            button.setStyle("-fx-effect: innershadow( three-pass-box , rgba(255,225,38,1) , 0, -50 , -50 , 0)");
        }
        else if (isNext())
        {
            button.setStyle("-fx-effect: innershadow( three-pass-box , rgba(38,255,149,1) , 0, -50 , -50 , 0)");
        }
    }
    /**
     * @return the professor
     */
    @Override
    public String getProfessor() {
        return professor;
    }

    /**
     * @return string representation of the lecture
     */
    @Override
    public String toString()
    {
        return professor + " " + super.toString();
    }
}
