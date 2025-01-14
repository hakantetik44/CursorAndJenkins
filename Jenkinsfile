pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK17'
    }

    parameters {
        choice(
            name: 'BRANCH',
            choices: ['main', 'development', 'qa', 'staging', 'feature/search-tests'],
            description: 'Hangi branch üzerinde test çalıştırılacak?'
        )
        
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
                script {
                    echo "🔄 ${params.BRANCH} branch'ine geçiliyor..."
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${params.BRANCH}"]],
                        userRemoteConfigs: [[
                            url: 'https://github.com/hakantetik44/CursorAndJenkins.git'
                        ]]
                    ])
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    try {
                        sh """
                            mvn clean test \
                            -Denv=${params.TEST_ENV.toLowerCase()} \
                            -Dsuite=${params.TEST_SUITE.toLowerCase()} \
                            -Dbranch=${params.BRANCH}
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Package Reports') {
            steps {
                script {
                    // Create a single directory for all reports
                    sh """
                        mkdir -p consolidated-reports
                        
                        # Copy all report types to the consolidated directory
                        cp -r target/surefire-reports consolidated-reports/ || true
                        cp -r target/cucumber-reports consolidated-reports/ || true
                        cp -r target/allure-results consolidated-reports/ || true
                        cp -r test-raporlari consolidated-reports/ || true
                        
                        # Create a single zip file
                        zip -r consolidated-test-results.zip consolidated-reports/
                    """
                }
            }
        }
    }

    post {
        always {
            // Archive only the consolidated zip file
            archiveArtifacts artifacts: 'consolidated-test-results.zip', fingerprint: true
            
            // Keep Allure report generation but don't archive individual files
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            // Process JUnit results but don't publish them
            junit(
                testResults: '**/target/surefire-reports/TEST-*.xml',
                skipPublishingChecks: true,
                skipMarkingBuildUnstable: true
            )

            cleanWs()
        }

        success {
            echo """
            ✅ Test Sonuçları:
            🌿 Branch: ${params.BRANCH}
            🎯 Test Suite: ${params.TEST_SUITE}
            🌍 Environment: ${params.TEST_ENV}
            ✨ Status: Başarılı
            """
        }

        failure {
            echo """
            ❌ Test Sonuçları:
            🌿 Branch: ${params.BRANCH}
            🎯 Test Suite: ${params.TEST_SUITE}
            🌍 Environment: ${params.TEST_ENV}
            ⚠️ Status: Başarısız
            """
        }
    }
}