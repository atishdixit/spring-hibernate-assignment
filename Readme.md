## Movie CRUD Application.
This project provide End point to add new move, remove movie, read and delete.

##Tool and technologies:
SpringBoot 2.2.4
Java 8
MySQL 
swagger2 2.6.1
JPA 
jackson 29.8

## How to Use Application
1. Create Data base 'moviesdb'.
and add User and password in Application.properties file
2. Run application.
Application create 10 record. 

#Now You can Just Hit on Browser:
#To retrieve a list of movies
http://localhost:8080/ws/movies/all
You will get 10 records.

#To get Movie for ID
http://localhost:8080/ws/movies/5

#To Create, Add new Movie
http://localhost:8080/ws/movies/create
Type: 	application.json
{
                "id": 1,
                "title": "movie1",
                "category": "cta2",
                "rating": 4.5
}

#For Update
http://localhost:8080/ws/movies/update

#For swagger user.
URL: http://localhost:8080/swagger-ui.html




