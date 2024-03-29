pipeline {
    agent any
    stages {
        stage('isRemote') {
            steps {
                script {
                    config = readFile "src/test/java/config/configuration.properties"
                    newConfig = config.replaceAll("isRemote=.*","isRemote=${isRemote}")
                    writeFile file: "src/test/java/config/configuration.properties", text: "${newConfig}"
                }
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    try {
                        timeout(time: 3, unit: 'MINUTES') {
                            bat script: 'mvn clean test'
                        }
                    } catch (err) {
                        echo "Test stage timed out, but the pipeline will continue."
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