# Minimum Over Capacity Service

Minimum Over Capacity Service is a project to find minimum number of
seniors and juniors cleaners.

## Installation
To run the project you have two ways
- run by docker
- run by CLI

#### Run by docker
You need to have docker on your system. Go to the root directory of the
project(change directory in terminal) and run this command:

```bash
docker build -t farib/min-over-cap .
```

next step is to go to run docker-compose:
```bash
docker-compose up -d 
```
You must see a result like this after running the last command:
```bash
Creating network "minimum-over-capacity_default" with the default driver
Creating docker-min-over-cap ... done
```
If you don't have the openjdk it will pull it after will run it. If you
run:
```bash
docker ps
```
Output must have these running images:
```bash 
docker-min-over-cap
```

Now its up.

#### Run by CLI
You need to have mvn and openjdk on your system. Go to the root
directory of the project(change directory in terminal) and run this
command: 
```bash 
mvn package
```
It will run all the tests and will create the jar file to run. in the
next step run: 
```bash
java -jar target/minimum-over-capacity-1.0-SNAPSHOT.jar
```
Now its up.




## Usage
In order to use this project API, there is swagger automatic
documentation, you just need to go to this address:
[localhost:8080 swagger-ui ](localhost:8080/swagger-ui.html) you will
see the parameters to do requests to the restful api.

## Hint
The way that I've solved the problem is by dynamic programming. The idea
came to me by rucursive function: 
```math
minOverCap(roomNums, senior, junior) = 
    min {
        minOverCap(roomNums - senior, senior, junior),
        minOverCap(roomNums - junior, seniro, junior)
        }
 ```
 It's just the simple idea so I changed it based on the problem(at least
 one senior) and after that change the code to dynamic programming to
 prevent problems of recursive functions.

## Future works
-We can improve the code buy creating a map of the solution for subtrees
just for the rooms that are in input not all the smaller rooms.


