/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineclassloader;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Class representation of a university Course
 * @author Andrej Zivkovic
 */
public class Course {
    private String courseCode;
    private String courseTitle;
    private int sectionNum;
    private String professor;
    private String labTA;
    
    public ArrayList<Lecture> lectureList;
    public ArrayList<Lab> labList;
    public ArrayList<CourseClass> allRelatedCourseClasses;
    
    public Button courseTab;
    public double heightSize = 0;
    
    public Course() {}; // empty constructor for easier inheritance
    
    /**
     * Creates a course object
     * @param courseCode the course code
     * @param courseTitle the course's title
     * @param sectionNum the course's section
     * @param lecture a list of lecture objects to be associated with the course
     * @param lab a lab object to be associated with the course
     */
    public Course(String courseCode, String courseTitle, int sectionNum, Lecture[] lecture, Lab lab){
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.sectionNum = sectionNum;
        this.professor = lecture[0].getProfessor();
        this.labTA = lab.getTA();
        
        lectureList = new ArrayList<>();
        labList = new ArrayList<>();
        allRelatedCourseClasses = new ArrayList<>();
        
        lectureList.addAll(Arrays.asList(lecture));
        labList.add(lab);
        
        allRelatedCourseClasses.addAll(Arrays.asList(lecture));
        allRelatedCourseClasses.add(lab);
    }

    /**
     * Will create an error if there are no lectures or the given i
     * is outside of the arrayList size.
     * @param i the index of the set of lecture associated with the course
     * @return a lab associated with the course
     */
    public Lecture getLecture(int i)
    {
        return lectureList.get(i);
    }
    
    /**
     * Will create an error if there are no labs or the given i
     * is outside of the arrayList size.
     * @param i the index of the set of labs associated with the course
     * @return a lab associated with the course
     */
    public Lab getLab(int i)
    {
        return labList.get(i);
    }
    
    /**
     * Finds the class such as a lecture or lab in the course that is closest to the 
     * current time on the machine.
     * @return CourseClass
     */
    public CourseClass nextClass()
    {
        CourseClass closestClass = allRelatedCourseClasses.get(0);
        for(int i = 0; i < allRelatedCourseClasses.size(); i++)
        {
            if (closestClass.score > allRelatedCourseClasses.get(i).score)
            {
                closestClass = allRelatedCourseClasses.get(i);
            }
        }
	
        return closestClass;
    }

    /**
     * Creates the JavaFX visuals for the entire course block, including
     * the header, and all of the class and labs associated with this course.
     * @param x the header starting position x on the screen
     * @param y the header starting position y on the screen
     * @param scene the scene of the JavaFX window
     * @param root the root node of the JavaFX scene
     */
    public void createTab(double x, double y, Scene scene, Pane root)
    {
        scene.getStylesheets().add("/dataFiles/StyleSheet.css");
        courseTab = new Button();
        // courseTab.setStyle("-fx-background-image: url('/classImages/" + courseCode + ".jpg')");
        courseTab.getStyleClass().add("buttonCourseHeader");
        courseTab.setLayoutX(x);
        courseTab.setLayoutY(y);
        courseTab.setText(courseCode + " Section: " + sectionNum +"\n" + courseTitle);
        courseTab.setMinWidth(800);
        courseTab.setPrefHeight(100);
        root.getChildren().add(courseTab);
        
        y += 100;
        
        for (int i = 0; i < allRelatedCourseClasses.size(); i++)
        {
            if(i < allRelatedCourseClasses.size() - 1)
            {
                allRelatedCourseClasses.get(i).createButton(scene);
                allRelatedCourseClasses.get(i).button.setLayoutX(x);
                allRelatedCourseClasses.get(i).button.setLayoutY(y + 100*i);
                root.getChildren().add(allRelatedCourseClasses.get(i).button);
            }
            else
            {
                allRelatedCourseClasses.get(i).createButton(scene);
                allRelatedCourseClasses.get(i).button.setLayoutX(x);
                allRelatedCourseClasses.get(i).button.setLayoutY(y + 100*i);
                allRelatedCourseClasses.get(i).button.getStyleClass().add("buttonLabLecLast");
                root.getChildren().add(allRelatedCourseClasses.get(i).button);
            }
        }
        
        heightSize = allRelatedCourseClasses.size()*100 + 100;
    }
    
    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @return the courseTitle
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * @return the sectionNum
     */
    public int getSectionNum() {
        return sectionNum;
    }

    /**
     * @return the professor
     */
    public String getProfessor() {
        return professor;
    }

    /**
     * @return the labTA
     */
    public String getLabTA() {
        return labTA;
    }
    
    /**
     * toString returns a string containing the course code, and the section number.
     * @return string representation of the class
     */
    @Override
    public String toString()
    {
        return getCourseCode() + " Section: " + getSectionNum();
    }
}
