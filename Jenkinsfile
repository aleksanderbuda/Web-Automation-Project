pipeline {
    agent {label 'Windows'}
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
            steps {
                script {
                    try {
                        timeout(time: 8, unit: 'MINUTES') {
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
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
                archiveArtifacts artifacts: 'allure-report/**', allowEmptyArchive: true
                cleanWs()
            }
        }
    }
}