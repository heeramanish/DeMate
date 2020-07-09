# DeMate
An Android application made on Android studio IDE. 
# am3dementia


Problem        
Dementia patients diagnosed with early or mild stages of Alzheimer’s aged 65-74 across Victoria are unable to perform any physical activities due to prolonged stay at home and decline in cognitive ability, leading to depression and further deterioration of memory due to limited blood circulation to the brain.

Solution
The application aims to provide a platform that will help dementia patients stay physically active by recommending them with low intensity indoor exercises. They can add the exercises  to the daily task after viewing the benefit of each type. In case they forget to exercise according to plan, the app provides a reminder functionality.

The main functions of the platform are listed below:
• Organise a shareable exercise plan with caretakers.
• Provide the benefit of each exercise type.
• Provide some exercise instruction video to guide patients on how to do exercise independently.
• Provide functions to allow patients to add tasks and set them as reminders.
• Frequency of reminder could be set, on daily,weekly and monthly basis to ensure the patient remembers to exercise
• Provide data visualization and info-graphic for caretaker to understand the current situation of dementia in Australia and benefits of exercising to improve cognitive abilities

# Functionalities:

About Dementia - Facts , Infographics about dementia in Australia,Benefits of Exercise to reduce Dementia and Interactive Charts to depict the current scenario in state of Victoria.

Find Help Centres - Feteches the Dementia Care Centres in Victoria and shows them on a map with details on map , list view and ability to call/ Navigate to the place.

Exercise - Ability to view various types of Low- Intensity Exercises, Add Task Reminders based on Benfit, Category and Sub-Category of Exercise required. 

Reminders- Can be set via Voice Reminder button, can select daily , weekly or monthly reminders.

Update Reminders/Tasks-  The tasks can be edited or deleted to change the schedule based on their convinience.

Filter Tasks based on daily,weekly or monthly basis.

Watch Video tutorials on "My Video Tutorials" page.


# Technical details: 
The folder structures consits of:
1) Manifest folder to define the manifest xml to ensure the permissions are obtained and the application runs with the correct sequence.

2)Java classes-The file has 17 main Java Class activities listed as part of the IE.walkmate.AM3Dementia.activites, it has an SQL and Model Class folder to connect with the SQlite Database.

3)Assets- Consists of the SQLite DB

4)Res- Front End/ XML code for UI

5) Gradle files to synct and build project with dependencies


# Pre-requistes: 
Before running the file , please install Android studio and set up a emulator using AVD managaer.
Steps to run the file:
1) Clone the repo to your local system.

2) Open the project on your local system using Android Studio and then wait for the gradle sync to proceed.

3)Use API level 26 Onwards, test phone emulator- Google Pixel 3a API 29/R

Screenshots:


Home Page:

![Homepage1](https://user-images.githubusercontent.com/58926289/80381140-d58d9180-88e3-11ea-94c0-a3e5ff09367d.PNG)

About Dementia:

![image](https://user-images.githubusercontent.com/58926289/80381547-6c5a4e00-88e4-11ea-97ff-08386c8d585f.png) ![image](https://user-images.githubusercontent.com/58926289/80381741-ba6f5180-88e4-11ea-8468-4d053f04b98d.png) ![image](https://user-images.githubusercontent.com/58926289/80382082-3073b880-88e5-11ea-9377-c0880a871d9b.png) 

Find Dementia Centres Tab:

![image](https://user-images.githubusercontent.com/58926289/80382281-74ff5400-88e5-11ea-9bd8-3dd211b2f2a6.png) ![image](https://user-images.githubusercontent.com/58926289/80385789-db867100-88e9-11ea-9ceb-c220a7944fc6.png)





Set Reminder by Adding Task - Type of Exercise you want to do:

![image](https://user-images.githubusercontent.com/58926289/80382755-01aa1200-88e6-11ea-89b3-c64648882e11.png) ![image](https://user-images.githubusercontent.com/58926289/80382905-3d44dc00-88e6-11ea-854a-61b48d35ea0a.png)

![image](https://user-images.githubusercontent.com/58926289/80383384-d83db600-88e6-11ea-815a-9f0539ac6db4.png) ![image](https://user-images.githubusercontent.com/58926289/80383631-2bb00400-88e7-11ea-8463-0ffce4917ad3.png)

![image](https://user-images.githubusercontent.com/58926289/80385481-703c9f00-88e9-11ea-8da7-7170abb62c39.png) ![image](https://user-images.githubusercontent.com/58926289/80444822-060e1380-8956-11ea-9c02-634f2f06992d.png)



