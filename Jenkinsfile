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
                    // Tüm raporları tek bir zip dosyasında topla
                    sh """
                        mkdir -p test-reports
                        cp -r target/surefire-reports test-reports/
                        cp -r target/cucumber-reports test-reports/
                        cp -r target/allure-results test-reports/
                        cp -r test-raporlari test-reports/
                        zip -r test-reports.zip test-reports/
                    """
                }
            }
        }
    }

    post {
        always {
            // Sadece zip dosyasını ve Allure raporunu arşivle
            archiveArtifacts artifacts: 'test-reports.zip', fingerprint: true
            
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            // JUnit sonuçlarını topla ama gösterme
            junit(
                testResults: '**/target/surefire-reports/TEST-*.xml',
                skipPublishingChecks: true
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