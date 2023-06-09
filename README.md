# Mini-Procure To Pay
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Description
A Client requested an application which will provide their buyers the flexibility to create a purchase order requisition for indirect goods and services like marketing, media and advertising services or travel, entertainment, or facilities services and converting that request into a purchase order. With more and more of their suppliers requesting cash on delivery, our client is researching ways of streamlining their process of making indirect purchases while maintaining some efficiencies and accountability in their accounts payable process.  With a COD payment terms, the client can pay their suppliers online at the time goods and services are delivered and connect the entire lifecycle of procurement from one phase to the next.


# Table of Contents
- [Project Planning](#project-planning)
- [User Stories](#user-stories)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Questions](#questions)
- [Credits](#credits)
- [Contributing](#contributing)

## Project Planning
GitHub Projects was instrumental in keeping on track and focused on the many tasks to complete and/or rethink for the project. During the brainstorm exercise, an ERD and wire-frame was created based on the user stories provided by the client.  After meeting with the client to go over those artifacts and obtain sign-off, each user story would become a task/todo within the project tool.  
![Project Image 1](./images/Project_P2P_API-1.png)
![Project Image 2](./images/Project_P2P_API-2.png)
![Project Image 3](./images/ERD_P2P.png)

A decision was made to divide the project up into phases for the backend application (API) and the frontend application (Client), as well.  There would be a combination of the API and Client applications in each phase.  The first phase for the API would be creating the necessary components and endpoints.

| Request Type | URL                        | Functionality              |  
|--------------|----------------------------|----------------------------|
| GET          | api/employee/              | Get all employees          |
| GET          | api/employee/{employeeId}/ | Get a specific employee    |
| POST         | api/employee/              | Create an employee         |  
| PUT          | api/employee/{employeeId}/ | Update a specific employee |   


| Request Type | URL                        | Functionality              |  
|--------------|----------------------------|----------------------------|
| GET          | api/suppliers/             | Get all suppliers          |
| GET          | api/suppliers/{supplierId}/ | Get a specific supplier    |
| POST         | api/suppliers/             | Create a supplier          |
| PUT          | api/suppliers/{supplierId} | Update a specific supplier |
| DELETE       | api/suppliers/{supplierId} | Delete a specific supplier |

| Request Type | URL                 | Functionality                   |  
|--------------|---------------------|---------------------------------|
| GET          | api/items/          | Get all item favorites          |
| GET          | api/items/{itemId}/ | Get an item favorite            |
| POST         | api/items/          | Create an item favorite         |
| PUT          | api/items/{itemId}/ | Update an item favorite         |
| DELETE       | api/items/{itemId}/ | Delete a specific item favorite |

| Request Type | URL         | Functionality |  
|--------------|-------------|---------------|
| GET          | api/po-req/ | Get all reqs  |


## User Stories

1. Users should be able to create and maintain an employee record.
2. Users should be able to set an employee role as DOA (delegation of authority) or Buyer. 
2. User should be able to assign the DOA (delegation of authority) to a specific GL (General Ledger) expense account.
3. Users should be able to create and maintain a supplier record.
4. Users should be able to create and maintain an item-favorites record.
3. Users should be able to see a listing of all purchase order requisitions.

## Usage

Using an API platform like Postman, you can access all operational endpoints of the Procure-To-Pay application which are available on port 8080.  For now, the H2 in memory database was chosen for this proof of concept; however, the endpoints will be consumed by a client application.

### To Get All Employees (Employee Controller):
```
    /**
     * Endpoint calls a method which obtains listing of all employees
     * @return ResponseEntity to configure HTTP response to be NOT_FOUND when no employees are found and OK when employee results are found.
    */
    @GetMapping(path="/employees/")
    public ResponseEntity<?> getAllEmployees() throws InformationNotFoundException {
        List<Employee> employeeList = employeeService.getAllEmployees();
        if (employeeList.isEmpty()){
            message.put("message", "cannot find any employees");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", employeeList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
   }
```
### To Get A Specific Supplier (Supplier Controller):
```
    /**
     * Endpoint calls method to obtain a specific supplier based on supperId.
     * @param supplierId Specified id of supplier.
     * @return A httpStatus of 200 if able to find specific supplier and status of 404 if not able to find the supplier specified.
     * @throws InformationNotFoundException Respond with custom message.
    */
    @GetMapping(path="/suppliers/{supplierId}")
    public ResponseEntity<?> getSupplierById(@PathVariable(value="supplierId") Long supplierId) throws InformationNotFoundException {
        Optional<Supplier> supplier =  supplierService.getSupplierById(supplierId);
        if (supplier.isPresent()) {
            message.put("message", "success");
            message.put("data", supplier);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find any supplier with id " + supplierId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
```

### To Create A New Item Favorite (ItemsFavorite Controller)
```
    /**
     * Endpoint calls method to create a new favorite item record.
     * @param itemFavoritesObject Favorite item data to create new item record.
     * @return Response stat us of 201 (CREATED) when successfully created and status of 404 when not able to create
    */
    @PostMapping(path="/items/")
    public ResponseEntity<?> createItemFavorites(@RequestBody ItemFavorites itemFavoritesObject) throws InformationNotFoundException {
        ItemFavorites itemFavorites = itemFavoritesService.createItemFavorites(itemFavoritesObject);
        if (itemFavorites == null) {
            message.put("message", "cannot find create item favorite");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", itemFavorites);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }
```

### To Get A Listing Of All Purchase Order Requisition Records (POReqHeader Controller)
```
    /**
     * Method will call service to obtain a listing of all purchase order reqs.
     * @return List of all purchase order requisitions records.
    */
    @GetMapping(path="/po-req/")
    public ResponseEntity<?> getPOReqs() {
        List<POReqHeader> poReqLists = poReqHeaderService.getPOReqs();
        if (poReqLists.isEmpty()) {
            message.put("message", "cannot find any purchase reqs");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", poReqLists);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
```

### To Update A Specific Employee Record
    /**
    * Endpoint calls a method which creates a new employee record.
    * @return Response of HttpStatus CREATED when new employee record is created and HttpStatus of OK when a new employee record cannot be created.
    */
    @PostMapping("/employees/")
      public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
      Employee newEmployee = employeeService.createEmployee(employee);
        if (newEmployee != null){
          message.put("message", "success");
          message.put("data", newEmployee);
          return new ResponseEntity<>(message, HttpStatus.CREATED);
          } else {
          message.put("message", "unable to create an employee at this time");
          return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
```

## Technologies Used

- Java 17
- Spring Boot 2.7.8
- H2 Database
- MockMvc
- PostMan
- Lucidchart

## Installation

1.  Clone the repository.
1.  Ensure you are using JDK 17, Spring Boot 2.7.8 and Maven 4.x
1.  Using IntelliJ IDEA, open the pom.xml and ensure all necessary dependencies have been installed.
1.  To run the project, navigate to the Procure2payApplication under /src/main/java/com.avery.procure2pay and right-click the file and choose run Procure2payApplication from the popup menu.  Or you can double-click on the file to open it and click the 'Run' option (green caret).
1.  Once the application is running you should see something like this:
```
   2023-04-30 12:48:03.906  INFO 71465 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
   2023-04-30 12:48:03.906  INFO 71465 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.71]
   2023-04-30 12:48:03.940  INFO 71465 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
   ```

https://github.com/users/wmsimien/projects/2
## Credits

My tech gains are due to the outstanding instructors involved with the 13-week Full-Stack Immersive.  For the many people praying and supporting me from the start and through to the end, whenever that may come.  For all, and I mean all, of my classmates in this cohort, I appreciate working with and learning from you all.  I am thankful for you all.

## Contributing

There are no current 'unsolved' issues; however,  any contributing suggestion(s) or bug notification(s) is greatly appreciated.  Reporting
any bug can be submitted via email, see Questions section below for contact information.  
Please put [Bug Report] in the subject section of the email.  Within the email, please
provide details of the bug(s) being reported.  All email correspondences will be replied
back to within a timely manner.  For feature suggestions, please fork the repo and create
a pull request.  Thanks.

1.  Fork the project/repo into your own GitHub account and create a local clone.
1.  Create your feature branch (git checkout -b yourfeaturefolder/YourFeaturName)
1.  Commit your changes (git commit -m 'Add your comment here regarding your feature).
1.  Push to the branch (git push origin yourfeaturefolder/YourFeaturName).
1.  Open a pull request, which will be responded to in a timely manner.

## License

[MIT License](https://opensource.org/licenses/MIT) A short and simple permissive license with conditions only requiring preservation of
copyright and license notices. Licensed works, modifications, and larger works may be distributed under different
terms and without source code.

## Questions

Send all questions/comments to:
| GitHub: wmsimien https://github.com/wmsimien                                  
|--------------------------------------------------------------------------------
| Email:  wanda.avery@att.net

## Test

Testing of this application is a success when you are able to successfully access all endpoints as a registered user of FTBAPI after having completed the installation steps successfully.  I hope you enjoy the Family Time Beverage API.
