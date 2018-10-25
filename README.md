#Backend for BJJ project

##Get up and running

Install Maven: https://maven.apache.org/

## Run the server with IntelliJ
- Create a new Run/Debug configuration (Upper right corner in IntelliJ)
- Press the green + to add a new configuration
- Choose 'Maven'
- In 'Command line', enter `spring-boot:run`
- You can now run/debug the application
- Check the output console upon start to find the url for the application


## Run server in the command line
Check the pom.xml for the artifact name and then run:
`mvn install && java -jar target/bjjtraining-x.x-SNAPSHOT.jar` (replace x.x-SNAPSHOT for the name in pom.xml)


## Add the database url to system variables
`DATABASE_URL=<get url from heroku>`