# Weather-Reporting-Comparator

The project is to capture any City Temperature data from ndtv.com and from oneweathermap.com and perform comparison using comparator logic.If the difference between the 2 data is greater that 3 the test will fail.The project develop on Data Driven model. Tools used to devloped the project are as follows:

# Software & Tools:

- Java 1.8 - Programming Language
- Selenium WebDriver - For UI test
- Maven - Build tool 
- RestAssured - For API testing
- Log4j2 - For log information
- TestNG - For reporting tool
- Eclipse(Version: 2020-06 (4.16.0)) -IDE


# Setup Steps

- Clone the project in local directory and import in Eclipse.
- Update the Maven Project
- Configure log4j2 in build configuration
- There is one config .properties file which store the setup configuration details
- locator.properties file which store all configuration details
- On Execution 'logs' folder will be created and 'output' folder stores the screenshot
- 'testData' folder contains excel file to store temperature value for comparision purpose.

# Project Structure

src/main/java - contains 3 packages 
 - com.base 
    - BaseClass.java ( contains all This a Base class which holds all the common functionality for performing the test)
 - com.utilitypackage 
    - ExcelOperation.java [Generic excel function to read and write]
 - com.weather 
    - CompareData.java [Class to perform temperature value comparison]


# Execution Steps:

- Click on 'testng.xml' file and Run As - TestNG Suite
- Alternate way is to click on pom.xml file and select 'Maven test'
- Another alternate way is to open 'cmd' from the project location and execute - 'mvn test'
- 'testng.xml' - files has 6 test files  divided into 2 sets - one set execute paralley and other in sequence










