/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineclassloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The main backend component of the application
 * @author Andrej Zivkovic
 */
public final class OnlineClassLoader{
    private static File dataFile;
    private static Scanner sc;

    public ArrayList<Course> courseList;
    public ArrayList<CourseClass> allCourseClasses;
    
    private static OnlineClassLoader oclInstance;

    /**
     * initializes the main class and its data.
     */
    private OnlineClassLoader()
    {
        courseList = new ArrayList<>();
        allCourseClasses = new ArrayList<>();
        try{
        dataFile = new File("classData.txt");
        sc = new Scanner(dataFile); }
        catch (FileNotFoundException e) {}

        loadData();
    }
    
    /**
     * Creates a single object of this class and returns the same object every time it is called.
     * @return "this"
     */
    public static OnlineClassLoader getInstance()
    {
        if (oclInstance == null)
        {
            oclInstance = new OnlineClassLoader();
        }
        
        return oclInstance;
    }
    
     /**
     * Loads all of the data in classData.txt. 
     * Format in classData.txt:
        Course Code: string
        Section: integer
        Professor: string
        Class Start Times: string of format "HH:MM"
        Class End Times: string of format "HH:MM"
        Class Days: string of day like "Monday"
        Title: string
        Class link: string of link
        TA: string
        Lab Start Times: string of format "HH:MM"
        Lab End Times: string of format "HH:MM"
        Lab Days: string of day like "Monday"
        Lab link: string of link
        
        For multiple classes, add "--------" and then add the next class info
     */
    public void loadData()
    {
        String courseCode;
        String section;
        String professor;
        String[] classStartTime;
        String[] classEndTime;
        String[] classDay;
        String title;
        String[] classLink;
        String TA;
        String labStartTime;
        String labEndTime;
        String labDay;
        String labLink;
        
        while(sc.hasNext())
        {
            courseCode = sc.nextLine().replace("Course Code: ", "");
            section = sc.nextLine().replace("Section: ", "");
            professor = sc.nextLine().replace("Professor: ", "");
            classStartTime = sc.nextLine().replace("Class Start Times: ", "").split(" ");
            classEndTime = sc.nextLine().replace("Class End Times: ", "").split(" ");
            classDay = sc.nextLine().replace("Class Days: ", "").split(" ");
            title = sc.nextLine().replace("Title: ", "");
            classLink = sc.nextLine().replace("Class link: ", "").split(" ");
            TA = sc.nextLine().replace("TA: ", "");
            labStartTime = sc.nextLine().replace("Lab Start Times: ", "");
            labEndTime = sc.nextLine().replace("Lab End Times: ", "");
            labDay = sc.nextLine().replace("Lab Days: ", "");
            labLink = sc.nextLine().replace("Lab link: ", "");
            
            Lab labTemp = new Lab(labStartTime, labEndTime, labDay, labLink, TA);
            Lecture[] lecTemp = new Lecture[classDay.length];
            
            for (int i = 0; i < classDay.length; i++)
            {
                if (classLink.length == 1)
                {
                    lecTemp[i] = new Lecture(classStartTime[i], classEndTime[i], classDay[i], classLink[0], professor);
                }
                else
                {
                    lecTemp[i] = new Lecture(classStartTime[i], classEndTime[i], classDay[i], classLink[i], professor);
                }
            }
            
            Course courseTemp = new Course(courseCode, title, Integer.parseInt(section), lecTemp, labTemp);

            allCourseClasses.addAll(Arrays.asList(lecTemp));
            allCourseClasses.add(labTemp);
            
            courseList.add(courseTemp);
            
            if (sc.nextLine().equals("--------"))
            {
                // System.out.println("Inputted: " + courseTemp.toString());
            }
        }
    }
    
    /**
     * Finds which course a given cClass is a part of.
     * @param cClass a class
     * @return course
     */
    public Course getCourse(CourseClass cClass)
    {
        for (Course course : courseList) 
        {
            for (CourseClass specific : course.allRelatedCourseClasses) 
            {
                if (specific.equals(cClass))
                {
                    return course;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Finds the class that is not running currently but is closest to current
     * system time.
     * @return a course class
     */
    public CourseClass nextCourseClass()
    {
        boolean allNegative = false;
        int counter = 0;
        for (CourseClass c : allCourseClasses) // sees if all of the classes have been passed
        {
            if (c.score < 0)
            {
                counter++;
            }
            if (counter == allCourseClasses.size())
            {
                allNegative = true;
            }
        }
        if (allNegative == false) // finds the next weeks class in case all of this weeks classes are done
        {
            ArrayList<CourseClass> nonNegativeScore = new ArrayList<>();
        
            for (CourseClass check : allCourseClasses)
            {
                if (check.score >= 0)
                {
                    nonNegativeScore.add(check); 
                }
            }
                    
            CourseClass closestClass = nonNegativeScore.get(nonNegativeScore.size() - 1);

            for(int i = 0; i < nonNegativeScore.size(); i++)
            {
                if (closestClass.score > nonNegativeScore.get(i).score && nonNegativeScore.get(i).score > 0)
                {
                    closestClass = nonNegativeScore.get(i);
                }
            }

            return closestClass;
        }
        else // finds the next class this week
        {
            CourseClass closestClass = allCourseClasses.get(allCourseClasses.size() - 1);
            
            for(int i = 0; i < allCourseClasses.size(); i++)
            {
                if (Math.abs(closestClass.score) < Math.abs(allCourseClasses.get(i).score))
                {
                    closestClass = allCourseClasses.get(i);
                }
            }

            return closestClass;
        }
    }
    
    /**
     * Finds the class that is running currently according to system time.
     * @return a course class
     */
    public CourseClass currentCourseClass()
    {
        for (CourseClass check : allCourseClasses)
        {
            if (check.isRightNow())
            {
                return check; 
            }
        }
        
        return null;
    }
}
