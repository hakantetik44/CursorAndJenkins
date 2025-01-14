pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK17'
    }

    parameters {
        choice(
            name: 'TEST_ENV',
            choices: ['QA', 'STAGING', 'PROD'],
            description: 'Test ortamını seçin'
        )
        
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression'],
            description: 'Test suite seçin'
        )
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        timestamps()
        disableConcurrentBuilds()
        skipDefaultCheckout()
    }

    stages {
        stage('Initialize') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    try {
                        sh """
                            mvn clean test \
                            -Denv=${params.TEST_ENV.toLowerCase()} \
                            -Dsuite=${params.TEST_SUITE.toLowerCase()}
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }
    }

    post {
        always {
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            cleanWs()
        }

        success {
            echo "✅ ${params.TEST_SUITE} testleri başarıyla tamamlandı"
        }

        failure {
            echo "❌ ${params.TEST_SUITE} testlerinde hata oluştu"
        }
    }
} 