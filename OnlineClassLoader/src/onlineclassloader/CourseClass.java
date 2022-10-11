/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineclassloader;

import java.util.Calendar;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * Abstract class representation of course class
 * @author Andrej Zivkovic
 */
public abstract class CourseClass extends Course{
    protected String timeStart;
    protected String timeEnd;
    protected String dayOn;
    protected String link;
    protected String type;
    public int score = 605000; // large score so that it can only realistically be set to go down
    
    protected Button button;
    
    public int courseLengthInSec;
    
    /**
     * Creates a course class
     * @param timeStart a string with the time in format "HH:MM" for the course starting time
     * @param timeEnd a string with the time in format "HH:MM" for the course ending time
     * @param dayOn a string with the name of the day ie "Monday" that the course is on in the week
     * @param link a link to join the class, like a zoom link
     */
    public CourseClass(String timeStart, String timeEnd, String dayOn, String link) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dayOn = dayOn;
        this.link = link;
        
        courseLengthInSec = getHour(timeEnd)*60*60 + getMinute(timeEnd)*60 - (getHour(timeStart)*60*60 + getMinute(timeStart)*60);
        updateScore();
    }
    
    /**
     * Returns a boolean depending or not if the class is currently happening.
     * @return true if the class is happening at the current system time, false otherwise
     */
    public boolean isRightNow()
    {
        return 0 >= score && score >= -courseLengthInSec;
    }
    
    /**
     * Depends on the OnlineClassLoader's set of classes, returns a boolean if "this"
     * course is next to be taken.
     * @return true if the course is next, false otherwise
     */
    public boolean isNext()
    {
        return OnlineClassLoader.getInstance().nextCourseClass().score == this.score;
    }
    
    /**
     * Updates the score of the class, depending on system time.
     */
    public final void updateScore()
    {
        score = timeBetweenSundayToLecture() - timeBetweenSundayMidnightToNow();
    }
    
    /**
     * @return difference in seconds between current system time and sunday midnight/start of the week
     */
    public static int timeBetweenSundayMidnightToNow()
    {
        Calendar c = Calendar.getInstance();
        
        int differenceInSec = 0;
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)- 1;
        int secondsSinceMidnight = c.get(Calendar.HOUR_OF_DAY)*60*60 + c.get(Calendar.MINUTE)*60 + c.get(Calendar.SECOND);
        
        differenceInSec = dayOfWeek*24*60*60 + secondsSinceMidnight;
        
        return differenceInSec;
    }
    
    /**
     * @return difference in seconds between the set class time and sunday midnight/start of the week
     */
    public int timeBetweenSundayToLecture()
    {
        int differenceInSec = 0;
        int dayOfWeek = getDayOfWeekInt(dayOn) - 1;
        int secondsSinceMidnight = getHour(timeStart)*60*60 + getMinute(timeStart)*60;
        
        differenceInSec = dayOfWeek*24*60*60 + secondsSinceMidnight;
        
        return differenceInSec;
    }
    
    /**
     * Converts a string of day to int: "Sunday"      -> 1,
     *                                  "Monday"      -> 2, 
     *                                  "Tuesday"     -> 3, 
     *                                  "Wednesday"   -> 4, 
     *                                  "Thursday"    -> 5, 
     *                                  "Friday"      -> 6, 
     *                                  "Saturday"    -> 7
     * else returns a printed warning and an int of -1
     * @return an integer corresponding to the day of the week
     */
    public int getDayOfWeekInt(String day)
    {
        switch (day)
        {
            case ("Sunday") :
                return 1;
            case ("Monday") :
                return 2;
            case ("Tuesday") :
                return 3;
            case ("Wednesday") :
                return 4; 
            case ("Thursday") :
                return 5;
            case ("Friday") :
                return 6;
            case ("Saturday") :
                return 7;
            default :
                System.out.println("WARNING: DAY OF WEEK INT NOT FOUND | CLASS: Lecture");
                return -1;
        }
    }
    
    /**
     * @param time string of time in format "HH:MM"
     * @return the integer of the "HH" portion of the given string
     */
    public static int getHour(String time)
    {
        String[] splitTime = time.split(":");
        int hour = Integer.parseInt(splitTime[0]);
        
        return hour;
    }
    
    /**
     * @param time string of time in format "HH:MM"
     * @return the integer of the "MM" portion of the given string
     */
    public static int getMinute(String time)
    {
        String[] splitTime = time.split(":");
        int minute = Integer.parseInt(splitTime[1]);
        
        return minute;
    }
    
    /**
     * Returns a string that converts to 12 hour time and appends an AM or PM 
     * depending on if it's afternoon or not.
     * @param hour integer of the hour
     * @param minute integer of the minute
     * @return a string of format "HH:MM" + "PM" or "AM"
     */
    public static String to12HourTimeString(int hour, int minute)
    {
        String meridiem;
        
        if (hour > 12)
        {
            hour -= 12;
            meridiem = "PM";
        }
        else
        {
            meridiem = "AM";
        }
        
        if(0 <= minute && minute <= 9)
        {
            return hour + ":0" + minute + meridiem;
        }
        else
        {
            return hour + ":" + minute + meridiem;
        }
    }
    
    /**
     * Creates a button for "this" class used in the JavaFX application
     * @param scene JavaFX scene for window
     */
    public abstract void createButton(Scene scene);
    
    /**
     * @return string representation of the class
     */
    @Override
    public String toString()
    {
        return type + ": " + timeStart + " to " + timeEnd + " on " + dayOn;
    }
}
