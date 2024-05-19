# scratch-game
## Table of Contents
- [Requirements](#requirements)
- [Build Instructions](#build-instructions)

## Requirements

### Java version
- Java 8 or higher

### Build Tool
- Maven

### Dependencies
- Gson (version 2.10.1): Used for JSON parsing and serialization.
- JUnit 5 (version 5.10.2): Used for writing and running unit tests.


## Build Instructions
To build the project at the base of the project, run:
```
mvn clean install
```
```
java -jar scratch-game.jar --config config.json --betting-amount 100
```

