# demo
A RESTful demo including a POM, an EAR, an EJB and a WAR projects.

The noteKeepr branch contains noteKeepr.zip which is a finished project using the stucture of this demo project. To view run the code, unzip noteKeepr.zip and import as maven projects by following these steps:
1. Import the POM project
2. Import the EJB project
3. Import the WAR project
4. Import the EAR project
In EJB project, there is an SQL script to create batabase table structure in MySQL.
JPA setting are need in web server.
Although, most full featured Java EE application servers should be good to deploy the project, it is originally targeted to run on payara or wildfly.
