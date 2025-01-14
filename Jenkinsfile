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
                            -Dsuite=${params.TEST_SUITE.toLowerCase()} \
                            -Dcucumber.plugin="json:target/cucumber-reports/cucumber.json" \
                            -Dcucumber.plugin="html:target/cucumber-reports/cucumber.html"
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Generate Reports') {
            steps {
                script {
                    // Raporları arşivle
                    sh """
                        mkdir -p test-reports
                        cp -r target/cucumber-reports/* test-reports/ || true
                        cp -r target/surefire-reports test-reports/ || true
                        cp -r target/allure-results test-reports/ || true
                        zip -r test-reports.zip test-reports/
                    """
                }
            }
        }
    }

    post {
        always {
            // Test raporlarını arşivle
            archiveArtifacts artifacts: [
                'test-reports.zip',
                'target/cucumber-reports/**/*'
            ].join(', '), fingerprint: true
            
            // Allure raporu
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            // Cucumber raporu
            cucumber(
                buildStatus: 'UNSTABLE',
                failedFeaturesNumber: 1,
                failedScenariosNumber: 1,
                skippedStepsNumber: 1,
                failedStepsNumber: 1,
                fileIncludePattern: '**/cucumber.json',
                jsonReportDirectory: 'target/cucumber-reports',
                reportTitle: 'Cucumber Test Raporu',
                classifications: [
                    ['key': 'Browser', 'value': 'Chrome'],
                    ['key': 'Branch', 'value': env.BRANCH_NAME],
                    ['key': 'Environment', 'value': params.TEST_ENV],
                    ['key': 'Test Suite', 'value': params.TEST_SUITE]
                ]
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