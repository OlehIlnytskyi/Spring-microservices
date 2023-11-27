# Spring-cloud-microservices-military-hangar

> This is a simple project that was created with a microservice architecture to learn and master the basic technologies when working with such an architecture.

## Why i created this project

I developed this project to explore and implement new technologies within Spring. If you're also learning and interested in the technology used in this project, you can use it as a base and I'll help you understand how it all works.

### Technologies used in this project

- Spring Boot
- Spring Web
- Spring Cloud
- Spring Data JDBC (DAO classes)
- PostgreSQL database
- Lombok
- Eureka

> Note that these are technologies that are already implemented, but i'm also going to add a few more that i'm working on right now

### Technologies that I am going to implement

- API Gateway
- Security
- Circuit Breaker
- ...



# How to Install the project

1. You need to have a (database server)[] installed and running. In my case i used **Postgres**.
2. You need to have (Maven)[] installed.
3. Clone this GitHub repository to your local machine.
4. Open this project and wait for Maven to download all needed dependencies.

Congratulations, you have successfully installed project and ready to run it!


## Project structure

Inside the project you will find three separate programs - one **Eureka server** and two **services**, namely **Hangar** and **Orders**.

![](src/resources/media/project_structure.png)


- **Hangar Service** - REST application that stores all machines, Eureka client.
- **Orders Service** - REST application that stores all orders, Eureka client.
- **Eureka server** - we need it for [Service Discovery](https://www.baeldung.com/spring-cloud-netflix-eureka). Also, [this](https://www.youtube.com/watch?v=e09P-CkCvvs&list=PLqq-6Pq4lTTZSKAFG6aCDVDP86Qx4lNas&index=17) is a good video about this topic.


## Database structure

As you can see in the image above, i used [Database per Service](https://microservices.io/patterns/data/database-per-service.html) pattern.

Now, let's look closer at our databases:

### Hangar database schema

![](src/resources/media/hangar_schema.png)

As we can see, this database has only one table that stores all the Machines. As simple as it can be.

### Orders database schema

![](src/resources/media/orders_schema.png)

Orders schema is a little more complicated. Here we have [unidirectional relationship](https://www.baeldung.com/spring-jpa-unidirectional-one-to-many-and-cascading-delete) between **Orders** and **OrderItem** tables. The third table !!! TableName !!! is created automatically by Hibernate.

**Orders** table stores orders and can contain one or several **OrderItem** values. **OrderItem** contains machineId and the price of this **Machine**. Therefore it's the relationship [One to Many](https://www.baeldung.com/hibernate-one-to-many).


Now we should have enough information to run project.


# How to Run the project

1. Configure the database server:
   - Make sure that server is running
   - If you're not using Postgres, change **postgresql** to needed value in **"spring.datasource.url"** in **"application.properties"**
   - Check your server port, if it's not **5432**, change value to needed one in **"spring.datasource.url"**
   - Change **"spring.datasource.username"** and **"spring.datasource.password"** to needed values
   - Create database **hangar**
   - Create database **orders**
     
3. Run **EurekaServer** application and wait for it to run completely.
4. Run **Hangar** and **Orders** applications.


If you didn't get eny exceptions, congratulations, your project sturted up successfully!

Now we're ready to send requests, or in other words communicate with our application.



# Sending Requests


Короче тут треба перше надіслати Get запрос через гугл

Тут вже перейти до Постмана

І придумати шо далі писати

Не забути добавити фото!
