## 📌 Selenium Test Automation Project for ParaBank

This project showcases a comprehensive Selenium-based test automation framework tailored specifically for the ParaBank website created by Parasoft. To maximize maintainability and scalability, it effectively utilizes the Page Object Model (POM) design pattern.

### Key Features:
* **Powerful Web Testing**: Employs _Selenium WebDriver_ to ensure comprehensive interaction and testing of parabank website elements.
* **Streamlined Maintenance:** Leverages _Page Object Model_ (POM) for clean code, easy updates, and long-term efficiency.
* **Cross-Browser Compatibility:** Guarantees flawless functionality across Chrome, Edge, and Firefox using Selenium Grid with Docker.
* **Flexible Execution**: Effortlessly switch between local testing and scaled-up testing using _Selenium Grid with Docker_.
* **Insightful Reporting**: Generates clear, interactive test reports with the _allure-reports_ library for data-driven analysis.
* **CI/CD Ready**: Includes _Jenkinsfile_ for seamless integration into automated pipelines, with adaptable configuration options.
* **Easy Customization**: Offers a _configuration.properties_ file for simple tailoring of test behavior.
* **Realistic Data Generation:** Integrates the _Faker library_ to create lifelike test data, improving scenario coverage.
____
### Setup Checklist
To ensure smooth execution of this Selenium project, please complete the following:

#### 1. Install Essentials

* **Java Development Kit** (**JDK**): Version 8 or higher. Download from Oracle's website or use OpenJDK.
* **Maven**: Maven is a build automation tool used in this project. Refer to the official Apache Maven guide for installation on your system.
* **Docker**: Get started with Docker by following their official installation instructions: https://www.docker.com/get-started

#### 2. Optional (But Recommended)
* **Jenkins**: Jenkins is an open-source automation server for continuous integration and continuous delivery (CI/CD). For automated testing pipelines, download Jenkins from the official website.
* **IDE**: Choose your preferred Java IDE (IntelliJ IDEA or Eclipse are great options).


#### 3. Project Setup Notes:
* **Dependencies**: The project's pom.xml file handles dependencies. Maven will download these during your initial setup (internet connection needed).

____
### Execution
1. **Launch your IDE**: Open your preferred Java IDE (IntelliJ IDEA or Eclipse are recommended).
2. **Import Project**: Import the project as a Maven project. Do this by selecting the project's root directory within your IDE.
3. **Configure for Remote Execution** (**Optional**):
    * Open the src/main/java/config/configuration.properties file.
    * Set the `isRemote` value:
        *
            * `true`: Enables remote execution using Docker and Selenium Grid for cross-browser testing (ensure Docker Desktop is running).
        * `false`: Enables local test execution.
4. **Run Tests:** Choose one of the following methods:
* **Right-click**: Right-click on a specific test class or individual test case(s) and select "Run".
* **testng.xml:** Execute the `testng.xml` file.
5. **Command Line Alternative**:
* Open a terminal and navigate to the project's root directory.
* Execute either `mvn clean test` or `mvn clean install` to run the test suite.
____
### Jenkins Integration
#### Pre-requisites
* **Jenkins**: Ensure you have Jenkins installed and running on your machine.
* **Plugins**: Install any required plugins in Jenkins (e.g., Git plugin, Allure plugin).
* **Tools Configuration**: Configure necessary tools within Jenkins (e.g., JDK, Maven).
#### Create a New Jenkins Pipeline Job:
1. **New Job**: Navigate to your Jenkins dashboard and click "New Item".

2. **Name and Type**: Provide a name for your job and select "Pipeline" as the job type.

3. **Parameterize**: In the "General" section, enable "This project is parametrized" and add a text parameter:

   **Name**: `isRemote`

   **Default value:** `false`

4. **Pipeline Configuration**: In the "Pipeline" section:

          Definition: Select "Pipeline script from SCM"
          SCM: Choose "Git"
          Repository URL: Set to https://github.com/Catanci/Para_Bank_Framework.git
          Branches to build: */main
          Script Path: Jenkinsfile


#### Run Your Pipeline

* **Build with Parameters**: Click "Build with Parameters" and set the `isRemote` parameter accordingly:
    * `true`: Enables remote execution with Docker (ensure Docker Desktop is running)
    * `false`: Enables local execution
* **Allure Reports**: After the pipeline completes, access the Allure Report as a build artifact (ensure the Allure plugin is installed in Jenkins).

Happy Testing! 😊
