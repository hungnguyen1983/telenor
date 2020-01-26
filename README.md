# Telenor test project

Telenor test project

## Project setup

Locate yourself in the project, under /telenor folder

### Build project

```
$ mvn package -Dmaven.test.skip=true && java -jar target/greeting.jar
```

### Build docker image

```
$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
$ docker build -t greeting .
```

### Run docker image locally

```
$ docker run -p 5000:8080 -t greeting
```

### Run your tests

```
$ mvn clean test
```