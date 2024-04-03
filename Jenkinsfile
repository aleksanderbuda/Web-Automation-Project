pipeline {
    agent any
    stages {
        stage('isRemote') {
            steps {
                script {
                    config = readFile "src/main/java/config/configuration.properties"
                    newConfig = config.replaceAll("isRemote=.*","isRemote=${isRemote}")
                    writeFile file: "src/main/java/config/configuration.properties", text: "${newConfig}"
                }
            }
        }
        stage('Run Tests') {
            node('Built-In Node') {
                steps {
                    script {
                        try {
                            timeout(time: 3, unit: 'MINUTES') {
                                bat 'mvn clean test -X'
                            }
                        } catch (err) {
                            echo "Test stage timed out, but the pipeline will continue."
                        }
                    }
                }
            }
        }
        stage('Generate Allure Report') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                }
                script {
                    cleanWs()
                }
            }
        }
    }
}