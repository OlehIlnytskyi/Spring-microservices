# Spring-microservices

This is a project that was created to learn the basic technologies when working with a microservice architecture. The general structure of the project, as well as an analysis of its main elements, are given below.

&nbsp;
# Content

- Project structure
    - Database structure
- Technologies used
  - Spring Boot
  - Lombok
  - Spring Data JPA
  - Eureka
  - Spring Cloud Gateway
  - Spring Security with JWT authorization
  - Circuit Breaker
- How to Install the project
- How to Run the project
- Sending requests
- Conclusion
- Sources

&nbsp;
# Project structure

Inside the project you will find five separate programs - eureka-server, hangar-service, order-service, security-service and gateway-service.

<img src="media/project_scheme.png">

- **Eureka server** - we need it for [Service Discovery](https://www.baeldung.com/spring-cloud-netflix-eureka). Also, [this](https://www.youtube.com/watch?v=e09P-CkCvvs&list=PLqq-6Pq4lTTZSKAFG6aCDVDP86Qx4lNas&index=17) is a good video about this topic.
- **Hangar Service** - REST application that stores all machines, Eureka client.
- **Orders Service** - REST application that stores all orders, Eureka client.
- **Security Service** - responsible for security and provide JWT authorization.
- **Gateway Service** - gateway for application.

&nbsp;
## Database structure

As you can see in the image above, i used [Database per Service](https://microservices.io/patterns/data/database-per-service.html) pattern.

Now, let's look closer at our databases:

### Hangar database schema

![](media/hangar_schema.png)

As we can see, this database has only one table that stores all the Machines. As simple as it can be.

### Orders database schema

![](media/orders_schema.png)

Orders schema is a little more complicated. Here we have [unidirectional relationship](https://www.baeldung.com/spring-jpa-unidirectional-one-to-many-and-cascading-delete) between **"Orders"** and **"OrderItem"** tables. The third table **"orders_order_items"** is created automatically by Hibernate.

**Orders** table stores orders and can contain one or several **OrderItem** values. **OrderItem** contains machineId and the price of this **Machine**. Therefore it's the relationship [One to Many](https://www.baeldung.com/hibernate-one-to-many).


Now we should have enough information to run project.

&nbsp;
# Technologies used

Below I have provided a brief explanation for each technology and links where you can learn about it.

## Spring Boot

Spring Boot excels in the development of web services and microservices architecture. Its design is particularly well-suited for building scalable and modular applications, making it a popular choice for creating robust and efficient backend services. With built-in support for microservices patterns and easy integration with Spring Cloud, Spring Boot simplifies the development and deployment of distributed systems.

### Links
 - [Official documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
 - [Great video for beginners](https://www.youtube.com/watch?v=Nv2DERaMx-4&list=LL&index=27&t=17004s)

&nbsp;
## Lombok

Lombok is a Java library that reduces boilerplate code by offering annotations to automatically generate common code structures, like getters and setters, during compilation. It simplifies code and improves readability by eliminating the need for manual coding of repetitive tasks, fostering cleaner and more concise Java code.

For example, class MachineRequest from this project, that was written using Lombok:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineRequest {
    private MachineType type;
    private String model;
    private BigDecimal price;
}
```

&nbsp;
And how we would write this class without Lombok

```java
public class MachineRequest {
    private MachineType type;
    private String model;
    private BigDecimal price;
    
    public MachineRequest() {
        
    }

    public MachineRequest(MachineType type, String model, BigDecimal price) {
        this.type = type;
        this.model = model;
        this.price = price;
    }

    public MachineType getType() {
        return type;
    }

    public void setType(MachineType type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineRequest that)) return false;
        return type == that.type && Objects.equals(model, that.model) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, model, price);
    }

    @Override
    public String toString() {
        return "MachineRequest{" +
                "type=" + type +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
```

&nbsp;
The difference is clearly visible.

### Links
- [Official documentation](https://projectlombok.org/features/)
- [Introduction to Project Lombok](https://www.baeldung.com/intro-to-project-lombok)

&nbsp;
## Spring Data JPA

Spring Data JPA simplifies database access in Spring applications by providing a repository-based abstraction over JPA. It streamlines data operations, supports query methods, and enhances code efficiency when interacting with relational databases.

Use in the project:

- Entity classes - these are classes that represent tables in the database.
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "machine")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MachineType type;

    private String model;
    private BigDecimal price;
}

```

&nbsp;
- JpaRepositories - a tool for interacting with the database. Namely, with the table, the entity of which we specify in the first generic parameter.
```java
@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

}
```

The important thing about repositories is that we don't need to write code for this class to work. Hibernate automatically generates the code for the methods of this interface, we only need to correctly specify the method name, following all conventions.

### Links
- [Official documentation](https://spring.io/projects/spring-data-jpa/)
- [Introduction to Spring Data JPA](https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa)
- [Hibernate](https://www.geeksforgeeks.org/hibernate-tutorial/)


&nbsp;
## Eureka

Eureka is a service discovery tool for microservices. It allows services to register and discover each other dynamically, enhancing communication in distributed systems by providing a central registry server.

The main thing we need from Eureka is Server and Clients.

&nbsp;
- Eureka server

To create a server, you need to mark the main class of our application with the annotation @EnableEurekaServer.

```java
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
```

&nbsp;
After starting the program, if we go to http://localhost:5432 we will see that the server is working.

&nbsp;
- Eureka client

In order to make the application an Eureca client, we need dependency to be in pom.xml file. And that's all!

If you wish, you can add the @bebra annotation for clarity, but this is optional.
```java
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

### Links
- [Guide video series](https://www.youtube.com/watch?v=_QezR9wkBKs)
- [Official documentation](https://cloud.spring.io/spring-cloud-netflix/reference/html/)

&nbsp;
## Spring Cloud Gateway

Spring Cloud Gateway is a robust API gateway built on Spring WebFlux. It simplifies routing, filtering, and load balancing for microservices, offering flexibility and easy configuration in managing API traffic.

I will not explain a lot here, you will find all this at the links below. I will only give the configuration used in this project.
```java
 @Bean
 public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
     return builder.routes()
             .route("security-service-route", r -> r
                     .path("/security/**")
                     .filters(f -> f
                             .rewritePath("/security/(?<segment>.*)", "/api/security/${segment}"))
                     .uri("lb://security-service")
             )
             .route("hangar-service-route", r -> r
                     .path("/hangar/**")
                     .filters(f -> f
                             .filter(authenticationFilter)
                             .rewritePath("/hangar/(?<segment>.*)", "/api/hangar/${segment}"))
                     .uri("lb://hangar-service"))

             .route("orders-service-route", r -> r
                     .path("/orders/**")
                     .filters(f -> f
                             .filter(authenticationFilter)
                             .rewritePath("/orders/(?<segment>.*)", "/api/orders/${segment}"))
                     .uri("lb://orders-service"))
             .build();
 }
```

### Links
- [Guide video](https://www.youtube.com/watch?v=1vjOv_f9L8I)
- [Official documentation](https://docs.spring.io/spring-cloud-gateway/reference/index.html)

&nbsp;
## Spring Security with JWT authorization

Spring Security with JWT authorization enables secure authentication and authorization in Java applications. It uses JSON Web Tokens for stateless authentication, issuing tokens upon login for subsequent authorization. This approach enhances security and scalability, particularly in distributed and microservices architectures.

Let's look at the implementation of the class responsible for tokens.
```java
public class JwtUtils {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public static void validateToken(final String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }


    public static String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private static String createToken(Map<String, Object> claims, String userName) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private static Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

&nbsp;
You can find out how he does it by following the links below.

### Links
- [Great video series about JWT tokens](https://www.youtube.com/watch?v=soGRyl9ztjI&list=PLqq-6Pq4lTTYTEooakHchTGglSvkZAjnE&index=10)
- [More information](https://www.baeldung.com/java-json-web-tokens-jjwt)

&nbsp;
## Circuit Breaker

Although I implemented a PATTERN in this project, I had to remove it because it did not fit the logic of the method.

Instead, I recommend the following sources of information where everything is explained in detail.

### Links
- [Great vidos series about Circuit Breaker](https://www.youtube.com/watch?v=mJ8JSach2P4&list=PLqq-6Pq4lTTbXZY_elyGv7IkKrfkSrX5e&index=11)
- [Spring Cloud Circuit Breaker Quickstart](https://spring.io/guides/gs/cloud-circuit-breaker/)
- [Official documentation](https://docs.spring.io/spring-cloud-circuitbreaker/reference/index.html)


&nbsp;
# How to Install the project

1. You need a [database server](https://phoenixnap.com/kb/what-is-a-database-server) installed and running. In my case i used **Postgres**.
2. You need a [Maven](https://maven.apache.org/download.cgi) installed.
3. Clone this GitHub repository to your local machine.
4. Open this project and wait for Maven to download all needed dependencies.

Congratulations, you have successfully installed project and ready to run it!


&nbsp;
# How to Run the project

1. Configure the database server:
   - Make sure that server is running
   - If you're not using Postgres, change **postgresql** to needed value in **"spring.datasource.url"** in **"application.properties"**
   - Check your server port, if it's not **5432**, change value to needed one in **"spring.datasource.url"**
   - Change **"spring.datasource.username"** and **"spring.datasource.password"** to needed values
   - Create database **hangar** for Hangar service
   - Create database **orders** for Orders service
   - Create database **security** for Security service
     
3. Run **EurekaServer** application and wait for it to run completely.
4. Run other applications.

If you didn't get eny exceptions, congratulations, your project sturted up successfully!

&nbsp;
Now we're ready to send requests, or in other words communicate with our application.


&nbsp;
# Sending Requests

Let's switch to [Postman](https://www.postman.com/downloads/). **Postman** is the application that we can use to communicate with our API in a very convenient and fast way.

&nbsp;
What is the end result of the program? That's right, it's an order! Let's create one.

But in order to make an order, it is necessary that at least several machines are available for ordering. Let's see if we have some.

We`ll use /hangar/getAll endpoint:

<img src="media/hangar/postman_hangar_getAll_unauthorized.png">
&nbsp;

As we can see, we got response status UNAUTHORIZED. This means that we have not passed the protection of the program.

&nbsp;
To do so, we have to get JWT token first. Let's get it using /security/register endpoint with body of UserRequest class.

UserRequest class:
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String username;
    private String password;
    private String role;
}
```
&nbsp;

Request:
<img src="media/security/postman_security_register.png">
&nbsp;

Now we have JWT token to access application!

> Every jwt token has an expiration date. In this project it's set to "1000 * 60 * 30" miliseconds, which equals 30 minutes.
&nbsp;

Now let's try to get available machines again.
To use JWT token, select Authorization tab, then Bearer Token type and paste your token

<img src="media/hangar/postman_hangar_getAll.png">
&nbsp;

Finally! Now we can freely communicate with the program.
&nbsp;

Note that you will receive an empty list in response. In order to add machines into the hangar, request the http://localhost:8080/hangar/post with the body, the text of which is given in data/hangarMachines.txt file. And don't forget to use token!

<img src="media/hangar/postman_hangar_post.png">
&nbsp;

Now let's make an Order. We can do whis through orders/post andpoint:

<img src="media/orders/postman_orders_post.png">
&nbsp;

Let's check our new order:

<img src="media/orders/postman_orders_getAll.png">
&nbsp;

Congratulations, you successfully created an order!


&nbsp;
# Conclusion

I hope you found something new and useful here. I know i missed a lot of details, but if i explained everything here, this README would be much longer :)


&nbsp;
# Sources

 - Old but gold [theoretical material](https://www.youtube.com/playlist?list=PLqq-6Pq4lTTZSKAFG6aCDVDP86Qx4lNas)
 - [Practical material](https://www.youtube.com/playlist?list=PLSVW22jAG8pBnhAdq9S8BpLnZ0_jVBj0c)
