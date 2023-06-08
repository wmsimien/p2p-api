# Mini-Procure To Pay
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Description
A Client requested an application which will provide their buyers the flexibility to create a purchase requisition for indirect goods and services like marketing, media and advertising services or travel, entertainment, or facilities services and turning that request into a purchase order. With more and more of their suppliers requesting cash on delivery, our client is researching ways of streamlining their process of making indirect purchases while maintaining some efficiencies and accountability in their accounts payable process.

# Table of Contents
- [Project Planning](#project-planning)
- [User Stories](#user-stories)
- [ERD](#erd)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Questions](#questions)
- [Credits](#credits)
- [Contributing](#contributing)

## Project Planning
GitHub Projects was instrumental in keeping on track and focused on the many task to complete and/or rethink. A task/todos was created for needed starting with obtaining/creating the user stories and detailing those requirements out on paper and generating an ERD diagram.  From there the project was created, then the database and models/tables.  Building the correct relationships between the tables was keen in creating the needed endpoints.  After completing each task, I moved them from 'In Progress' to 'Complete' with GitHub Projects which kept me moving forward without needing to consider what task/step was up next.  After all the endpoints was working as designed to meet requirements, security, JWT and user features were added ensuring private endpoints where private and only the two public endpoints where public. After adding the user to each endpoint ensuring they meet each requirement as designed, I moved onto documentation.  Ensuring all objects were documented and needed elements/sections were covered in the README brought the project a completion.


## User Stories
https://docs.google.com/document/d/1sIzwKBlCLPSUYdU6CzwdtqNyLfoyN84VbOtsOaX6tjw/edit?usp=sharing

1. As an unregistered user of the Family Time Beverages API (FTBAPI), I should only have access to the following public urls/endpoints: /auth/users/register and /auth/users/login.
1. As an unregistered user of FTBAPI, I want the ability to create a new user to become a registered user.
1. As a registered user of FTBAPI, I should be able to log in and then have access to the other endpoints.
1. As a registered user of FTBAPI, I should be able to create my beverage types enabling me to have beverage types of my choosing.
1. As a registered user of FTBAPI, I should be able to see a listing of all beverages types and assigned beverages in order to easily see that listing created by me.
1. As a registered user of FTBAPI, I should be able to update any of my beverage types and change the beverage type to another name of my choosing.
1. As a registered user of FTBAPI, I should be able to delete any of my beverage types of my choosing.
1. As a registered user of FTBAPI, I should be able to create my own beverages under any available beverage types with the following information:  name, description, pairing, goodToKnow, and proTip.
1. As a registered user of FTBAPI, I should be able to update my own beverage description, pairing, goodToKnow, and proTip.
1. As a registered user of FTBAPI, I should be able to delete any beverage created by me to prevent it from showing up in any listing.
1. As a registered user of FTBAPI, I should be able to see a listing of all beverages created by me.
1. As a registered user of FTBAPI, I should be able to see a listing of all beverages for a specific type created by me.
1. As a registered user of FTBAPI, I should be able to obtain a beverage for a specific type created by me.

## Usage

Using an API platform like Postman, as a registered user, you can access all operational endpoints of the Family Time Beverage API (FTBA) which are available on port 9092.  PostgresSQL is the database used and a new 'Database' entry, named beverages, needs to be created.  PGAdmin is a web-based GUI which can be used for this task as it can communicate with your Postgres database.

![FTBAPI ERD](./src/main/resources/assets/FTBAPI_ERD.png)

An unregistered user can only access the register endpoint as follows:
### #2 To Create a New User (Register):
```
localhost:9092/auth/users/register/

{
    "userName" : "jane",
    "email": "jane@aol.com",
    "password": "jane"
}
```
A registered user can accessc the login endpoint as follows:
### #3 To Login As a Registered User:
```
localhost:9092/auth/users/login/

{
    "email":"jane@aol.com",
    "password":"jane"
}
```

Once the registered user logs in, a JWT will be returned which will be needed to access all the other endpoints.  The JWT will need to be added to every request/endpoint headers section with 'Authorization' as a Key and the Value will need to be Bear [JWT] as follows:
![Postman Headers](./src/main/resources/assets/postman_headers.png)

### #4 Create A Beverage Type
```
POST localhost:9092/api/beverage-type/
{
    "name":"MaeMae-Tea Drinks"
    
}

RESPONSE 200 OK
{
    "id": 29,
    "name": "MaeMae-Tea Drinks",
    "beverageList": null
}
```

### #5 Listing of All Beverage Types
```
GET localhost:9092/api/beverage-type/

RESPONSE 200 OK
[
    {
        "id": 29,
        "name": "MaeMae-Tea Drinks",
        "beverageList": []
    }
]
```

### #6 Update A Specific Beverage Type
```
PUT localhost:9092/api/beverage-type/29/
{
    "name":"MaeMae - Very Cherry Drinks"
}

RESPONSE 200 OK
{
    "id": 29,
    "name": "MaeMae - Very Cherry Drinks",
    "beverageList": []
}
```
### #8 Create A Beverage For A Specific Beverage Type
```
POST localhost:9092/api/beverage-type/29/
{
    "name":"MaeMae - Cherry-Berry Drink",
    "description":"Sweet Smooth Cherry Berry Fizzles",
    "pairing":"Tofu/Chicken/Fish",
    "goodToKnow":"Great w/ all eats.",
    "proTip":"Enjoy w/ all eats"
}

RESPONSE 200 OK
{
    "id": 30,
    "name": "MaeMae - Cherry-Berry Drink",
    "description": "Sweet Smooth Cherry Berry Fizzles",
    "pairing": "Tofu/Chicken/Fish",
    "goodToKnow": "Great w/ all eats.",
    "proTip": "Enjoy w/ all eats"
}
```

### #11 Display A Listing Of All Beverage
```
GET localhost:9092/api/beverages/

RESPONSE 200 OK
[
    {
        "id": 12,
        "name": "Java Twins",
        "description": "Smooth Twin Java",
        "pairing": "Tofu/Chicken/Fish",
        "goodToKnow": "Great w/ all eats.",
        "proTip": "Enjoy w/ all eats"
    },
    {
        "id": 13,
        "name": "Java Blend",
        "description": "Smooth Blended Java",
        "pairing": "Tofu/Chicken/Fish",
        "goodToKnow": "Great w/ all eats.",
        "proTip": "Enjoy w/ all eats"
    },
    {
        "id": 29,
        "name": "Java Blend",
        "description": "Smooth Blended Java",
        "pairing": "Tofu/Chicken/Fish",
        "goodToKnow": "Great w/ all eats.",
        "proTip": "Enjoy w/ all eats"
    },
    {
        "id": 30,
        "name": "MaeMae - Cherry-Berry Drink",
        "description": "Sweet Smooth Cherry Berry Fizzles",
        "pairing": "Tofu/Chicken/Fish",
        "goodToKnow": "Great w/ all eats.",
        "proTip": "Enjoy w/ all eats"
    }
]
```



## Technologies Used

- Java 17
- Spring Boot 2.7.8
- H2 Database
- MockMvc
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
