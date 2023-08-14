# PostAPI | Challenge-3-Compass

## About

The PostAPI is a challenge proprosed by Compass UOL to test our development throught the internship program.

In this project we needed to create a RestAPI, an application that asynchronously fetches posts from an external API, enriches them with comment data, and keeps a log of processing updates. The client will then be able to search for posts and the history of states through the API immediately.

The main goal of this project is to demonstrate proficiency in building a scalable and maintainable API using Java and Spring Boot. 
It incorporates industry-standard practices and leverages the power of Spring Boot's framework to create a robust and efficient solution. 
The app will be subjected to a bombardment of requests simulating a high volume of processing, and it is expected that the responses and processing will be as close to real-time as possible. 
The evaluation will be based primarily on the objective and technical requirements. Additionally, the quality of the code, chosen architecture, and the resilience of the solution will be assessed.


## How to Prepare the Environment?

### IDE

To begin, it's important to have an Integrated Development Environment (IDE) installed on your machine. You can choose to install IntelliJ IDEA, 
which is a popular IDE for Java development. You can download and install IntelliJ IDEA by visiting their official [website](https://www.jetbrains.com/idea/download/).

If you come across any issues or face challenges during the installation process, you can refer to a helpful video tutorial on YouTube that provides step-by-step instructions on how to install the IDE.

[![Video](https://i.ytimg.com/vi/viNG3VVnzFE/hq720.jpg)](https://www.youtube.com/watch?v=viNG3VVnzFE)

---

### Attention!

Once you have your IDE installed, it's essential to ensure that you have the Java JDK (Java Development Kit) installed as well. When you create a new project in IntelliJ IDEA, it provides an option to install the JDK automatically.

It's important to note that you should install version 17 or a more recent version of the JDK to ensure compatibility with the project.

By verifying the installation of the Java JDK and ensuring that it's at least version 17, you'll be ready to proceed with your development tasks using IntelliJ IDEA.

You'll need to have Docker installed too! so be sure to install Docker and, if using windows, you should also have WSL installed!


---

### Postman

Almost ready! Once you have the IDE installed, the next step is to install Postman, which is a popular tool for testing API endpoints. To download Postman, simply visit their official website at [https://www.postman.com/downloads/](https://www.postman.com/downloads/) and follow the instructions provided.

By installing Postman, you'll have a user-friendly interface that allows you to send requests to your API and inspect the responses. It's a valuable tool for testing and debugging API endpoints.

Make sure to have Postman installed before proceeding with testing the API.  

---

## All set!

Now you're all set to test the API!

### Windows

in your windows terminal use the command: "docker-compose up" and wait for docker to be initialized, once started, just use the command: "./gradlew :challenge3-api:run" the API will start and will be ready for testing!
Then just use the endpoints provided below. Enjoy exploring the functionalities!

### Linux

For Linux users, use the command: "docker-compose up" to start docker and use the command: "./gradlew :challenge3-api:run" to run the API.
Then just use the endpoints provided below. Enjoy exploring the functionalities!

---

## Endpoints

### • POST

#### Process Post: 

    - Description: Processes a post.
    - Method: POST
    - Path: http://localhost:8080/posts/{postId}
    - Requirements:
        - postId must be a number between 1 and 100.
        - Existing postId should not be accepted.
        
---

### • DELETE

#### Disable post By id:

    - Description: Disables a post that is in the "ENABLED" state.
    - Method: DELETE
    - Path: http://localhost:8080/posts/{postId}
    - Requirements:
        - postId must be a number between 1 and 100.
        - postId should be in the "ENABLED" state.
        - the postId must correspond the main ID of the post, not the externalId

---

### • PUT

### Reprocess post:

    - Description: Reprocesses a post that is in the "ENABLED" or "DISABLED" state.
    - Method: PUT
    - Path: http://localhost:8080/posts/{postId}
    - Requirements:
        - postId must be a number between 1 and 100.
        - postId should be in the "ENABLED" or "DISABLED" state.
        - the postId must correspond the main ID of the post, not the externalId

---

### • GET

#### GET all posts:

    - Description: Provides a list of posts.
    - Method: GET
    - Path: /posts
    - Response:
![image](https://legend-nightshade-bd0.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fe6c2fec6-e9ff-41e2-bac4-41a001f157f9%2FImage2.png?table=block&id=d04294e7-28e8-4b19-93da-23f14595d087&spaceId=1278aaea-06ee-4326-9268-d987610c5c1c&width=2000&userId=&cache=v2)

    - title: Can be null or empty depending on the state.
    - body: Can be null or empty depending on the state.
    - comments: Can be null or empty depending on the state.
    - history: Cannot be null or empty; it must always have a value.
    
---

# Technologies

In this project, the following techlogies were used:

- Java
- SpringBoot
- H2
- Gradle
- FeignClient
- RabbitMQ
- Docker
- PostMan

---

# Thanks!

I would like to express my gratitude to @Compass.UOL for giving me this amazing opportunity to showcase my skills and knowledge. I am truly grateful for this challenge and look forward to embracing future opportunities.

I extend our heartfelt thanks to the entire team at @Compass.UOL for their support and guidance throughout this process. It has been an incredible learning experience, and I appreciate the chance to put my ability to the test.

I am excited about the challenges that lie ahead and am eager to contribute my best to the success of @Compass.UOL. Thank you once again for this wonderful opportunity, and I eagerly await what the future holds.


## Author

Developed by:<br><br>
<a href="https://www.linkedin.com/in/mateus-silva-ara%C3%BAjo-187586217/">Mateus Silva Araújo</a>
