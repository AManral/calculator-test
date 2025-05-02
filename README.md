# Google Calculator Automation with Playwright & Java

This project automates calculator UI functionality using Playwright and generates detailed test report using Allure.

## Technologies Used

- Java 11+ [Preferably Java 17]
- [Playwright for Java](https://playwright.dev/java/)
- TestNG
- Allure Framework (for reporting)
- Maven

## Setup Instructions

- Install JDK 17
- Install Maven ```brew install maven```
- Clone Repo - https://github.com/AManral/calculator-test.git
- Install allure: ```brew install allure```

## Run Tests using Maven
 -  ```mvn clean test```
 - Alternatively, Right click on `CalculatorTest` class --> Run 'CalculatorTest'


## Generate Allure Report within browser
- After Test execution is complete, go to target folder on terminal(cd [local path]/calculator-test/target)
- Use command ```allure serve allure-results```
- Clicking on the ip url shared on terminal will open a report on browser


## Project Structure

```bash
calculator-test/
├── README.md
├── pom.xml
├── src
│ ├── main
│ │ ├── java
│ │ └── resources
│ └── test
│     └── java

