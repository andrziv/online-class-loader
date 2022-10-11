# online-class-loader

## Introduction
The application was made using the Netbeans ide and jdk version 1.8, because that one comes bundled with JavaFX, which is how the graphics
were made.  
An application I made to make joining online classes easier. It is a simple application that reads from a txt file containing information
on the courses you are taking, and lays them onto a small window so you can click on the lecture or lab and join the class. The information
needs to be entered manually by hand, but the format isn't too difficult to get used to. 

### classData.txt Format
The "--------" at the bottom is important as it signifies the end of the class info.

Example below:  
Course Code: COO444  
Section: 5  
Professor: Teacher Teach  
Class Start Times: 18:00  
Class End Times: 21:00  
Class Days: Thursday  
Title: Algorithms  
Class link: https://www.google.ca/  
TA: Teacher Assist  
Lab Start Times: 12:00  
Lab End Times: 14:00  
Lab Days: Wednesday  
Lab link: https://www.google.ca/  
`--------`  
(if you want to add more classes, just do the same thing above again after the -------)
