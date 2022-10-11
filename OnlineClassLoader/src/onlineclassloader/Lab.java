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
 * Class representation of a lab
 * @author Andrej Zivkovic
 */
public final class Lab extends CourseClass{
    private String TA;
    
    /**
     * Creates a lab object
     * @param timeStartLab a string with the time in format "HH:MM" for the class starting time
     * @param timeEndLab a string with the time in format "HH:MM" for the class ending time
     * @param dayLab a string with the name of the day ie "Monday" that the class is on in the week
     * @param linkLab a link to join the lab, like a zoom link
     * @param TA the teaching assistant
     */
    public Lab(String timeStartLab, String timeEndLab, String dayLab, String linkLab, String TA){
        super(timeStartLab, timeEndLab, dayLab, linkLab);
        type = "Lab";
        
        this.TA = TA;
    }

    /**
     * Creates a button for "this" lab used in the JavaFX application
     * @param scene JavaFX scene for window
     */
    @Override
    public void createButton(Scene scene)
    {
        scene.getStylesheets().add("/dataFiles/StyleSheet.css");
        button = new Button();
        updateButton();
        button.getStyleClass().add("buttonLabLec");
        button.setText(type + "\t\t\t\t\t\t\t\t\t\t" + dayOn + " " + to12HourTimeString(getHour(timeStart), getMinute(timeStart)) + " to " + to12HourTimeString(getHour(timeEnd), getMinute(timeEnd)) + "\n" + TA);
        button.setMinWidth(800);
        button.setPrefHeight(100);
        
        button.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
            Display.openWebpage(new URL(link));}
            catch (MalformedURLException e) {}
        }});
    }

    /**
     * Adds some styling to the lab button depending on if it is occurring right now or is the next class
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
     * @return the TA
     */
    public String getTA() {
        return TA;
    }
    
    /**
     * @return string representation of the lab
     */
    @Override
    public String toString()
    {
        return TA + " " + super.toString();
    }
}
