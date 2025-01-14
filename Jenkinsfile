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
                script {
                    // Önce repository'yi çek
                    checkout scm
                    
                    // Mevcut branch'i al
                    def currentBranch = sh(
                        script: 'git rev-parse --abbrev-ref HEAD',
                        returnStdout: true
                    ).trim()
                    
                    // Environment variable olarak kaydet
                    env.BRANCH_NAME = currentBranch
                    
                    echo "🔄 Tespit edilen branch: ${env.BRANCH_NAME}"
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
                            -Dbranch=${env.BRANCH_NAME}
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
            archiveArtifacts artifacts: 'consolidated-test-results.zip', fingerprint: true
            
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

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
            🌿 Branch: ${env.BRANCH_NAME}
            🎯 Test Suite: ${params.TEST_SUITE}
            🌍 Environment: ${params.TEST_ENV}
            ✨ Status: Başarılı
            """
        }

        failure {
            echo """
            ❌ Test Sonuçları:
            🌿 Branch: ${env.BRANCH_NAME}
            🎯 Test Suite: ${params.TEST_SUITE}
            🌍 Environment: ${params.TEST_ENV}
            ⚠️ Status: Başarısız
            """
        }
    }
} 