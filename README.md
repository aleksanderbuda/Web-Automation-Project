## 📌 Selenium Test Automation Project for ParaBank

This project showcases a comprehensive Selenium-based test automation framework tailored specifically for the ParaBank website created by Parasoft. To maximize maintainability and scalability, it effectively utilizes the Page Object Model (POM) design pattern.

### Key Features:
* **Powerful Web Testing**: Employs _Selenium WebDriver_ to ensure comprehensive interaction and testing of parabank website elements.
* **Streamlined Maintenance:** Leverages _Page Object Model_ (POM) for clean code, easy updates, and long-term efficiency.
* **Cross-Browser Compatibility:** Guarantees flawless functionality across Chrome, Edge, and Firefox using Selenium Grid with Docker.
* **Flexible Execution**: Effortlessly switch between local testing and scaled-up testing using _Selenium Grid with Docker_.
* **Insightful Reporting**: Generates clear, interactive test reports with the _allure-reports_ library for data-driven analysis.
* **CI/CD Ready**: Seamless integration into automated jobs, with adaptable configuration options.
* **Easy Customization**: Offers a _config.properties file for simple tailoring of test behavior.
____
### Setup Checklist
To ensure smooth execution of this Selenium project, please complete the following:

#### 1. Install Essentials

* **Java Development Kit** (**JDK**): Version 8 or higher. 
* **Maven**: Maven is a build automation tool used in this project.
* **Docker**: Get started with Docker by following their official installation instructions: https://www.docker.com/get-started

#### 2. Optional (But Recommended)
* **Jenkins**: Jenkins is an open-source automation server for continuous integration and continuous delivery (CI/CD).
* **IDE**: Choose your preferred Java IDE.


#### 3. Project Setup Notes:
* **Dependencies**: The project's pom.xml file handles dependencies. Maven will download these during your initial setup.

____
### Execution
1. **Launch your IDE**: Open your preferred Java IDE (IntelliJ IDEA or Eclipse are recommended).
2. **Import Project**: Import the project as a Maven project. Do this by selecting the project's root directory within your IDE.
3. **Configure for Remote Execution** (**Optional**):
    * Open the src/main/resources/_config.properties file. 
    * Set the `browser` value to desired browser choice
4. **Run Tests:** Choose one of the following methods:
* **Right-click**: Right-click on a specific test class or individual test case(s) and select "Run".
* **testng.xml:** Execute the `regression.xml` file.
5. **Command Line Alternative**:
* Open a terminal and navigate to the project's root directory.
* Execute either `mvn clean test` or `mvn clean install` to run the test suite.
____
### Jenkins Integration
#### Pre-requisites
* **Jenkins**: Ensure you have Jenkins installed and running on your machine.
* **Plugins**: Install any required plugins in Jenkins (e.g., Git plugin, Docker, Maven etc.).
* **Tools Configuration**: Configure necessary tools within Jenkins (e.g., JDK, Maven).
#### Create a New Jenkins Pipeline Job:
1. **New Job**: Navigate to your Jenkins dashboard and click "New Item".

2. **Name and Type**: Provide a name for your job and select "Freestyle" as the job type.
3. **Freestyle Configuration**:

          
          Repository URL: Set to https://github.com/Catanci/ParaBank-Automation-Project.git
          Branches to build: */main
          Build Steps: Invoke top-level Maven targets
            -Maven version: Maven 3.9.8
            -Goals: clean test
4. The test must  run on `Windows node!`

#### Run Your Freestyle Job


Happy Testing! 😊
