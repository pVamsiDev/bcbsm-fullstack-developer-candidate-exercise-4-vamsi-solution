# BCBSM Full Stack Developer Candidate Coding Exercise

## Requirements:
1.	Create a single page web application with a login screen (username, password)
2.	Demonstrate user login and authentication - Spring Security
3.	Upon login present member with ability to compress the multiple files and download it as a ZIP/RAR file, User must be login to access this page
4.	Display success or Failure message on web page, once a file downloaded successfuly or failed.
5.  Fork this repository and we will review your code from the fork.
6.  Mandatory to provide a code and workable application walk through 

## Tech Stack:  
    Springboot  
    Angular  
    MongoDB
    
ReadMe

Files Manager

•    Files Manager is a single page application which is built using Springboot, Angular and MongoDB.
•    The User Interface has three screens Registration page for user registration, Login page for user login and Home page which presents the user to upload single or multiple files to get a downloaded ZIP folder.
•    Responsive web design with basic styling has been implemented at the front end.
•    The Server side has two controllers, AuthController which implements the signup , signin requests and FileUploadController which handles the file upload and gives the zipped folder version as output.
•    Authentication and Authorization of the application has been achieved using Spring Security, Spring Data MongoDB and JWT.
•    A video demo has attached on how the application works which is placed in the root folder along with the client side and server side code.

Steps for Running the application in local
MongoDB
•    Connect to the MongoDB URI mongodb://localhost:27017
Back End
•    Run mvn clean install
•    Run Spring Boot application with mvn spring-boot:run
Front End 
•    Install the node modules with the help of npm install.
•    Start the client side by executing ng serve in the front end.
•    Access the application at http://localhost:4200/




