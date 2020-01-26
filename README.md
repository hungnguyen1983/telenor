# Telenor test project

Telenor test project

## Project setup

### Build project

```
$ cd telenor
$ mvn package -Dmaven.test.skip=true && java -jar target/greeting.jar
```

### Build docker image

```
$ cd telenor
$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
$ docker build -t greeting 
```

### Run docker image locally

```
$ docker run -p 5000:8080 -t greeting
```

### Run your tests

```
$ cd telenor
$ mvn clean test
```