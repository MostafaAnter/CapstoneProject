#M-Articles<img src="https://github.com/MostafaAnter/CapstoneProject/blob/master/app/src/main/res/mipmap-xxhdpi/ic_launcher.png" width="80"> 

Description 

<img style="position: center;" src="https://github.com/MostafaAnter/CapstoneProject/blob/master/art/output_RJH4vY.gif" width="250">

The M-Articles app brings you the latest English articles reading experience on the go and keeps you informed on happenings from anywhere and around the World, greater personalisation and better overall performance. So you can get more of the articles that matters to you.

Intended User

Every one that interested in reading articles&news.

Features
- Read the best articles from multi correspondents
- Push notifications for breaking news stories
- Improved offline experience. Download the latest stories when you have a signal and then read them at your leisure 
- Share stories to your social networks, or email/SMS to a friend


User Interface Mocks

Screen 1

<img style="position: center;" src="https://github.com/MostafaAnter/CapstoneProject/blob/master/art/device-2016-12-29-233533.png" width="250">

Matrial Splash screen that animate text then end and start main activity that contain all articles


Screen 2

<img style="position: center;" src="https://github.com/MostafaAnter/CapstoneProject/blob/master/art/device-2017-01-02-160643.png" width="250">

Just select source of articles from awesome navigation drawer then you will see magic :)


Screen 3

<img style="position: center;" src="https://github.com/MostafaAnter/CapstoneProject/blob/master/art/device-2017-01-02-160548.png" width="250">

Get the latest Headlines, World News, International Headlines, Global Finance, Sports, NFL, Entertainment, Politics, Technology, Celebrity News - Over 1 million personalized topics in a lightning-fast, material design interface. Just select category from navigation drawer . 

Screen 4

<img style="position: center;" src="https://github.com/MostafaAnter/CapstoneProject/blob/master/art/device-2017-01-02-160618.png" width="250">

Read news full details and share stories to your social networks, or email/SMS to a friend.


Screen 5

<img style="position: center;" src="https://github.com/MostafaAnter/CapstoneProject/blob/master/art/device-2017-01-02-160857.png" width="250">

Widget to provide relevant information to the user on the home screen.

#Key Considerations

###How will your app handle data persistence? 

All the data in the application is sore in a local repository using SQlite. A content provider will be use to connect the local repository with main application. Main entity is news item.

###Describe any corner cases in the UX.

A widget will provide news information to users, when use tab on the widget it will open the application will open detail activity to show user all information and allow user to share news detail with his friends.

###Describe any libraries you’ll be using and share your reasoning for including them.

Retrofit :  to make the http calls.
Butterknife : simply to reduce boilerplate code in my application by inject views (or setting click listeners).

###Describe how you will implement Google Play Services.

Google Ads by integrate the Firebase libraries with adMob id.

#Required Tasks

###Task 1: Project Setup
- Design  all system functions and Implement mvp pattern  
- Design contract of SQlite database
- Write content provider
- Implement SyncAdapter to update data

###Task 2: Implement UI for Each Activity and Fragment
- Build UI for MainActivity
- Build UI for Detail Activity  

###Task 3: Implement Google Ads
- Create new app on AdMob to get id 
- Integrate Firebase libraries with my app.

###Task 4: Create widget to display last news to user
- Create collection widget that display list of news.


