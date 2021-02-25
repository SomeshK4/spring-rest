 # Introduction
 This project has been developed using Spring Boot 2.4.2, it implements RESTful API which in turn make use of RestTemplate to call the remote server APIs to fetch the actual data.
 
 ## Technologies and tools used for this development
 * Spring Boot 2.4.2
 * Spring Security
 * JDK 11
 * JUnit 
 * Swagger 2
 * Maven
 * IntelliJ IDE
 
 ## Setup
 * Download JDK, Maven, IDE of your choice and set Environmet variables viz. JAVA_HOME, MAVEN_HOME
 * To run the project, clone this repo, and go to the /assignment folder.
 * Run ```mvn clean install ``` to build the project
 * Execute ```mvn spring-boot:run``` command in our project root folder /assignment . This initializes the start up of Tomcat on port 8080.
 * Once the application is UP and running on port 8080, follow below steps
  
 ### Swager UI 
  * Use CTRL+Click on this link to open Swagger UI <a href="http://localhost:8080/swagger-ui.html" target="_blank">Swagger-UI</a> in new tab
  * Click on Authorize button, when prompted for Basic Authorization enter credentials as follows
  * Username: user and Password: passowrd and click on Authorize button, and close the prompt after authorization.
  * Click on api-controller below the Authorize button to expand the list of APIs as follows
 #### Get list of countries
 * Click on this link GET /api/v1/countries, click on Try it out button, enter "name" in the text for field_name, and click on Execute button below this text. Check the reponse received is as expected
   
 #### Get airport for a given city
 * Click on this link GET /api/v1/single, then click on Try it out button, enter "LON" in the text for Iata, and click on Execute button below this text. Check the reponse received is as expected.     
 #### Get list of states for a given country
 * Click on this link GET /api/v1/states, then click on Try it out button, enter "IN" in the text for country, and click on Execute button below this text. Check the reponse received is as expected.

 ### List of API's implemented to fetch Countries, States and Airport.
 * Use CTRL+Click on this link <a href="http://localhost:8080/api/v1/countries?field_name=name" target="_blank">Countries</a> to open in new tab
 * Use CTRL+Click on this link <a href="http://localhost:8080/api/v1/states?country=IN" target="_blank">States</a> to open in new tab
 * Use CTRL+Click on this link <a href="http://localhost:8080/api/v1/single?iata=LON" target="_blank">Airport</a> to open in new tab
 
 ### List of target APIs used for the implementation
  * [Countries](https://www.air-port-codes.com/api/v1/countries?field_name=name).
  * [States](https://www.air-port-codes.com/api/v1/states?country=IN).
  * [Airport](https://www.air-port-codes.com/api/v1/single?iata=LON).
 
 ### Test the API as mentioned below
 #### Happy Path
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:password 'http://localhost:8080/api/v1/single?iata=LON'```
 
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:password 'http://localhost:8080/api/v1/countries?field_name=name'```
 
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:password 'http://localhost:8080/api/v1/states?country=IN'```
 
 #### Sad Path
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:passwords 'http://localhost:8080/api/v1/single?iata=LON'```
 
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:passwords 'http://localhost:8080/api/v1/countries?field_name=name'```
 
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:passwords 'http://localhost:8080/api/v1/states?country=IN'```

 #### Bad Path
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:password 'http://localhost:8080/api/v1/'```
 
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:password 'http://localhost:8080/api/v1/'```
 
 * ```curl -kX GET --header 'Content-Type: application/json' --header 'Accept: application/json' --user user:password 'http://localhost:8080/api/v1/'```

 ## Security
  * Current implementation is using basic Auth mechanism provided by Spring Security. We are using an in-memory manager with the user and password defined in plain text. 
  * Sensitive information like credentials used for basic authentication and security headers (secret key value) used to call the remote API using RestTemplate, has been stored in application.properties in plain text.
  * Security needs be improved in future.