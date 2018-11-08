# Backend for BJJ project

## Get up and running

Install Maven: https://maven.apache.org/

## Run the server with IntelliJ
- Create a new Run/Debug configuration (Upper right corner in IntelliJ)
- Press the green + to add a new configuration
- Choose 'Maven'
- In 'Command line', enter `spring-boot:run`
- You can now run/debug the application
- Check the output console upon start to find the url for the application

## (Optional) Add environment variables
The application searches for an enviroment variable named `SECRET_KEY` for the authentication. If none is found it creates a default value, which is **not** secure.
Create one that contains a random string to secure the application.

If you want to connect to a specific database, you need to add the `DATABASE_URL` enviroment variable, otherwise the application will connect to a local H2 database 

## Run server in the command line
Check the pom.xml for the artifact name and then run:
`mvn install && java -jar target/bjjtraining-x.x-SNAPSHOT.jar` (replace x.x-SNAPSHOT for the name in pom.xml)
