# warehouse management project with spring boot and maven
this project is all about managing the products in a warehouse


Spring Boot "Microservice" Example Project
This is a Java / Maven / Spring Boot (version 2.1.0) application


project includes an executable shell-script file called
"warehouse" at the root of the project directory which builds the code, runs tests/specs (packages application as jar), then runs the
program on embadded tomcat.
and opens an shell where we can intract with the application by running different provided custom commands and
get the results 


How to Run

Clone this repository
Make sure you are using JDK 1.8 and Maven 3.x
runnig on linux
  go to the projects root directory and execute
    ./warehouse
    
 it will builds the code, runs tests/specs (packages application as jar), then runs the
program on embadded tomcat.
and opens an shell where we can intract with the application by running different provided custom commands and
get the results 
   
   you can use the commands (see commands below with input and output formats)like:
   
Input:    
warehouse 6
Output:
Created a warehouse with 6 slots

Input:
store 72527273070 White
Output:
Allocated slot number: 1

Input:
store 72527273071 Green
Output:
Allocated slot number: 2

Input:
store 72537113170 Purple
Output:
Allocated slot number: 3

Input:
store 72537113171 Black
Output:
Allocated slot number: 4

Input:
store 72537113172 Black
Output:
Allocated slot number: 5

Input:
store 72537113173 Green
Output:
Allocated slot number: 6

Input:
sell 4
Output:
Slot number 4 is free

Input:
status
Output (tab delimited output):
Slot No. Product Code Colour
1 72527273070 White
2 72527273071 Green
3 72537113170 Purple
5 72537113172 Black
6 72537113173 Green

Input:
store 82537113174 Purple
Output:
Allocated slot number: 4

Input:
store 82527273075 White
Output:
Warehouse is full

Input:
product_codes_for_products_with_colour White
Output:
72527273070, 82527273075

Input:
slot_numbers_for_products_with_colour Green
Output:
2, 6

Input:
slot_number_for_product_code 82537113174
Output:
4

Input:
slot_number_for_product_code 82532222174
Output:
Not found
   

   
 running on windows:
 
 You can build the project and run the tests by running "mvn clean package"
Once successfully built, you can run the service by one of these two methods:

        java -jar -Dspring.profiles.active=test target/spring-boot-rest-example-0.5.0.jar
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
 

About the Service 

The service is warehouse amnagement exposed as REST service. It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in  com.example.warehouse.warehouse.controller on port 8001. (see below)

POST http://localhost:8001/warehouse?wareHouseCapacity=1 
POST http://localhost:8001/store 

	body {
	"productCode":"72527273070",
	"productColor":"green"
}

GET http://localhost:8001/status
GET http://localhost:8001/product_codes_for_products_with_colour?productColor=green
GET http://localhost:8001/slot_number_for_product_code?productCode=72527273070 
GET http://localhost:8001/slot_numbers_for_products_with_colour?productColor=green 
DELETE http://localhost:8001/sell?slotNo=1 
