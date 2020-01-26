# Telenor test project

Telenor test project

## Project setup

Locate yourself in the project, under /telenor folder

### Build project

```
$ mvn package
```

### Build docker image

```
$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
$ docker build -t telenor-test .
```

### Run docker image locally

```
$ docker run -p 5000:8080 -t telenor-test
```

### Run tests and integration tests

```
$ mvn verify -Pfailsafe
```